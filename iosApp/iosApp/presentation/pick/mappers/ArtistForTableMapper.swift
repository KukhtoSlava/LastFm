//
//  ArtistForTableMapper.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ArtistForTableMapper {
    
    func transform(artistModel: ArtistModel) -> [CellPrototype]{
        var array: [CellPrototype] = []
        let imagePrototype = ImageCellPrototype()
        imagePrototype.imagePath = artistModel.imagePath
        array.append(imagePrototype)
        let scrobblesAndListenersPrototype = ScrobblesAndListenersPrototype()
        scrobblesAndListenersPrototype.scrobbles = Int(artistModel.scrobbles)
        scrobblesAndListenersPrototype.listeners = Int(artistModel.listeners)
        array.append(scrobblesAndListenersPrototype)
        if(!artistModel.tags.isEmpty){
            let tagsPrototype = TagsPrototype()
            tagsPrototype.tags = artistModel.tags.joined(separator:", ")
            array.append(tagsPrototype)
        }
        if(!artistModel.url.isEmpty){
            let lastFmPrototype = LastFMPrototype()
            lastFmPrototype.lastFm = artistModel.url
            array.append(lastFmPrototype)
        }
        if(!artistModel.wiki.isEmpty){
            let wikiPrototype = WikiPrototype()
            wikiPrototype.wiki = artistModel.wiki
            array.append(wikiPrototype)
        }
        return array
    }
}
