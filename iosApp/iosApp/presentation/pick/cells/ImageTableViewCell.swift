//
//  ImageTableViewCell.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

class ImageTableViewCell: UITableViewCell {
    
    @IBOutlet weak var pictureImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func setPrototype(prototype: ImageCellPrototype){
        pictureImageView.load(url: URL.init(string: prototype.imagePath)!)
    }
}
