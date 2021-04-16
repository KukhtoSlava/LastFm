//
//  FavouriteTrackViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 9.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class FavouriteTrackViewCell: UITableViewCell {
    
    @IBOutlet weak var imageTrack: UIImageView!
    @IBOutlet weak var trackLabel: UILabel!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var scrobblesLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func setFavouriteTrack(track: FavouriteTrack){
        trackLabel.text = track.track
        artistLabel.text = track.artist
        scrobblesLabel.text = String(format: NSLocalizedString("scrobbles_count", comment: ""), track.scrobbles)
        imageTrack.load(url: URL.init(string: track.imagePath)!)
    }
}

extension FavouriteTrackViewCell : NameDescribable {}
