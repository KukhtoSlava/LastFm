//
//  AuthViewController.swift
//  iosApp
//
//  Created by Slava Kukhto on 5.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

class AuthViewController: BaseViewController {

    @IBOutlet weak var progressBar: UIProgressView!
    @IBOutlet weak var userNameEditText: UITextField!
    @IBOutlet weak var userPasswordEditText: UITextField!
    @IBOutlet weak var signInButton: UIButton!
    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var resetPasswordButton: UIButton!
    
    private lazy var loginViewModel: LoginViewModel = {
        LoginViewModelImpl()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initViews()
        loginViewModel.subscribe()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: false)
        loginViewModel.liveData.observe(lifecycle: lifecycle) { data in
            self.onUIDataReceived(uiData: data!)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    @IBAction func onSignInClicked(_ sender: Any) {
        let name = userNameEditText.text ?? ""
        let password = userPasswordEditText.text ?? ""
        let loginParams = LoginParams(name: name, password: password)
        loginViewModel.signInClicked(loginParams: loginParams)
    }
    
    @IBAction func onSignUpClicked(_ sender: Any) {
        loginViewModel.signUpClicked()
    }
    
    @IBAction func onResetPasswordClicked(_ sender: Any) {
        loginViewModel.resetPasswordClicked()
    }
    
    @objc func dismissKeyboard (_ sender: UITapGestureRecognizer) {
        userNameEditText.resignFirstResponder()
        userPasswordEditText.resignFirstResponder()
    }
    
    private func initViews(){
        progressBar.animateProgress()
        userPasswordEditText.isSecureTextEntry = true
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard (_:)))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    deinit {
        loginViewModel.unSubscribe()
    }
}

extension AuthViewController{
    
    func onUIDataReceived(uiData: UIData) {
        guard let data = uiData as? LoginUIData else {
            return
        }
        if(data is LoginUIData.Loading){
            loading(load: true)
        }else if(data is LoginUIData.Success){
            loading(load: false)
        }else if(data is LoginUIData.ErrorAuth){
            loading(load: false)
            let errorData = data as! LoginUIData.ErrorAuth
            showErrorMessage(message: errorData.message)
        }
    }
    
    private func loading(load: Bool){
        if(load) {
            progressBar.startIndefinateProgress()
            signInButton.isEnabled = false
            signUpButton.isEnabled = false
            resetPasswordButton.isEnabled = false
            userNameEditText.isEnabled = false
            userPasswordEditText.isEnabled = false
        }else {
            progressBar.stopIndefinateProgress()
            signInButton.isEnabled = true
            signUpButton.isEnabled = true
            resetPasswordButton.isEnabled = true
            userNameEditText.isEnabled = true
            userPasswordEditText.isEnabled = true
        }
    }
}
