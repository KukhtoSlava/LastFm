//
//  AlbumForTableMapper.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class AlbumForTableMapper {
    
    func transform(albumModel: AlbumModel) -> [CellPrototype]{
        var array: [CellPrototype] = []
        let imagePrototype = ImageCellPrototype()
        imagePrototype.imagePath = albumModel.imagePath
        array.append(imagePrototype)
        let scrobblesAndListenersPrototype = ScrobblesAndListenersPrototype()
        scrobblesAndListenersPrototype.scrobbles = Int(albumModel.scrobbles)
        scrobblesAndListenersPrototype.listeners = Int(albumModel.listeners)
        array.append(scrobblesAndListenersPrototype)
        if(!albumModel.tags.isEmpty){
            let tagsPrototype = TagsPrototype()
            tagsPrototype.tags = albumModel.tags.joined(separator: String.commaSeparator())
            array.append(tagsPrototype)
        }
        let artistPrototype = ArtistPrototype()
        artistPrototype.artist = albumModel.artist
        array.append(artistPrototype)
        if(!albumModel.tracks.isEmpty){
            let trackTitlePrototype = TracksTitlePrototype()
            array.append(trackTitlePrototype)
            for (index, element) in albumModel.tracks.enumerated() {
                let trackPrototype = TrackPrototype()
                trackPrototype.artist = albumModel.artist
                trackPrototype.track = element
                trackPrototype.number = index + 1
                array.append(trackPrototype)
            }
        }
        if(!albumModel.url.isEmpty){
            let lastFmPrototype = LastFMPrototype()
            lastFmPrototype.lastFm = albumModel.url
            array.append(lastFmPrototype)
        }
        if(!albumModel.wiki.isEmpty){
            let wikiPrototype = WikiPrototype()
            wikiPrototype.wiki = albumModel.wiki
            array.append(wikiPrototype)
        }
        return array
    }
}
