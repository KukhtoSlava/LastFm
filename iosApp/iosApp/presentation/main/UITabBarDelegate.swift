//
//  UITabBarDelegate.swift
//  iosApp
//
//  Created by Slava Kukhto on 11.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol UITabBarDelegate {
    
    func onPeriodClicked()
    
    func setUpPeriod(timeStampPeriod: TimeStampPeriod)
}
