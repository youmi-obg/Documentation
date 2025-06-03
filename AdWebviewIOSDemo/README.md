# 1.拷贝文件
拷贝H5WebviewController.h和H5WebviewController.m文件，并修改H5WebviewController.m中的requestUrl参数。

# 2.在UnityBridge.mm中添加桥接函数

```
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

# 3.可在UnityPlugin.cs中调用

```
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
