---
title: Demo For iOS AdWebView
---

# Demo For iOS AdWebView

iOS WebView 互动广告示例项目。

## 项目位置

Demo 源代码位于仓库的 `AdWebviewIOSDemo/` 目录下。

- [GitHub 仓库](https://github.com/youmi-obg/Documentation/tree/main/AdWebviewIOSDemo)

## 项目结构

```
AdWebviewIOSDemo/
├── H5WebviewController.h
├── H5WebviewController.m
└── README.md
```

## Unity 集成

项目还包含 Unity 桥接示例代码：

### 1. 拷贝文件

拷贝 `H5WebviewController.h` 和 `H5WebviewController.m` 文件，并修改 `H5WebviewController.m` 中的 `requestUrl` 参数。

### 2. 在 UnityBridge.mm 中添加桥接函数

```objc
#include "UnityInterface.h"
#include "H5WebviewController.h"

extern "C" {
    void ShowH5WebviewController() {
        dispatch_async(dispatch_get_main_queue(), ^{
            UIViewController *rootVC = [UIApplication sharedApplication].keyWindow.rootViewController;
            H5WebviewController *viewController = [[H5WebviewController alloc] init];
            viewController.modalPresentationStyle = UIModalPresentationFullScreen;
            [rootVC presentViewController:viewController animated:YES completion:nil];
        });
    }
}
```

### 3. 在 UnityPlugin.cs 中调用

```csharp
using UnityEngine;
using System.Runtime.InteropServices;

public class UnityPlugin : MonoBehaviour {

    [DllImport("__Internal")]
    private static extern void ShowH5WebviewController();

    public void ShowWebView() {
        ShowH5WebviewController();
    }
}
```
