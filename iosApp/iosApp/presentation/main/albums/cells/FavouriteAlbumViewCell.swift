//
//  FavouriteAlbumViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 9.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteAlbumViewCell: UICollectionViewCell {

    @IBOutlet weak var albumImage: UIImageView!
    @IBOutlet weak var albumLabel: UILabel!
    @IBOutlet weak var scrobblesLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func setFavouriteAlbum(favouriteAlbum: FavouriteAlbum){
        albumImage.load(url: URL.init(string: favouriteAlbum.imagePath)!)
        albumLabel.text = favouriteAlbum.album
        scrobblesLabel.text = "Scrobbles \(favouriteAlbum.scrobbles)"
    }
}
