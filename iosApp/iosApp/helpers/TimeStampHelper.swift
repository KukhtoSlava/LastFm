//
//  TimeStampHelper.swift
//  iosApp
//
//  Created by Slava Kukhto on 12.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

class TimeStampHelper {
    
    static func convertRegistrationDate(date: Int64) -> String{
        let dateFormatterPrint = DateFormatter()
        dateFormatterPrint.dateFormat = "dd.MM.yyyy"
        let myNSDate = Date(timeIntervalSince1970: TimeInterval(date / 1000))
        return dateFormatterPrint.string(from: myNSDate)
    }
}
