//
//  BaseViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 14.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class BaseViewController: UIViewController {
    
    var lifecycle = KLifecycle()

    override func viewDidLoad() {
        super.viewDidLoad()
        lifecycle.start()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        lifecycle.stop()
    }
}
