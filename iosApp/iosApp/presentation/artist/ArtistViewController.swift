//
//  ArtistViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 14.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class ArtistViewController: BasePickViewController {
    
    @IBOutlet weak var artistTableView: UITableView!
    private var artistForTableMapper = ArtistForTableMapper()
    
    private lazy var artistViewModel: ArtistViewModel = {
        ArtistViewModelImpl()
    }()
    
    override func viewDidLoad() {
        setTableView(table: artistTableView)
        super.viewDidLoad()
        artistViewModel.subscribe()
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
    
    override func refresh(_ sender: AnyObject) {
        artistViewModel.loadArtist()
    }
    
    override func handleClick(prototype: CellPrototype){
        switch prototype.getCellType() {
        case .lastfm:
            let type = prototype as! LastFMPrototype
            artistViewModel.onLastFmClicked(link: type.lastFm)
            break
        default:
            break
        }
    }
    
    func setUpParams(artist: String){
        artistViewModel.setUpParams(artist: artist)
    }
    
    private func onUIDataReceived(uiData: UIData) {
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
        tableView.reloadData()
    }
    
    deinit {
        artistViewModel.unSubscribe()
    }
}
