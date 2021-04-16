//
//  ScrobblesViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class ScrobblesViewController: MainViewController {
    
    private static let scrobbleTrackCell = "ScrobbleTrackCell"
    private static let emptyTrackCell = "EmptyTrackCell"
    private static let cellHeight: CGFloat = 105
    
    @IBOutlet weak var scrobblesTableView: UITableView!
    private var refreshControl = UIRefreshControl()
    private var scrobblesTrackList: [ScrobblesTrack] = []
    
    private lazy var scrobblesViewModel: ScrobblesViewModel = {
        ScrobblesViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initTable()
        scrobblesViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = NSLocalizedString("scrobbles", comment: "")
        super.viewDidAppear(animated)
        scrobblesViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    private func initTable(){
        scrobblesTableView.delegate = self
        scrobblesTableView.dataSource = self
        scrobblesTableView.register(UINib(nibName: ScrobbleTrackViewCell.typeName, bundle: nil), forCellReuseIdentifier: ScrobblesViewController.scrobbleTrackCell)
        scrobblesTableView.register(UINib(nibName: EmptyFieldViewCell.typeName, bundle: nil), forCellReuseIdentifier: ScrobblesViewController.emptyTrackCell)
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        scrobblesTableView.addSubview(refreshControl)
    }
    
    @objc func refresh(_ sender: AnyObject) {
        scrobblesViewModel.loadScrobblesTracks()
    }
    
    deinit {
        scrobblesViewModel.unSubscribe()
    }
}

extension ScrobblesViewController: UITableViewDataSource, UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return scrobblesTrackList.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return ScrobblesViewController.cellHeight
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let track = scrobblesTrackList[indexPath.row]
        if(track.artist.isEmpty && track.track.isEmpty && track.imagePath.isEmpty){
            let cell = tableView.dequeueReusableCell(withIdentifier: ScrobblesViewController.emptyTrackCell, for: indexPath) as! EmptyFieldViewCell
            return cell
        }else{
            let cell = tableView.dequeueReusableCell(withIdentifier: ScrobblesViewController.scrobbleTrackCell, for: indexPath) as! ScrobbleTrackViewCell
            cell.setTrack(scrobblesTrack: track)
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let track = scrobblesTrackList[indexPath.row]
        if(track.artist.isEmpty && track.track.isEmpty && track.imagePath.isEmpty){
            scrobblesViewModel.onMoreClicked()
        }else{
            scrobblesViewModel.onTrackClicked(scrobblesTrack: track)
        }
    }
}

extension ScrobblesViewController {
    
    override func onUIDataReceived(uiData: UIData) {
        super.onUIDataReceived(uiData: uiData)
        guard let data = uiData as? ScrobblesUIData else {
            return
        }
        if(data is ScrobblesUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is ScrobblesUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let scrobblesData = data as! ScrobblesUIData.Success
            let footer = ScrobblesTrack(track: String.empty(), artist: String.empty(), imagePath: String.empty(), date: String.empty())
            scrobblesTrackList.removeAll()
            scrobblesTrackList = scrobblesData.scrobblesTracks
            scrobblesTrackList.append(footer)
            scrobblesTableView.reloadData()
        }else if(data is ScrobblesUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! ScrobblesUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
}
