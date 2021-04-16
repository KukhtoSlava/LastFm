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
    
    var imagePath = String.empty()
    
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
    
    var artistTitle = String(format: NSLocalizedString("artist", comment: ""))
    var artist = String.empty()
    
    override func getCellType() -> CellType {
        return .artist
    }
}

class FromAlbumPrototype: CellPrototype {
    
    var fromAlbumTitle = String(format: NSLocalizedString("from_album", comment: ""))
    var fromAlbum = String.empty()
    var artist = String.empty()
    
    override func getCellType() -> CellType {
        return .fromAlbum
    }
}

class TagsPrototype : CellPrototype {
    
    var tagsTitle = String(format: NSLocalizedString("tags", comment: ""))
    var tags = String.empty()
    
    override func getCellType() -> CellType {
        return .tags
    }
}

class LastFMPrototype : CellPrototype {
    
    var lastFmTitle = String(format: NSLocalizedString("last_fm", comment: ""))
    var lastFm = String.empty()
    
    override func getCellType() -> CellType {
        return .lastfm
    }
}

class YouTubePrototype: CellPrototype {
    
    var youTubeTitle = String(format: NSLocalizedString("youtube", comment: ""))
    var youTube = String.empty()
    
    override func getCellType() -> CellType {
        return .youtube
    }
}

class WikiPrototype : CellPrototype{
    
    var wikiTitle = String(format: NSLocalizedString("wiki", comment: ""))
    var wiki = String.empty()
    
    override func getCellType() -> CellType {
        return .wiki
    }
}

class TracksTitlePrototype : CellPrototype{
    
    var title = String(format: NSLocalizedString("tracks", comment: ""))
    
    override func getCellType() -> CellType {
        return .tracksTitle
    }
}

class TrackPrototype : CellPrototype {
    
    var number = 0
    var track = String.empty()
    var artist = String.empty()
    
    override func getCellType() -> CellType {
        return .track
    }
}
