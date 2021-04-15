//
//  CellPrototype.swift
//  iosApp
//
//  Created by Slava Kukhto on 15.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

class CellPrototype {

    func getCellType() -> CellType {
        return .empty
    }
}

class ImageCellPrototype: CellPrototype {
    
    var imagePath = ""
    
    override func getCellType() -> CellType {
        return .image
    }
}

class ScrobblesAndListenersPrototype: CellPrototype {
    
    var scrobbles = 0
    var listeners = 0
    
    override func getCellType() -> CellType {
        return .scrobblesAndListeners
    }
}

class ArtistPrototype: CellPrototype {
    
    var artistTitle = "Artist"
    var artist = ""
    
    override func getCellType() -> CellType {
        return .artist
    }
}

class FromAlbumPrototype: CellPrototype {
    
    var fromAlbumTitle = "FromAlbum"
    var fromAlbum = ""
    var artist = ""
    
    override func getCellType() -> CellType {
        return .fromAlbum
    }
}

class TagsPrototype : CellPrototype {
    
    var tagsTitle = "Tags"
    var tags = ""
    
    override func getCellType() -> CellType {
        return .tags
    }
}

class LastFMPrototype : CellPrototype {
    
    var lastFmTitle = "Last FM"
    var lastFm = ""
    
    override func getCellType() -> CellType {
        return .lastfm
    }
}

class YouTubePrototype: CellPrototype {
    
    var youTubeTitle = "YouTube"
    var youTube = ""
    
    override func getCellType() -> CellType {
        return .youtube
    }
}

class WikiPrototype : CellPrototype{
    
    var wikiTitle = "Wiki"
    var wiki = ""
    
    override func getCellType() -> CellType {
        return .wiki
    }
}

class TracksTitlePrototype : CellPrototype{
    
    var title = "Tracks"
    
    override func getCellType() -> CellType {
        return .tracksTitle
    }
}

class TrackPrototype : CellPrototype {
    
    var number = 0
    var track = ""
    var artist = ""
    
    override func getCellType() -> CellType {
        return .track
    }
}
