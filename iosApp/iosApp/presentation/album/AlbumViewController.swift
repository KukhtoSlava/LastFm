//
//  AlbumViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit
import shared

class AlbumViewController : BaseViewController {
    
    @IBOutlet weak var albumTableView: UITableView!
    private var refreshControl = UIRefreshControl()
    private var prototyps: [CellPrototype] = []
    private var albumForTableMapper = AlbumForTableMapper()
    
    private lazy var albumViewModel: AlbumViewModel = {
        AlbumViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        albumViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        albumViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    func setUpParams(album: String, artist: String){
        albumViewModel.setUpParams(album: album, artist: artist)
    }
    
    private func initTable(){
        albumTableView.delegate = self
        albumTableView.dataSource = self
        albumTableView.register(UINib(nibName: "ImageTableViewCell", bundle: nil), forCellReuseIdentifier: "ImageTableCell")
        albumTableView.register(UINib(nibName: "ScrobblesAndListenersTableViewCell", bundle: nil), forCellReuseIdentifier: "ScrobblesAndListenersTableCell")
        albumTableView.register(UINib(nibName: "BaseTitleAndDescriptionTableViewCell", bundle: nil), forCellReuseIdentifier: "BaseTitleAndDescriptionTableCell")
        albumTableView.register(UINib(nibName: "TrackTitleTableViewCell", bundle: nil), forCellReuseIdentifier: "TrackTitleTableCell")
        albumTableView.register(UINib(nibName: "TrackTableViewCell", bundle: nil), forCellReuseIdentifier: "TrackTableCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        albumTableView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        albumViewModel.loadAlbum()
    }
    
    deinit {
        albumViewModel.unSubscribe()
    }
}

extension AlbumViewController {
    
    func onUIDataReceived(uiData: UIData) {
        guard let data = uiData as? AlbumUIData else {
            return
        }
        if(data is AlbumUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is AlbumUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let albumData = data as! AlbumUIData.Success
            setUpAlbum(albumModel: albumData.albumModel)
        }else if(data is AlbumUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! AlbumUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
    
    private func setUpAlbum(albumModel: AlbumModel){
        navigationController?.navigationBar.topItem?.title = albumModel.album
        prototyps = albumForTableMapper.transform(albumModel: albumModel)
        albumTableView.reloadData()
    }
}

extension AlbumViewController : UITableViewDataSource, UITableViewDelegate{
    
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
        case .tracksTitle:
            let cell = tableView.dequeueReusableCell(withIdentifier: "TrackTitleTableCell", for: indexPath) as! TrackTitleTableViewCell
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
        case .track:
            let cell = tableView.dequeueReusableCell(withIdentifier: "TrackTableCell", for: indexPath) as! TrackTableViewCell
            cell.setPrototype(prototype: prototype as! TrackPrototype)
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
        case .artist, .tags, .lastfm, .wiki:
            size = 90
            break
        case .tracksTitle:
            size = 80
            break
        case .image:
            size = 250
            break
        case .scrobblesAndListeners:
            size = 100
            break
        case .track:
            size = 30
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
            albumViewModel.onArtistClicked(artist: type.artist)
            break
        case .track:
            let type = prototype as! TrackPrototype
            albumViewModel.onTrackClicked(artist: type.artist, track: type.track)
            break
        case .lastfm:
            let type = prototype as! LastFMPrototype
            albumViewModel.onLastFmClicked(link: type.lastFm)
            break
        default:
            break
        }
    }
}
