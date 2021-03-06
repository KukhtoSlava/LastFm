//
//  FavouriteTracksViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteTracksViewController: MainViewController {
    
    private static let favouriteTrackCell = "FavouriteTrackCell"
    private static let emptyTrackCell = "EmptyTrackCell"
    private static let cellHeight: CGFloat = 105
    
    @IBOutlet weak var favouriteTracksTableView: UITableView!
    private var refreshControl = UIRefreshControl()
    private var favouriteTrackList: [FavouriteTrack] = []
    
    private lazy var favouriteTracksViewModel: FavouriteTracksViewModel = {
        FavouriteTracksViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        favouriteTracksViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = String(format: NSLocalizedString("tracks", comment: ""))
        super.viewDidAppear(animated)
        favouriteTracksViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    private func initTable(){
        favouriteTracksTableView.delegate = self
        favouriteTracksTableView.dataSource = self
        favouriteTracksTableView.register(UINib(nibName: FavouriteTrackViewCell.typeName, bundle: nil), forCellReuseIdentifier: FavouriteTracksViewController.favouriteTrackCell)
        favouriteTracksTableView.register(UINib(nibName: EmptyFieldViewCell.typeName, bundle: nil), forCellReuseIdentifier: FavouriteTracksViewController.emptyTrackCell)
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        favouriteTracksTableView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        favouriteTracksViewModel.loadFavouriteTracks()
    }
    
    deinit {
        favouriteTracksViewModel.unSubscribe()
    }
}

extension FavouriteTracksViewController: UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return favouriteTrackList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let track = favouriteTrackList[indexPath.row]
        if(track.artist.isEmpty && track.track.isEmpty && track.imagePath.isEmpty){
            let cell = tableView.dequeueReusableCell(withIdentifier: FavouriteTracksViewController.emptyTrackCell, for: indexPath) as! EmptyFieldViewCell
            return cell
        }else{
            let cell = tableView.dequeueReusableCell(withIdentifier: FavouriteTracksViewController.favouriteTrackCell, for: indexPath) as! FavouriteTrackViewCell
            cell.setFavouriteTrack(track: track)
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return FavouriteTracksViewController.cellHeight
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let track = favouriteTrackList[indexPath.row]
        if(track.artist.isEmpty && track.track.isEmpty && track.imagePath.isEmpty){
            favouriteTracksViewModel.onMoreClicked()
        }else{
            favouriteTracksViewModel.onTrackClicked(favouriteTrack: track)
        }
    }
}

extension FavouriteTracksViewController {
    
    override func onUIDataReceived(uiData: UIData) {
        super.onUIDataReceived(uiData: uiData)
        guard let data = uiData as? FavouriteTracksUIData else {
            return
        }
        if(data is FavouriteTracksUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is FavouriteTracksUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let favouriteTracksData = data as! FavouriteTracksUIData.Success
            let footer = FavouriteTrack(track: String.empty(), artist: String.empty(), scrobbles: 0, imagePath: String.empty())
            favouriteTrackList.removeAll()
            favouriteTrackList = favouriteTracksData.favouriteTracks
            favouriteTrackList.append(footer)
            favouriteTracksTableView.reloadData()
        }else if(data is FavouriteTracksUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! FavouriteTracksUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
}
