//
//  MainTabBarViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 11.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit
import shared

class MainTabBarViewController: UITabBarController, UITabBarListener {
    
    private var periodButton: UIBarButtonItem?
    private var tabBarDelegate: UITabBarDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initNavigationBar()
    }
    
    func setTitle(title: String) {
        periodButton?.title = title
    }
    
    func showPeriodSheetDialog() {
        showPeriodSheet()
    }
    
    func setUITabBarDelegate(delegate: UITabBarDelegate) {
        tabBarDelegate = delegate
    }
    
    private func initNavigationBar(){
        navigationController?.navigationBar.titleTextAttributes = [ NSAttributedString.Key.font: UIFont(name: "HelveticaNeue-Bold", size: 20)!, NSAttributedString.Key.foregroundColor: UIColor.white]
        navigationController?.setNavigationBarHidden(false, animated: false)
        periodButton = UIBarButtonItem(title: "", style: .plain, target: self, action: #selector(addTapped))
        self.navigationItem.rightBarButtonItem = periodButton
        self.navigationItem.rightBarButtonItem?.tintColor = UIColor.white
    }
    
    private func showPeriodSheet(){
        let alert = UIAlertController(title: "Period", message: nil, preferredStyle: .actionSheet)
        let titleAttributes = [NSAttributedString.Key.font: UIFont(name: "HelveticaNeue-Bold", size: 20)!, NSAttributedString.Key.foregroundColor: UIColor.systemBlue]
        let titleString = NSAttributedString(string: "Period", attributes: titleAttributes)
        alert.setValue(titleString, forKey: "attributedTitle")
        alert.addAction(UIAlertAction(title: "Overall", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.overall)}))
        alert.addAction(UIAlertAction(title: "7 Days", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.days7)}))
        alert.addAction(UIAlertAction(title: "1 Month", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.month1)}))
        alert.addAction(UIAlertAction(title: "3 Months", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.month3)}))
        alert.addAction(UIAlertAction(title: "6 Months", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.month6)}))
        alert.addAction(UIAlertAction(title: "12 Months", style: .default, handler: {_ in self.tabBarDelegate?.setUpPeriod(timeStampPeriod: TimeStampPeriod.month12)}))
        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        self.present(alert, animated: true)
    }
    
    @objc func addTapped(_ sender: AnyObject){
        tabBarDelegate?.onPeriodClicked()
    }
    
    deinit {
        tabBarDelegate = nil
    }
}
