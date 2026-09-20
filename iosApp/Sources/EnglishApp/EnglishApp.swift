import SwiftUI
import UIKit
import Shared

// Swift 侧的全部代码就这些。UI 是 Compose 画的，Swift 只负责
// 把它作为一个 UIViewController 塞进 SwiftUI 的窗口里。

@main
struct EnglishApp: App {
    var body: some Scene {
        WindowGroup { ContentView() }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        // ignoresSafeArea 交给 Compose 整块画到边：Compose 侧自己用 WindowInsets
        // 处理刘海/Home 指示条，不在这儿先裁一刀，否则底栏会被顶上去。
        ComposeView().ignoresSafeArea()
    }
}
