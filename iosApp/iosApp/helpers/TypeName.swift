//
//  TypeName.swift
//  iosApp
//
//  Created by Slava Kukhto on 16.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol NameDescribable {
    var typeName: String { get }
    static var typeName: String { get }
}

extension NameDescribable {
    
    var typeName: String {
        return String(describing: type(of: self))
    }
    
    static var typeName: String {
        return String(describing: self)
    }
}
