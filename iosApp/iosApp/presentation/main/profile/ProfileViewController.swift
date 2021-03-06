//
//  ProfileViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 8.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class ProfileViewController: MainViewController {
    
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var profileImage: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var scrobblesCountLabel: UILabel!
    @IBOutlet weak var cityLabel: UILabel!
    @IBOutlet weak var registrationDateLabel: UILabel!
    private var refreshControl = UIRefreshControl()
    
    private lazy var profileViewModel: ProfileViewModel = {
        ProfileViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initScrollView()
        profileViewModel.subscribe()
        showActivityIndicatory()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        navigationController?.navigationBar.topItem?.title = String(format: NSLocalizedString("profile", comment: ""))
        super.viewDidAppear(animated)
        profileViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    private func initScrollView(){
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        scrollView.refreshControl = refreshControl
    }
    
    @IBAction func logOutClicked(_ sender: Any) {
        profileViewModel.logOutClicked()
    }
    
    @objc func refresh(_ sender: AnyObject) {
        profileViewModel.loadProfile()
    }
    
    deinit {
        profileViewModel.unSubscribe()
    }
}

extension ProfileViewController{
    
    override func onUIDataReceived(uiData: UIData) {
        super.onUIDataReceived(uiData: uiData)
        guard let data = uiData as? ProfileUIData else {
            return
        }
        if(data is ProfileUIData.Loading){
            refreshControl.beginRefreshing()
        }else if(data is ProfileUIData.Success){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let profileData = data as! ProfileUIData.Success
            setProfile(userProfile: profileData.profile)
        }else if(data is ProfileUIData.Error){
            hideActivityIndicatory()
            refreshControl.endRefreshing()
            let errorData = data as! ProfileUIData.Error
            showErrorMessage(message: errorData.message)
        }
    }
    
    private func setProfile(userProfile: UserProfile){
        profileImage.load(url: URL.init(string: userProfile.profileImagePath!)!)
        userNameLabel.text = userProfile.userName
        scrobblesCountLabel.text = String(userProfile.scrobbles)
        cityLabel.text  = userProfile.country
        registrationDateLabel.text = TimeStampHelper.convertRegistrationDate(date: userProfile.registrationDate)
    }
}
