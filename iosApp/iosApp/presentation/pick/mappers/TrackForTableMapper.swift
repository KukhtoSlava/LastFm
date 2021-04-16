//
//  TrackForTableMapper.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class TrackForTableMapper{
    
    func transform(trackModel: TrackModel) -> [CellPrototype] {
        var array: [CellPrototype] = []
        let imagePrototype = ImageCellPrototype()
        imagePrototype.imagePath = trackModel.imagePath
        array.append(imagePrototype)
        let scrobblesAndListenersPrototype = ScrobblesAndListenersPrototype()
        scrobblesAndListenersPrototype.scrobbles = Int(trackModel.scrobbles)
        scrobblesAndListenersPrototype.listeners = Int(trackModel.listeners)
        array.append(scrobblesAndListenersPrototype)
        if(!trackModel.tags.isEmpty){
            let tagsPrototype = TagsPrototype()
            tagsPrototype.tags = trackModel.tags.joined(separator: String.commaSeparator())
            array.append(tagsPrototype)
        }
        let artistPrototype = ArtistPrototype()
        artistPrototype.artist = trackModel.artist
        array.append(artistPrototype)
        if(!trackModel.album.isEmpty){
            let fromAlbumPrototype = FromAlbumPrototype()
            fromAlbumPrototype.fromAlbum = trackModel.album
            fromAlbumPrototype.artist = trackModel.artist
            array.append(fromAlbumPrototype)
        }
        if(!trackModel.youtubeLink.isEmpty){
            let youTubePrototype = YouTubePrototype()
            youTubePrototype.youTube = trackModel.youtubeLink
            array.append(youTubePrototype)
        }
        if(!trackModel.url.isEmpty){
            let lastFmPrototype = LastFMPrototype()
            lastFmPrototype.lastFm = trackModel.url
            array.append(lastFmPrototype)
        }
        if(!trackModel.wiki.isEmpty){
            let wikiPrototype = WikiPrototype()
            wikiPrototype.wiki = trackModel.wiki
            array.append(wikiPrototype)
        }
        return array
    }
}
