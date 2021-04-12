//
//  FavouriteAlbumsViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteAlbumsViewController: MainViewController {
    
    @IBOutlet weak var albumsCollectionView: UICollectionView!
    private var refreshControl = UIRefreshControl()
    private var favouriteAlbumsList: [FavouriteAlbum] = []
    
    private lazy var favouriteAlbumsViewModel: FavouriteAlbumsViewModel = {
        FavouriteAlbumsViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        favouriteAlbumsViewModel.setUIDataListener(uiDataListener: self)
        initCollection()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = "Albums"
        super.viewDidAppear(animated)
        favouriteAlbumsViewModel.subscribe()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        favouriteAlbumsViewModel.unSubscribe()
    }
    
    private func initCollection(){
        albumsCollectionView.delegate = self
        albumsCollectionView.dataSource = self
        albumsCollectionView.register(UINib(nibName: "FavouriteAlbumViewCell", bundle: nil), forCellWithReuseIdentifier: "FavouriteAlbumCell")
        albumsCollectionView.register(UINib(nibName: "EmptyFieldCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "EmptyFieldCollectionCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        albumsCollectionView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        favouriteAlbumsViewModel.loadFavouriteAlbums()
    }
}

extension FavouriteAlbumsViewController: UICollectionViewDataSource, UICollectionViewDelegate{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return favouriteAlbumsList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let album = favouriteAlbumsList[indexPath.row]
        if(album.album.isEmpty && album.imagePath.isEmpty){
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "EmptyFieldCollectionCell", for: indexPath) as! EmptyFieldCollectionViewCell
            return cell
        }else{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FavouriteAlbumCell", for: indexPath) as! FavouriteAlbumViewCell
            cell.setFavouriteAlbum(favouriteAlbum: album)
            return cell
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let album = favouriteAlbumsList[indexPath.row]
        if(album.album.isEmpty && album.imagePath.isEmpty){
            favouriteAlbumsViewModel.onMoreClicked()
        }else{
            favouriteAlbumsViewModel.onAlbumClicked(favouriteAlbum: album)
        }
    }
}

extension FavouriteAlbumsViewController {
    
    override func onUIDataReceived(uiData: UIData) {
        super.onUIDataReceived(uiData: uiData)
        guard let data = uiData as? FavouriteAlbumsUIData else {
            return
        }
        if(data is FavouriteAlbumsUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is FavouriteAlbumsUIData.Success){
            refreshControl.endRefreshing()
            let favouriteAlbumData = data as! FavouriteAlbumsUIData.Success
            let footer = FavouriteAlbum(album: "", scrobbles: 0, imagePath: "")
            favouriteAlbumsList.removeAll()
            favouriteAlbumsList = favouriteAlbumData.favouriteAlbums
            favouriteAlbumsList.append(footer)
            albumsCollectionView.reloadData()
        }else if(data is FavouriteAlbumsUIData.Error){
            refreshControl.endRefreshing()
            let errorData = data as! FavouriteAlbumsUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
}
