//
//  UIProgressViewExtension.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import UIKit

extension UIProgressView {
    
    private struct Holder {
        static var _progressFull:Bool = false
        static var _completeLoading:Bool = false;
    }

    var progressFull:Bool {
        get {
            return Holder._progressFull
        }
        set(newValue) {
            Holder._progressFull = newValue
        }
    }

    var completeLoading:Bool {
        get {
            return Holder._completeLoading
        }
        set(newValue) {
            Holder._completeLoading = newValue
        }
    }

    func animateProgress(){
        if(completeLoading){
            return
        }
        UIView.animate(withDuration: 1, animations: {
            self.setProgress(self.progressFull ? 1.0 : 0.0, animated: true)
        })

        progressFull = !progressFull;

        DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
            self.animateProgress();
        }
    }

    func startIndefinateProgress(){
        isHidden = false
        completeLoading = false
        animateProgress()
    }

    func stopIndefinateProgress(){
        completeLoading = true
        isHidden = true
    }
}
