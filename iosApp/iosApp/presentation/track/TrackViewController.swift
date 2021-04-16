//
//  TrackViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 14.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class TrackViewController: BasePickViewController {
    
    @IBOutlet weak var trackTableView: UITableView!
    private var trackForTableMapper = TrackForTableMapper()
    
    private lazy var trackViewModel: TrackViewModel = {
        TrackViewModelImpl()
    }()
    
    override func viewDidLoad() {
        setTableView(table: trackTableView)
        super.viewDidLoad()
        trackViewModel.subscribe()
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
    
    override func refresh(_ sender: AnyObject) {
        trackViewModel.loadTrack()
    }
    
    override func handleClick(prototype: CellPrototype){
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
    
    func setUpParams(track: String, artist: String){
        trackViewModel.setUpParams(track: track, artist: artist)
    }
    
    private func onUIDataReceived(uiData: UIData) {
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
    
    deinit {
        trackViewModel.unSubscribe()
    }
}
