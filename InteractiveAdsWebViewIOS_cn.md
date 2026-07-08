# 互动广告IOS WebView对接

## 1. 配置 HTTP 支持

在项目的 info.plist 中配置：

```info.plist
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
    <true/>
</dict>
```

## 2. 初始化 Webview 配置项

Swift：

```swift
let config = WKWebViewConfiguration()
// 根据版本判判断，加入 JS 支持
if #available(iOS 14.0, *) {
    let prefs = WKWebpagePreferences()
    prefs.allowsContentJavaScript = true
    config.defaultWebpagePreferences = prefs
} else {
    config.preferences.javaScriptEnabled = true
}
// 构建 webview, fram 需要根据实际大小配置
let webView = WKWebView(frame: .zero, configuration: config)
// 设置 navigation 代理
webView.navigationDelegate = self
```

Object-c：

```object-c
WKWebViewConfiguration *config = [[WKWebViewConfiguration alloc] init];
// 根据版本判判断，加入 JS 支持
if (@available(iOS 14.0, *)) {
    WKWebpagePreferences *prefs = [[WKWebpagePreferences alloc] init];
    prefs.allowsContentJavaScript = YES;
    config.defaultWebpagePreferences = prefs;
} else {
    config.preferences.javaScriptEnabled = YES;
}
// 构建 webView，frame 需根据实际布局设置
WKWebView *webView = [[WKWebView alloc] initWithFrame:CGRectZero configuration:config];
// 设置 navigation 代理
webView.navigationDelegate = self;
```

## 3. 配置 Webview 拦截

1.拦截所有的非 https/http 协议的请求，并通过浏览器打开

2.如果拦截命中自定义的特殊协议（如 spingo://），则进入自定义逻辑

Swift：

```swift
// 实现 WKNavigationDelegate 回调方法，在发起导航时拦截
func webView(_ webView: WKWebView, decidePolicyFor navigationAction:WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) ->Void) {
    guard let url = navigationAction.request.url else {
        decisionHandler(.allow)
        return
    }
    let scheme = (url.scheme ?? "").lowercased()
    // http 和 https 正常加载
    if scheme == "http" || scheme == "https" {
        decisionHandler(.allow)
        return
    }
    // 自定义协议：spingo 取出 redirect_url 参数并通过浏览器打开
    if scheme == "spingo" {
        let redirect = extractRedirect(from: url)
        UIApplication.shared.open(redirect, options: [:])
        decisionHandler(.cancel)
        return
    }
    // 其他所有非 http/https 协议：尝试通过外部浏览器打开
    UIApplication.shared.open(url, options: [:])
    decisionHandler(.cancel)
}

/// 从自定义协议链接中提取 redirect_url 
private func extractRedirect(from url: URL) -> URL? {
    guard let components = URLComponents(url: url, resolvingAgainstBaseURL:false),
          let value = components.queryItems?.first(where: { $0.name =="redirect_url" })?.value,
          let redirect = URL(string: value) else {
        return nil
    }
    return redirect
}
```

Object-c：

```object-c
// 实现 WKNavigationDelegate 回调方法，在发起导航时拦截
- (void)webView:(WKWebView *)webView
decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction
decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    NSURL *url = navigationAction.request.URL;
    if (url == nil) {
        decisionHandler(WKNavigationActionPolicyAllow);
        return;
    }
    NSString *scheme = url.scheme.lowercaseString ?: @"";
    // http 和 https 正常加载
    if ([scheme isEqualToString:@"http"] || [scheme isEqualToString:@"https"]) {
        decisionHandler(WKNavigationActionPolicyAllow);
        return;
    }
    // 自定义协议 spingo：提取 redirect_url 参数并通过外部浏览器打开
    if ([scheme isEqualToString:@"spingo"]) {
        NSURL *redirect = [self extractRedirectFromURL:url];
        if (redirect != nil) {
            [[UIApplication sharedApplication] openURL:redirect options:@{} completionHandler:nil];
        }
        decisionHandler(WKNavigationActionPolicyCancel);
        return;
    }
    // 其他所有非 http/https 协议：尝试通过外部浏览器打开
    [[UIApplication sharedApplication] openURL:url options:@{} completionHandler:nil];
    decisionHandler(WKNavigationActionPolicyCancel);
}

/// 从自定义协议链接中提取 redirect_url 参数并构造 NSURL
- (NSURL *)extractRedirectFromURL:(NSURL *)url {
    NSURLComponents *components = [NSURLComponents componentsWithURL:url resolvingAgainstBaseURL:NO];
    NSArray<NSURLQueryItem *> *queryItems = components.queryItems ?: @[];
    for (NSURLQueryItem *item in queryItems) {
        if ([item.name isEqualToString:@"redirect_url"]) {
            NSString *value = item.value;
            if (value.length > 0) {
                return [NSURL URLWithString:value];
            }
            break;
        }
    }
    return nil;
}
```

## 4. 配置页面返回按钮逻辑

自定义配置 NavigationBar 的返回按钮，如果 Webview 可以返回上一页，点击返回按钮时调用 webview goBack 方法，否则关闭当前页面

Swift：

```swift
// 设置返回按钮
navigationItem.leftBarButtonItem = UIBarButtonItem(title: "返回", style: .plain, target: self, action: #selector(handleBackButton))

// 实现返回方式
@objc private func handleBackButton() {
    if webView.canGoBack {
        webView.goBack()
    } else {
        navigationController?.popViewController(animated: true)
    }
}
```

Object-c：

```object-c
self.navigationItem.leftBarButtonItem =
[[UIBarButtonItem alloc] initWithTitle:@"返回" style:UIBarButtonItemStylePlain target:self action:@selector(handleBackButton)];

- (void)handleBackButton {
    if ([self.webView canGoBack]) {
        [self.webView goBack];
    } else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}
```
