//
//  ScrobblesAndListenersTableViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

class ScrobblesAndListenersTableViewCell: UITableViewCell {
    
    @IBOutlet weak var scrobblesLabe: UILabel!
    @IBOutlet weak var listenersLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func setPrototype(prototype: ScrobblesAndListenersPrototype){
        scrobblesLabe.text = "\(prototype.scrobbles)"
        listenersLabel.text = "\(prototype.listeners)"
    }
}

extension ScrobblesAndListenersTableViewCell : NameDescribable {}
