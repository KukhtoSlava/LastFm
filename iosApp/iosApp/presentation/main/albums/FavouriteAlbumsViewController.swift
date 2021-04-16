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
    
    private static let favouriteAlbumCell = "FavouriteAlbumCell"
    private static let emptyFieldCollectionCell = "EmptyFieldCollectionCell"
    
    @IBOutlet weak var albumsCollectionView: UICollectionView!
    private var refreshControl = UIRefreshControl()
    private var favouriteAlbumsList: [FavouriteAlbum] = []
    
    private lazy var favouriteAlbumsViewModel: FavouriteAlbumsViewModel = {
        FavouriteAlbumsViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initCollection()
        favouriteAlbumsViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = String(format: NSLocalizedString("albums", comment: ""))
        super.viewDidAppear(animated)
        favouriteAlbumsViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    private func initCollection(){
        albumsCollectionView.delegate = self
        albumsCollectionView.dataSource = self
        albumsCollectionView.register(UINib(nibName: FavouriteAlbumViewCell.typeName, bundle: nil), forCellWithReuseIdentifier: FavouriteAlbumsViewController.favouriteAlbumCell)
        albumsCollectionView.register(UINib(nibName: EmptyFieldCollectionViewCell.typeName, bundle: nil), forCellWithReuseIdentifier: FavouriteAlbumsViewController.emptyFieldCollectionCell)
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        albumsCollectionView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        favouriteAlbumsViewModel.loadFavouriteAlbums()
    }
    
    deinit {
        favouriteAlbumsViewModel.unSubscribe()
    }
}

extension FavouriteAlbumsViewController: UICollectionViewDataSource, UICollectionViewDelegate{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return favouriteAlbumsList.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let album = favouriteAlbumsList[indexPath.row]
        if(album.album.isEmpty && album.imagePath.isEmpty){
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: FavouriteAlbumsViewController.emptyFieldCollectionCell, for: indexPath) as! EmptyFieldCollectionViewCell
            return cell
        }else{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: FavouriteAlbumsViewController.favouriteAlbumCell, for: indexPath) as! FavouriteAlbumViewCell
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
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let favouriteAlbumData = data as! FavouriteAlbumsUIData.Success
            let footer = FavouriteAlbum(artist: String.empty(), album: String.empty(), scrobbles: 0, imagePath: String.empty())
            favouriteAlbumsList.removeAll()
            favouriteAlbumsList = favouriteAlbumData.favouriteAlbums
            favouriteAlbumsList.append(footer)
            albumsCollectionView.reloadData()
        }else if(data is FavouriteAlbumsUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! FavouriteAlbumsUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
}
