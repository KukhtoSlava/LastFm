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
    private let activityView = UIActivityIndicatorView(style: .whiteLarge)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        lifecycle.start()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        lifecycle.stop()
    }
    
    func showErrorMessage(message: String?){
        let alert = UIAlertController(title: "Error", message: message ?? "Oops, something went wrong", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    func showActivityIndicatory() {
        activityView.center = self.view.center
        self.view.addSubview(activityView)
        activityView.startAnimating()
    }
    
    func hideActivityIndicatory() {
        activityView.stopAnimating()
        self.activityView.removeFromSuperview()
    }
}
