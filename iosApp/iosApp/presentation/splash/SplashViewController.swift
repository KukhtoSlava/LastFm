//
//  SplashViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class SplashViewController: UIViewController {
    
    private lazy var splashViewModel: SplashViewModel = {
        SplashViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let screenNavigator: ScreenNavigator = IosGeneralScreenNavigator(navigationController: self.navigationController!)
        GeneralInjectorKt.provideScreenNavigator(screenNavigator: screenNavigator)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        splashViewModel.subscribe()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        splashViewModel.unSubscribe()
    }
}
