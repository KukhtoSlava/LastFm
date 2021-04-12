//
//  IosGeneralScreenNavigator.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit
import shared

class IosGeneralScreenNavigator : ScreenNavigator {
    
    private var navigationController: UINavigationController
    
    init(navigationController: UINavigationController){
        self.navigationController = navigationController
    }
    
    func popScreen() {
        navigationController.popViewController(animated: true)
    }
    
    func pushScreen(screen: Screen, params: ScreenParams?, clearBackStack: Bool, withAnimation: Bool) {
        if(isScreenExternal(screen: screen)){
            openExternalScreen(screen: screen, params: params)
        }else{
            openInternalScreen(screen: screen, params: params, clearBackStack: clearBackStack, withAnimation: withAnimation)
        }
    }
    
    private func isScreenExternal(screen: Screen) -> Bool {
        var result = false
        switch screen {
        case Screen.splash, Screen.auth, Screen.main:
            result = false
            break
        default:
            result = true
            break
        }
        return result
    }
    
    private func createUIViewController(
            screen: Screen,
            screenParams: ScreenParams?
        ) -> UIViewController {
        let viewController: UIViewController
        switch screen {
        case Screen.splash:
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            viewController = storyboard.instantiateViewController(identifier: "splash") as SplashViewController
            break
        case Screen.auth:
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            viewController = storyboard.instantiateViewController(identifier: "auth") as AuthViewController
            break
        case Screen.main:
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            viewController = storyboard.instantiateViewController(identifier: "main") as MainTabBarViewController
            break
        default:
            viewController = UIViewController()
            break
        }
        return viewController
    }
    
    private func openInternalScreen(screen: Screen,
                                     params: ScreenParams?,
                                     clearBackStack: Bool,
                                     withAnimation: Bool){
        let viewController = createUIViewController(screen: screen, screenParams: params)
            navigationController.pushViewController(viewController, animated: withAnimation)
        if(clearBackStack){
            navigationController.viewControllers.removeAll(where: {$0 != viewController})
        }
    }
    
    private func openExternalScreen(screen: Screen,
                                    params: ScreenParams?){
        switch screen {
        case Screen.browser:
            openBrowserIfPossible(params: params)
            break
        default:
            break
        }
        
    }
        
    private func openBrowserIfPossible(params: ScreenParams?){
        guard let browserParams = params as? BrowserScreenParams else {
            return
        }
        if let url = URL(string: browserParams.url) {
            UIApplication.shared.open(url)
        }
    }
}
