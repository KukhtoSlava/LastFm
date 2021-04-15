//
//  ArtistViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 14.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class ArtistViewController: BaseViewController {
    
    @IBOutlet weak var artistTableView: UITableView!
    private var refreshControl = UIRefreshControl()
    private var prototyps: [CellPrototype] = []
    private var artistForTableMapper = ArtistForTableMapper()
    
    private lazy var artistViewModel: ArtistViewModel = {
        ArtistViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        artistViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        artistViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    func setUpParams(artist: String){
        artistViewModel.setUpParams(artist: artist)
    }
    
    private func initTable(){
        artistTableView.delegate = self
        artistTableView.dataSource = self
        artistTableView.register(UINib(nibName: "ImageTableViewCell", bundle: nil), forCellReuseIdentifier: "ImageTableCell")
        artistTableView.register(UINib(nibName: "ScrobblesAndListenersTableViewCell", bundle: nil), forCellReuseIdentifier: "ScrobblesAndListenersTableCell")
        artistTableView.register(UINib(nibName: "BaseTitleAndDescriptionTableViewCell", bundle: nil), forCellReuseIdentifier: "BaseTitleAndDescriptionTableCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        artistTableView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        artistViewModel.loadArtist()
    }
    
    deinit {
        artistViewModel.unSubscribe()
    }
}

extension ArtistViewController{
    
    func onUIDataReceived(uiData: UIData) {
        guard let data = uiData as? ArtistUIData else {
            return
        }
        if(data is ArtistUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is ArtistUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let artistData = data as! ArtistUIData.Success
            setUpArtist(artistModel: artistData.artist)
        }else if(data is ArtistUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! ArtistUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
    
    private func setUpArtist(artistModel: ArtistModel){
        navigationController?.navigationBar.topItem?.title = artistModel.artist
        prototyps = artistForTableMapper.transform(artistModel: artistModel)
        artistTableView.reloadData()
    }
}

extension ArtistViewController : UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return prototyps.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let prototype = prototyps[indexPath.row]
        let uiTableViewCell: UITableViewCell
        switch prototype.getCellType() {
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
        case .tags, .lastfm, .wiki, .youtube:
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
        case .lastfm:
            let type = prototype as! LastFMPrototype
            artistViewModel.onLastFmClicked(link: type.lastFm)
            break
        default:
            break
        }
    }
}

