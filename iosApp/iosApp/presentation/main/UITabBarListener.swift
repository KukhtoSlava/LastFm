//
//  UITabBarListener.swift
//  iosApp
//
//  Created by Slava Kukhto on 11.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol UITabBarListener {

    func setTitle(title: String)
    
    func showPeriodSheetDialog()
    
    func setUITabBarDelegate(delegate: UITabBarDelegate)
}
