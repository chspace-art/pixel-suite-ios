# pixel-english iOS 移植工程

把安卓版「句透」（`Pixel/pixel-english`）搬成 iPhone 上自用的 iOS App。
链路：**Windows 写码 → GitHub Actions 的 macOS runner 出未签名 IPA → 免费 Apple ID + SideStore 重签装机**。

可行性、风险与来源查证见 [`Pixel/docs/IOS_PORT_PLAN.md`](../docs/IOS_PORT_PLAN.md)。

---

## 现状（2026-09-20）

| 部分 | 状态 |
|---|---|
| KMP 工程骨架（Kotlin 2.4.20 + CMP 1.12.0 + Gradle 9.7.0） | ✅ 建立 |
| 墨玉主题层移植（Palette / Tokens / Type / PixelTheme / PixelIcons / PageHeader / Text） | ✅ **原样编译通过，桌面端渲染已验收** |
| 桌面靶机（Windows 上的迭代回路） | ✅ 可跑，见下 |
| iOS 工程（XcodeGen 双 target：Pilot + EnglishApp） | ✅ 已写，**未在 macOS 上验证过** |
| CI 未签名 IPA 流水线 | ✅ 已写，**未跑过** |
| 手机侧装机（iTunes / iloader / SideStore / Apple ID） | ⬜ 待用户操作 |
| 全量 UI 移植（首页/阅读器/词本/我的 + 数据层） | ⬜ 未开始，目前是骨架 |

**这个仓库的目的不止是搬 App，更是先把链路跑通**——所以里面有两个 App：
`Pilot` 是纯 SwiftUI 空壳，用来单独回答「这台 iPhone 到底能不能侧载」；
`EnglishApp` 才是正式 App。

## 目录

```
ios/
├── shared/                      Kotlin Multiplatform 模块（UI + 逻辑，桌面与 iOS 共用）
│   └── src/commonMain/kotlin/com/pixeltools/english/
│       ├── ui/theme/            墨玉令牌层——从安卓侧逐字拷来，包名保持一致
│       ├── ui/components/       PixelIcons / PageHeader / Text，同上
│       ├── App.kt               根 composable（首页三卡 + 悬浮胶囊导航）
│       └── Common.kt            AppLang / L()
├── iosApp/
│   ├── project.yml              XcodeGen 工程描述（Windows 上生成不了 .xcodeproj，故用 YAML）
│   └── Sources/{Pilot,EnglishApp}/
├── tools/capture-window.ps1     桌面靶机截图
├── tools/fetch-ipa.sh           从 CI 取回未签名 IPA
└── .github/workflows/ios-unsigned.yml
```

---

## 一、本机迭代（日常都在这里）

Windows 上编不了 iOS，也**没有任何真机热重载**。但共享 UI 层是同一份 Kotlin，
所以在本机开一个手机比例的桌面窗口 ≈ 看 iOS 渲染结果，**秒级**。

```bash
cd Pixel/ios
export JAVA_HOME=/c/android/jdk17
export GRADLE_USER_HOME=/c/android/gradle-home
sh gradlew :shared:run          # 打开 390×780 的桌面窗口
```

改完 `commonMain` 里的代码，重跑这条命令就能看到新版式（热编译约 6 秒）。
**调 UI 全在这条路上做，不要拿 CI 当预览器**——一次 CI 是 15–35 分钟。

截图（窗口开着的时候跑）：

```bash
powershell -ExecutionPolicy Bypass -File tools/capture-window.ps1 -Out ../docs/ui-preview/shots-0920-ios/desktop-home.png
```

## 二、打通 GitHub 链路

这个目录**要单独建成一个 GitHub 仓库**（`Pixel/.gitignore` 里已把 `ios/` 排除，
安卓套件不跟着上传，CI 也只看得到 iOS 工程）。

```bash
cd Pixel/ios
git remote add origin git@github.com:<你的账号>/pixel-english-ios.git
git push -u origin main
```

**仓库 public 还是 private 是个真选择**：

- `public`：标准 macOS runner **免费无限量**，跑多少次都行。
- `private`：免费额度 2000 分钟/月，macOS 按 10 倍扣（约 200 有效分钟）——
  一次冷构建就能吃掉 1/5，一个月只够跑几次。

源码不想公开的话，先用 public 把链路验通、再决定。

然后：

1. 装 `gh` CLI（Windows：`winget install GitHub.cli`），`gh auth login`。
2. 手动触发一次：Actions 页面点 `ios-unsigned` → `Run workflow`，或
   ```bash
   gh workflow run ios-unsigned.yml
   ```
3. 取回未签名 IPA：
   ```bash
   ./tools/fetch-ipa.sh            # 落进 dist/
   ```

**第一次跑会很慢**（要下 277 MB 的 Kotlin/Native 工具链，冷链接 15–30 分钟）。
`~/.konan` 和 `~/.gradle` 都做了缓存，第二次起会快很多。

## 三、手机侧装机（一次性，约半小时）

本机现在**什么都没装**：没有 iTunes、没有 Apple 驱动、iPhone 插上去系统都不认识。

1. **装 Apple 官方版 iTunes**（从 apple.com 下，**不要** Microsoft Store 版）——
   它提供 USB 驱动和 Apple Mobile Device Service，是后面所有工具的前提。
2. **装 iloader**（https://iloader.app/ ，MSI）→ USB 连 iPhone → 登录 **小号 Apple ID**
   → `Install SideStore (Stable)`。它会顺带把 pairing file 放好。
3. **iPhone 上**：
   - 设置 → 隐私与安全性 → **开发者模式** 打开（会要求重启）
   - 设置 → 通用 → VPN 与设备管理 → 信任你的开发者证书
   - 设置 → 通用 → 后台 App 刷新 → 打开
   - 启动 **LocalDevVPN** 并保持连接（SideStore 装/更新/续签都要求它开着）
4. 打开 SideStore，登录同一个 Apple ID，点那个 `7 DAYS` 数字手动刷新一次。

### 装 App

先装 **Pilot**：它能开就说明链路通了，屏幕上会直接印出机型和 iOS 版本。

IPA 怎么进手机：仓库 public 的话，把 IPA 挂成 Release 资产、在 SideStore 里填 URL 装最省事；
否则先把 IPA 弄到手机上（文件传输 / 云盘），再用 SideStore 导入。
**具体入口以你装上的 SideStore 版本为准**，这一步我没法在 Windows 上替你验证。

### 名额提醒

免费 Apple ID 同时只能装 **3 个** 自签 App，**SideStore 自己占一个**。
所以：Pilot + SideStore = 2，验完删掉 Pilot 再装 EnglishApp。
**7 天证书过期后 App 会直接打不开**（不是静默续命），删了重装数据会丢——永远原地覆盖升级。

---

## 四、待验证 / 已知风险

| 项 | 说明 |
|---|---|
| iOS 27 上 SideStore 登录有 bug | SideStore issue #1604（2026-09-20 仍开着）。**你的 iOS 版本是整件事的前提**，装 Pilot 后截图给我 |
| 免费 Apple ID 封禁波 | 2026-07 起有 `0xe8008024`「描述文件被封」的报告，同机换小号即可。**别用主 iCloud 账号签** |
| CMP 产出的 App 能否被 SideStore 重签 | 机制上没问题（静态 framework 没有嵌套 dylib 要重签），但没有公开先例。Pilot 就是为了把这条单独摘出来验 |
| `embedAndSignAppleFrameworkForXcode` 在关签名下的行为 | 源码层面确认：`isStatic = true` 时该任务 `isEnabled = false`，不会调 codesign。但**这条没在真 CI 上跑过** |
| 桌面端与 iOS 端的字体渲染差异 | 桌面用系统字体，iOS 上字形会不同；版式按 iPhone 逻辑分辨率（390pt 宽）调 |
| 仓库仓库顺序 | `settings.gradle.kts` 里阿里云镜像在前（本地迭代快），Central 兜底。CI 在美国跑，嫌慢就把 `mavenCentral()` 提前 |
