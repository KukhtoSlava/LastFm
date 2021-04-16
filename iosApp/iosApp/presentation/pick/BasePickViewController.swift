//
//  BasePickViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 16.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit

class BasePickViewController : BaseViewController {
    
    private static let imageTableCell = "ImageTableCell"
    private static let scrobblesAndListenersTableCell = "ScrobblesAndListenersTableCell"
    private static let baseTitleAndDescriptionTableCell = "BaseTitleAndDescriptionTableCell"
    private static let trackTitleTableCell = "TrackTitleTableCell"
    private static let trackTableCell = "TrackTableCell"
    
    private static let trackTitleCellHeight: CGFloat = 50
    private static let imageCellHeight: CGFloat = 250
    private static let scrobblesAndListenersCellHeight: CGFloat = 100
    private static let trackCellHeight: CGFloat = 30
    private static let defaultHeight: CGFloat = 0
    
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
        tableView.register(UINib(nibName: ImageTableViewCell.typeName, bundle: nil), forCellReuseIdentifier: BasePickViewController.imageTableCell)
        tableView.register(UINib(nibName: ScrobblesAndListenersTableViewCell.typeName, bundle: nil), forCellReuseIdentifier: BasePickViewController.scrobblesAndListenersTableCell)
        tableView.register(UINib(nibName: BaseTitleAndDescriptionTableViewCell.typeName, bundle: nil), forCellReuseIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell)
        tableView.register(UINib(nibName: TrackTitleTableViewCell.typeName, bundle: nil), forCellReuseIdentifier: BasePickViewController.trackTitleTableCell)
        tableView.register(UINib(nibName: TrackTableViewCell.typeName, bundle: nil), forCellReuseIdentifier: BasePickViewController.trackTableCell)
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
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! ArtistPrototype)
            uiTableViewCell = cell
            break
        case .tags:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! TagsPrototype)
            uiTableViewCell = cell
            break
        case .lastfm:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! LastFMPrototype)
            uiTableViewCell = cell
            break
        case .wiki:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! WikiPrototype)
            uiTableViewCell = cell
            break
        case .fromAlbum:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! FromAlbumPrototype)
            uiTableViewCell = cell
            break
        case .youtube:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.baseTitleAndDescriptionTableCell, for: indexPath) as! BaseTitleAndDescriptionTableViewCell
            cell.setPrototype(prototype: prototype as! YouTubePrototype)
            uiTableViewCell = cell
            break
        case .tracksTitle:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.trackTitleTableCell, for: indexPath) as! TrackTitleTableViewCell
            uiTableViewCell = cell
            break
        case .image:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.imageTableCell, for: indexPath) as! ImageTableViewCell
            cell.setPrototype(prototype: prototype as! ImageCellPrototype)
            uiTableViewCell = cell
            break
        case .scrobblesAndListeners:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.scrobblesAndListenersTableCell, for: indexPath) as! ScrobblesAndListenersTableViewCell
            cell.setPrototype(prototype: prototype as! ScrobblesAndListenersPrototype)
            uiTableViewCell = cell
            break
        case .track:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasePickViewController.trackTableCell, for: indexPath) as! TrackTableViewCell
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
            size = BasePickViewController.trackTitleCellHeight
            break
        case .image:
            size = BasePickViewController.imageCellHeight
            break
        case .scrobblesAndListeners:
            size = BasePickViewController.scrobblesAndListenersCellHeight
            break
        case .track:
            size = BasePickViewController.trackCellHeight
            break
        default:
            size = BasePickViewController.defaultHeight
            break
        }
        return size
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let prototype = prototyps[indexPath.row]
        handleClick(prototype: prototype)
    }
}
