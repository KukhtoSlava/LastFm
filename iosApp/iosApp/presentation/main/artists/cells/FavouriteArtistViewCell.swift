//
//  FavouriteArtistViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 9.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteArtistViewCell: UICollectionViewCell {
    
    
    @IBOutlet weak var imageArtist: UIImageView!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var scrobblesLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func setFavouriteArtist(favouriteArtist: FavouriteArtist){
        imageArtist.load(url: URL.init(string: favouriteArtist.imagePath)!)
        artistLabel.text = favouriteArtist.artist
        scrobblesLabel.text = "Scrobbles \(favouriteArtist.scrobbles)"
    }
}
