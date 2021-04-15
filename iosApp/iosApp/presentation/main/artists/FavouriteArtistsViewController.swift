//
//  FavouriteArtistsViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteArtistsViewController: MainViewController {
    
    @IBOutlet weak var favouriteArtistCollectionView: UICollectionView!
    private var refreshControl = UIRefreshControl()
    private var favouriteArtistList: [FavouriteArtist] = []
    
    private lazy var favouriteArtistsViewModel: FavouriteArtistsViewModel = {
        FavouriteArtistsViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initCollection()
        favouriteArtistsViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = "Artists"
        super.viewDidAppear(animated)
        favouriteArtistsViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    private func initCollection(){
        favouriteArtistCollectionView.delegate = self
        favouriteArtistCollectionView.dataSource = self
        favouriteArtistCollectionView.register(UINib(nibName: "FavouriteArtistViewCell", bundle: nil), forCellWithReuseIdentifier: "FavouriteArtistCell")
        favouriteArtistCollectionView.register(UINib(nibName: "EmptyFieldCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "EmptyFieldCollectionCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        favouriteArtistCollectionView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        favouriteArtistsViewModel.loadFavouriteArtists()
    }
    
    deinit {
        favouriteArtistsViewModel.unSubscribe()
    }
}

extension FavouriteArtistsViewController: UICollectionViewDataSource, UICollectionViewDelegate{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return favouriteArtistList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let artist = favouriteArtistList[indexPath.row]
        if(artist.artist.isEmpty && artist.imagePath.isEmpty){
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "EmptyFieldCollectionCell", for: indexPath) as! EmptyFieldCollectionViewCell
            return cell
        }else{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FavouriteArtistCell", for: indexPath) as! FavouriteArtistViewCell
            cell.setFavouriteArtist(favouriteArtist: artist)
            return cell
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let artist = favouriteArtistList[indexPath.row]
        if(artist.artist.isEmpty && artist.imagePath.isEmpty){
            favouriteArtistsViewModel.onMoreClicked()
        }else{
            favouriteArtistsViewModel.onArtistClicked(favouriteArtist: artist)
        }
    }
}

extension FavouriteArtistsViewController {
    
    override func onUIDataReceived(uiData: UIData) {
        super.onUIDataReceived(uiData: uiData)
        guard let data = uiData as? FavouriteArtistsUIData else {
            return
        }
        if(data is FavouriteArtistsUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is FavouriteArtistsUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let favouriteAlbumData = data as! FavouriteArtistsUIData.Success
            let footer = FavouriteArtist(artist: "", scrobbles: 0, imagePath: "")
            favouriteArtistList.removeAll()
            favouriteArtistList = favouriteAlbumData.favouriteArtists
            favouriteArtistList.append(footer)
            favouriteArtistCollectionView.reloadData()
        }else if(data is FavouriteArtistsUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! FavouriteArtistsUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
}
