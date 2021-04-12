//
//  MainViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class MainViewController: UIViewController {
    
    private var listener: UITabBarListener?
    
    private lazy var mainViewModel: MainViewModel = {
        MainViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        listener = self.tabBarController as? UITabBarListener
        listener?.setUITabBarDelegate(delegate: self)
        mainViewModel.setUIDataListener(uiDataListener: self)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        mainViewModel.subscribe()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        mainViewModel.unSubscribe()
    }
    
    func showErrorMessage(message: String?){
        let alert = UIAlertController(title: "Error", message: message ?? "Oops, something went wrong", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

extension MainViewController : UIDataListener{
    
    func onUIDataReceived(uiData: UIData) {
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
