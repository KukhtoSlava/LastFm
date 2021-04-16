//
//  BasePickViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 16.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit

class BasePickViewController : BaseViewController{
    
    var tableView: UITableView!
    var refreshControl = UIRefreshControl()
    var prototyps: [CellPrototype] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        showActivityIndicatory()
    }
    
    func setTableView(table: UITableView){
        tableView = table
    }
    
    @objc func refresh(_ sender: AnyObject) {
        
    }
    
    func handleClick(prototype: CellPrototype){
        
    }
    
    private func initTable(){
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(UINib(nibName: "ImageTableViewCell", bundle: nil), forCellReuseIdentifier: "ImageTableCell")
        tableView.register(UINib(nibName: "ScrobblesAndListenersTableViewCell", bundle: nil), forCellReuseIdentifier: "ScrobblesAndListenersTableCell")
        tableView.register(UINib(nibName: "BaseTitleAndDescriptionTableViewCell", bundle: nil), forCellReuseIdentifier: "BaseTitleAndDescriptionTableCell")
        tableView.register(UINib(nibName: "TrackTitleTableViewCell", bundle: nil), forCellReuseIdentifier: "TrackTitleTableCell")
        tableView.register(UINib(nibName: "TrackTableViewCell", bundle: nil), forCellReuseIdentifier: "TrackTableCell")
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        tableView.addSubview(refreshControl)
        tableView.rowHeight = UITableView.automaticDimension
    }
}

extension BasePickViewController : UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return prototyps.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let prototype = prototyps[indexPath.row]
        let uiTableViewCell: UITableViewCell
        switch prototype.getCellType() {
        case .artist:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! ArtistPrototype)
            uiTableViewCell = cell
            break
        case .tags:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! TagsPrototype)
            uiTableViewCell = cell
            break
        case .lastfm:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! LastFMPrototype)
            uiTableViewCell = cell
            break
        case .wiki:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! WikiPrototype)
            uiTableViewCell = cell
            break
        case .fromAlbum:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! FromAlbumPrototype)
            uiTableViewCell = cell
            break
        case .youtube:
            let cell = tableView.dequeueReusableCell(withIdentifier: "BaseTitleAndDescriptionTableCell", for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! YouTubePrototype)
            uiTableViewCell = cell
            break
        case .tracksTitle:
            let cell = tableView.dequeueReusableCell(withIdentifier: "TrackTitleTableCell", for: indexPath) as! TrackTitleTableViewCell
            uiTableViewCell = cell
            break
        case .image:
            let cell = tableView.dequeueReusableCell(withIdentifier: "ImageTableCell", for: indexPath) as! ImageTableViewCell
            cell.setPrototype(prototype: prototype as! ImageCellPrototype)
            uiTableViewCell = cell
            break
        case .scrobblesAndListeners:
            let cell = tableView.dequeueReusableCell(withIdentifier: "ScrobblesAndListenersTableCell", for: indexPath) as! ScrobblesAndListenersTableViewCell
            cell.setPrototype(prototype: prototype as! ScrobblesAndListenersPrototype)
            uiTableViewCell = cell
            break
        case .track:
            let cell = tableView.dequeueReusableCell(withIdentifier: "TrackTableCell", for: indexPath) as! TrackTableViewCell
            cell.setPrototype(prototype: prototype as! TrackPrototype)
            uiTableViewCell = cell
            break
        default:
            uiTableViewCell = UITableViewCell()
            break
        }
        return uiTableViewCell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let prototype = prototyps[indexPath.row]
        let size: CGFloat
        switch prototype.getCellType() {
        case .artist, .tags, .lastfm, .wiki, .youtube, .fromAlbum:
            size = UITableView.automaticDimension
            break
        case .tracksTitle:
            size = 50
            break
        case .image:
            size = 250
            break
        case .scrobblesAndListeners:
            size = 100
            break
        case .track:
            size = 30
            break
        default:
            size = 0
            break
        }
        return size
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let prototype = prototyps[indexPath.row]
        handleClick(prototype: prototype)
    }
}
