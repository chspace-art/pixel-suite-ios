import SwiftUI

// 靶机 App：纯 SwiftUI，一行 Kotlin 都没有。
//
// 它存在的意义只有一个——把"装不上"这件事的归因范围缩小。
// 如果它能装能开，而 EnglishApp 装不上，那问题在 Kotlin framework 那条链路上；
// 如果它都装不上，那问题在 SideStore / Apple ID / pairing file，跟代码无关。
//
// 屏幕上直接印出机型和系统版本：装机后拍一张截图，就能同时拿到"链路通不通"和
// "这台机器是什么 iOS 版本"两个信息（后者决定 SideStore 能不能用）。

@main
struct PilotApp: App {
    var body: some Scene {
        WindowGroup { PilotView() }
    }
}

struct PilotView: View {
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            Image(systemName: "checkmark.seal.fill")
                .font(.system(size: 64))
                .foregroundStyle(.green)
                .padding(.bottom, 20)

            Text("链路打通")
                .font(.system(size: 32, weight: .bold))
                .padding(.bottom, 8)

            Text("纯 SwiftUI 空壳，不含任何 Kotlin 代码")
                .font(.subheadline)
                .foregroundStyle(.secondary)
                .padding(.bottom, 28)

            VStack(alignment: .leading, spacing: 10) {
                row("机型", DeviceInfo.modelName)
                row("系统", DeviceInfo.systemVersion)
                row("App", DeviceInfo.appVersion)
                row("签名方式", "免费 Apple ID · 7 天")
            }
            .padding(18)
            .background(Color(.secondarySystemBackground))
            .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
            .padding(.horizontal, 24)

            Spacer()

            Text("看到这行字，说明 GitHub Actions 出的\n未签名 IPA 能经 SideStore 重签并安装。")
                .font(.footnote)
                .multilineTextAlignment(.center)
                .foregroundStyle(.secondary)
                .padding(.horizontal, 32)
                .padding(.bottom, 40)
        }
    }

    private func row(_ label: String, _ value: String) -> some View {
        HStack {
            Text(label).foregroundStyle(.secondary)
            Spacer()
            Text(value).fontWeight(.medium)
        }
        .font(.subheadline)
    }
}

enum DeviceInfo {
    static var systemVersion: String {
        "iOS \(UIDevice.current.systemVersion)"
    }

    static var appVersion: String {
        let v = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? "?"
        let b = Bundle.main.infoDictionary?["CFBundleVersion"] as? String ?? "?"
        return "\(v) (\(b))"
    }

    /// `UIDevice.current.model` 只给 "iPhone"，拿不到具体型号，得走 utsname 的机器标识。
    static var modelName: String {
        var info = utsname()
        uname(&info)
        let machine = withUnsafeBytes(of: &info.machine) { raw -> String in
            let bytes = raw.prefix { $0 != 0 }
            return String(decoding: bytes, as: UTF8.self)
        }
        return machine.isEmpty ? "iPhone" : machine
    }
}
