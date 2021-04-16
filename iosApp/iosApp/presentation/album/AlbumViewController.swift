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

class AlbumViewController : BasePickViewController {
    
    @IBOutlet weak var albumTableView: UITableView!
    private var albumForTableMapper = AlbumForTableMapper()
    
    private lazy var albumViewModel: AlbumViewModel = {
        AlbumViewModelImpl()
    }()
    
    override func viewDidLoad() {
        setTableView(table: albumTableView)
        super.viewDidLoad()
        albumViewModel.subscribe()
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
    
    override func refresh(_ sender: AnyObject) {
        albumViewModel.loadAlbum()
    }
    
    override func handleClick(prototype: CellPrototype){
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
    
    func setUpParams(album: String, artist: String){
        albumViewModel.setUpParams(album: album, artist: artist)
    }
    
    private func onUIDataReceived(uiData: UIData) {
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
    
    deinit {
        albumViewModel.unSubscribe()
    }
}
