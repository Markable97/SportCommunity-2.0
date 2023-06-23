//
//  ContentView.swift
//  SportCommunityIOS
//
//  Created by Глушко Марк on 23.06.2023.
//

import SwiftUI
import shared

struct ContentView: View {

    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text(Greeting().greet())
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
