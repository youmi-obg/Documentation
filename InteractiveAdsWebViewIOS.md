# Interactive Ads IOS WebView Integration

## 1. Configure HTTP Support

Configure in the project's info.plist：

```info.plist
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
    <true/>
</dict>
```

## 2. Initialize Webview Configuration

Swift：

```swift
let config = WKWebViewConfiguration()
// Check version and enable JavaScript support
if #available(iOS 14.0, *) {
    let prefs = WKWebpagePreferences()
    prefs.allowsContentJavaScript = true
    config.defaultWebpagePreferences = prefs
} else {
    config.preferences.javaScriptEnabled = true
}
// Build webview, frame needs to be configured according to actual size
let webView = WKWebView(frame: CGRect(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.height), configuration: config)
```

Object-c：

```object-c
WKWebViewConfiguration *config = [[WKWebViewConfiguration alloc] init];
// Check version and enable JavaScript support
if (@available(iOS 14.0, *)) {
    WKWebpagePreferences *prefs = [[WKWebpagePreferences alloc] init];
    prefs.allowsContentJavaScript = YES;
    config.defaultWebpagePreferences = prefs;
} else {
    config.preferences.javaScriptEnabled = YES;
}
// Build webview, frame needs to be configured according to actual size
WKWebView *webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height) configuration:config];
```

## 3. Load Page

```load page
// URL needs to be replaced with actual address
NSString *urlStr = @"https://example.com";
NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:urlStr]];
[webView loadRequest:request];
```

## 4. JSBridge Usage

### 4.1 Register Native Methods for Frontend Call

#### iOS Native Registration Method

Swift:
```
// Register method for frontend call
webView.configuration.userContentController.add(self, name: "nativeFunction")

// Implementation of WKScriptMessageHandler protocol
func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
    if message.name == "nativeFunction" {
        // Process received message
        print("Received message from frontend: \(message.body)")

        // Return data to frontend
        let script = "window.postMessage({type: 'response', data: 'Native response data'}, '*')"
        webView.evaluateJavaScript(script, completionHandler: nil)
    }
}
```

Objective-C:
```
// Register method for frontend call
[webView.configuration.userContentController addScriptMessageHandler:self name:@"nativeFunction"];

#pragma mark - WKScriptMessageHandler
- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message {
    if ([message.name isEqualToString:@"nativeFunction"]) {
        // Process received message
        NSLog(@"Received message from frontend: %@", message.body);

        // Return data to frontend
        NSString *script = @"window.postMessage({type: 'response', data: 'Native response data'}, '*')";
        [webView evaluateJavaScript:script completionHandler:nil];
    }
}
```

### 4.2 Frontend Calling Native Methods

```
// Call native method
window.webkit.messageHandlers.nativeFunction.postMessage({type: 'request', data: 'Frontend data'});
```

### 4.3 Frontend Listening for Native Messages

```
// Listen for messages from native
window.addEventListener('message', function(event) {
    if (event.data.type === 'response') {
        console.log('Received response from native:', event.data.data);
    }
});
```

## 5. Common Issues and Solutions

### 5.1 White Screen Issue
```
1. Check if the network is normal
2. Check if the URL is correct
3. Check if ATS configuration is correct
4. Check if JavaScript is enabled
```

### 5.2 JSBridge Not Working

```
1. Check if the registration name matches
2. Check if the message handler is properly added
3. Check if the frontend calling method is correct
4. Check if the WKScriptMessageHandler protocol is correctly implemented
```

### 5.3 Page Loading Slow

```
1. Check network speed
2. Optimize page resources
3. Consider using local caching
```
