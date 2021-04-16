//
//  ScrobbleTrackViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class ScrobbleTrackViewCell: UITableViewCell {
    
    @IBOutlet weak var trackImage: UIImageView!
    @IBOutlet weak var trackLabel: UILabel!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var scrobblesDateLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func setTrack(scrobblesTrack: ScrobblesTrack){
        trackLabel.text = scrobblesTrack.track
        artistLabel.text = scrobblesTrack.artist
        if(scrobblesTrack.date.isEmpty){
            scrobblesDateLabel.text = NSLocalizedString("scrobbles_now", comment: "")
        }else{
            scrobblesDateLabel.text = scrobblesTrack.date
        }
        trackImage.load(url: URL.init(string: scrobblesTrack.imagePath)!)
    }
}


extension ScrobbleTrackViewCell : NameDescribable {}
