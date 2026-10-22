# 小窗播放器项目指南

这是一个 PICO Spatial SDK Planar 应用：用户输入一个 HTTP(S) 直链视频 URL，在紧凑的空间窗口里播放，并可播放、暂停或更换网址。

## 结构

- `Main.kt`：保持精简，只负责 `DefaultWindowContainer`、`PicoTheme` 和窗口尺寸约束。
- `ui/player/`：MVI-lite 状态、事件、ViewModel 和工厂。
- `ui/player/components/`：输入区、16:9 Media3 视频表面和 SpatialUI 控制区。
- `domain/`：视频来源与播放状态模型、URL 准备 UseCase。
- `data/repository/`：URL 校验和标题推导。
- `.scratch/`：已验收设计包、设计到实现映射和验证结果。

## 约束

- 所有 2D UI 必须使用 `com.pico.spatial.ui.design.*` 并包在 `PicoTheme` 中。
- 禁止引入 Material / Material3 组件或主题。
- 默认窗口继续使用系统 `Material.Regular` 背景；不要给根节点添加自绘背景。
- `screen-video` 是唯一 app 自绘表面，颜色来自 `MiniPlayerColors.screenSurface`。
- 视频播放使用 Media3 `ExoPlayer` + `PlayerView`，关闭其原生控制器，由 SpatialUI `Button` / `IconButton` 控制。
- 首版面向可直接播放的媒体 URL；网页详情页、DRM、登录态与反盗链资源不保证可用。

## 构建

```bash
./gradlew testDebugUnitTest assembleDebug
```

APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。运行入口是 `.platform.LaunchActivity`。

自然的后续方向是补充 HLS/DASH 兼容性说明、音频焦点策略、播放进度，以及用 PICO 模拟器/真机验证窗口尺寸和后台播放生命周期。
