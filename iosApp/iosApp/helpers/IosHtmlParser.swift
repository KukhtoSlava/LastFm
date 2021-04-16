//
//  IosHtmlParser.swift
//  iosApp
//
//  Created by Slava Kukhto on 13.04.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class IosHtmlParser: HtmlParser {
    
    func findYouTubeLink(responseBody: String) -> String {
        do {
            var name: String = String.empty()
            let regex = try NSRegularExpression(pattern: "(http[s]?://(?:www\\.)?youtube.com\\S+)", options: NSRegularExpression.Options.caseInsensitive)
            let matches = regex.matches(in: responseBody, options: [], range: NSRange(location: 0, length: responseBody.utf16.count))
            if let match = matches.first {
                let range = match.range(at:1)
                if let swiftRange = Range(range, in: responseBody) {
                    let substring = String(responseBody[swiftRange])
                    name = substring.replacingOccurrences(of: "\"", with: "e", options: NSString.CompareOptions.literal, range: nil)
                }
            }
            return name
        } catch {
            return String.empty()
        }
    }
    
    func findArtistImage(responseBody: String) -> String{
        do {
            var name: String = String.empty()
            let regex = try NSRegularExpression(pattern: "class=\"header-new-background-image\".*\\s*.*\\s*.*\\s*content=\"(.*)\".*\\s*></div>", options: NSRegularExpression.Options.caseInsensitive)
            let matches = regex.matches(in: responseBody, options: [], range: NSRange(location: 0, length: responseBody.utf16.count))
            if let match = matches.first {
                let range = match.range(at:1)
                if let swiftRange = Range(range, in: responseBody) {
                    name = String(responseBody[swiftRange])
                }
            }
            return name
        } catch {
            return String.empty()
        }
    }
}
