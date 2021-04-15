//
//  TrackTableViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

class TrackTableViewCell: UITableViewCell {
    
    @IBOutlet weak var trackLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func setPrototype(prototype: TrackPrototype){
        trackLabel.text = "\(prototype.number). \(prototype.track)"
    }
}
