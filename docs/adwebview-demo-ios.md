---
title: Demo For iOS AdWebView
---

# Demo For iOS AdWebView

iOS WebView interactive advertising demo project.

## Project Location

Demo source code is located in the `AdWebviewIOSDemo/` directory of the repository.

- [GitHub Repository](https://github.com/youmi-obg/Documentation/tree/main/AdWebviewIOSDemo)

## Project Structure

```
AdWebviewIOSDemo/
├── H5WebviewController.h
├── H5WebviewController.m
└── README.md
```

## Unity Integration

The project also includes Unity bridging example code:

### 1. Copy Files

Copy `H5WebviewController.h` and `H5WebviewController.m` files, and modify the `requestUrl` parameter in `H5WebviewController.m`.

### 2. Add Bridge Functions in UnityBridge.mm

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

### 3. Call in UnityPlugin.cs

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
