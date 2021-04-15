//
//  TrackViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 14.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class TrackViewController: BaseViewController {
    
    @IBOutlet weak var trackTableView: UITableView!
    private var refreshControl = UIRefreshControl()
    private var prototyps: [CellPrototype] = []
    private var trackForTableMapper = TrackForTableMapper()
    
    private lazy var trackViewModel: TrackViewModel = {
        TrackViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        trackViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        trackViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    func setUpParams(track: String, artist: String){
        trackViewModel.setUpParams(track: track, artist: artist)
    }
    
    private func initTable(){
        trackTableView.delegate = self
        trackTableView.dataSource = self
        trackTableView.register(UINib(nibName: "ImageTableViewCell", bundle: nil), forCellReuseIdentifier: "ImageTableCell")
        trackTableView.register(UINib(nibName: "ScrobblesAndListenersTableViewCell", bundle: nil), forCellReuseIdentifier: "ScrobblesAndListenersTableCell")
        trackTableView.register(UINib(nibName: "BaseTitleAndDescriptionTableViewCell", bundle: nil), forCellReuseIdentifier: "BaseTitleAndDescriptionTableCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        trackTableView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        trackViewModel.loadTrack()
    }
    
    deinit {
        trackViewModel.unSubscribe()
    }
}

extension TrackViewController{
    
    func onUIDataReceived(uiData: UIData) {
        guard let data = uiData as? TrackUIData else {
            return
        }
        if(data is TrackUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is TrackUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let tracktData = data as! TrackUIData.Success
            setUpTrack(trackModel: tracktData.track)
        }else if(data is TrackUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! TrackUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
    
    private func setUpTrack(trackModel: TrackModel){
        navigationController?.navigationBar.topItem?.title = trackModel.trackName
        prototyps = trackForTableMapper.transform(trackModel: trackModel)
        trackTableView.reloadData()
    }
}

extension TrackViewController : UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return prototyps.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let prototype = prototyps[indexPath.row]
        let uiTableViewCell: UITableViewCell
        switch prototype.getCellType() {
        case .artist:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! ArtistPrototype)
            uiTableViewCell = cell
            break
        case .tags:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! TagsPrototype)
            uiTableViewCell = cell
            break
        case .lastfm:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! LastFMPrototype)
            uiTableViewCell = cell
            break
        case .wiki:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! WikiPrototype)
            uiTableViewCell = cell
            break
        case .fromAlbum:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! FromAlbumPrototype)
            uiTableViewCell = cell
            break
        case .youtube:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! YouTubePrototype)
            uiTableViewCell = cell
            break
        case .image:
            let cell = tableView.dequeueReusableCell(withIdentifier: "ImageTableCell", for: indexPath) as! ImageTableViewCell
            cell.setPrototype(prototype: prototype as! ImageCellPrototype)
            uiTableViewCell = cell
            break
        case .scrobblesAndListeners:
            let cell = tableView.dequeueReusableCell(withIdentifier: "ScrobblesAndListenersTableCell", for: indexPath) as! ScrobblesAndListenersTableViewCell
            cell.setPrototype(prototype: prototype as! ScrobblesAndListenersPrototype)
            uiTableViewCell = cell
            break
        default:
            uiTableViewCell = UITableViewCell()
            break
        }
        return uiTableViewCell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let prototype = prototyps[indexPath.row]
        let size: CGFloat
        switch prototype.getCellType() {
        case .artist, .tags, .lastfm, .wiki, .fromAlbum, .youtube:
            size = 90
            break
        case .image:
            size = 250
            break
        case .scrobblesAndListeners:
            size = 100
            break
        default:
            size = 0
            break
        }
        return size
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let prototype = prototyps[indexPath.row]
        handleClick(prototype: prototype)
    }
    
    private func handleClick(prototype: CellPrototype){
        switch prototype.getCellType() {
        case .artist:
            let type = prototype as! ArtistPrototype
            trackViewModel.onArtistClicked(artist: type.artist)
            break
        case .fromAlbum:
            let type = prototype as! FromAlbumPrototype
            trackViewModel.onAlbumClicked(artist: type.artist, album: type.fromAlbum)
            break
        case .lastfm:
            let type = prototype as! LastFMPrototype
            trackViewModel.onLastFmClicked(link: type.lastFm)
            break
        case .youtube:
            let type = prototype as! YouTubePrototype
            trackViewModel.onYouTubeClicked(link: type.youTube)
            break
        default:
            break
        }
    }
}
