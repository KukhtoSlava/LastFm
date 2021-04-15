//
//  MainViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class MainViewController: BaseViewController {
    
    private var listener: UITabBarListener?
    
    private lazy var mainViewModel: MainViewModel = {
        MainViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        listener = self.tabBarController as? UITabBarListener
        listener?.setUITabBarDelegate(delegate: self)
        lifecycle.start()
        mainViewModel.subscribe()
        mainViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        // skip
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        // skip
    }
    
    deinit {
        lifecycle.stop()
        mainViewModel.unSubscribe()
    }
}

extension MainViewController {
    
    @objc func onUIDataReceived(uiData: UIData) {
        guard let data = uiData as? MainUIData else {
            return
        }
        if(data is MainUIData.TimeData){
            let uiData = data as! MainUIData.TimeData
            listener?.setTitle(title: uiData.time)
        }else if(data is MainUIData.PeriodEvent){
            listener?.showPeriodSheetDialog()
        }
    }
}

extension MainViewController : UITabBarDelegate{
    
    func onPeriodClicked() {
        mainViewModel.onPeriodClicked()
    }
    
    func setUpPeriod(timeStampPeriod: TimeStampPeriod) {
        mainViewModel.setTimePeriod(timeStampPeriod: timeStampPeriod)
    }
}
