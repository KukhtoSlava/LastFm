//
//  BaseTitleAndDescriptionTableViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

class BaseTitleAndDescriptionTableViewCell: UITableViewCell {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func setPrototype(prototype: ArtistPrototype){
        titleLabel.text = prototype.artistTitle
        descriptionLabel.text = prototype.artist
    }
    
    func setPrototype(prototype: FromAlbumPrototype){
        titleLabel.text = prototype.fromAlbumTitle
        descriptionLabel.text = prototype.fromAlbum
    }
    
    func setPrototype(prototype: TagsPrototype){
        titleLabel.text = prototype.tagsTitle
        descriptionLabel.text = prototype.tags
    }
    
    func setPrototype(prototype: LastFMPrototype){
        titleLabel.text = prototype.lastFmTitle
        descriptionLabel.text = prototype.lastFm
    }
    
    func setPrototype(prototype: YouTubePrototype){
        titleLabel.text = prototype.youTubeTitle
        descriptionLabel.text = prototype.youTube
    }
    
    func setPrototype(prototype: WikiPrototype){
        titleLabel.text = prototype.wikiTitle
        descriptionLabel.text = prototype.wiki
    }
}

extension BaseTitleAndDescriptionTableViewCell : NameDescribable {}
