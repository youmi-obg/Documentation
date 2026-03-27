---
title: Demo For Android AdWebView
---

# Demo For Android AdWebView

Android WebView 互动广告示例项目。

## 项目位置

Demo 源代码位于仓库的 `AdWebViewDemo/` 目录下。

- [GitHub 仓库](https://github.com/youmi-obg/Documentation/tree/main/AdWebViewDemo)

## 项目结构

```
AdWebViewDemo/
├── AdWebViewDemo.apk          # 编译好的 APK 文件
└── code/                       # 源代码目录
    ├── app/
    │   └── src/main/
    │       ├── java/com/example/adwebviewdemo/
    │       │   ├── MainActivity.java
    │       │   ├── WebViewActivity.java
    │       │   └── utils/OpenAppUtils.java
    │       └── res/
    └── build.gradle
```

## 快速开始

1. 克隆仓库
2. 使用 Android Studio 打开 `AdWebViewDemo/code/` 目录
3. 同步 Gradle 依赖
4. 运行项目

## 主要功能

- WebView 基础配置
- 下载监听处理
- URL 重定向处理
- JavaScript 接口桥接
- 网页回退支持
