import 'dart:async';

import 'package:android_intent_plus/android_intent.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HomePage(),
    );
  }
}

class _HomePageState extends State<HomePage> {
  bool _isButtonEnable = false;
  final TextEditingController _textUrlController = TextEditingController();

  @override
  void dispose() {
    _textUrlController.dispose(); //释放控制器资源
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('Please input WebView url:'),
            const SizedBox(height: 20),
            Container(
              margin: const EdgeInsets.only(left: 20, right: 20),
              child: TextFormField(
                controller: _textUrlController,
                autofocus: true,
                onChanged: (text) {
                  setState(() {
                    _isButtonEnable = text.isNotEmpty;
                  });
                },
              ),
            ),
            const SizedBox(height: 60),
            ElevatedButton(
              child: const Text('Jump to WebView(InAppWebView)'),
              onPressed: _isButtonEnable
                  ? () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => InAppWebViewExample(
                                _textUrlController.text)),
                      );
                    }
                  : null,
            ),
            const SizedBox(height: 60),
            ElevatedButton(
              child: const Text('Jump to WebView(WebView_Flutter)'),
              onPressed: _isButtonEnable
                  ? () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => WebViewExample(
                          _textUrlController.text)),
                );
              }
                  : null,
            ),
          ],
        ),
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  _HomePageState createState() => _HomePageState();
}

class WebViewExample extends StatefulWidget {
  final String linkUrl;

  const WebViewExample(this.linkUrl, {super.key});

  @override
  WebViewExampleState createState() => WebViewExampleState();
}

class InAppWebViewExample extends StatefulWidget {
  final String linkUrl;

  const InAppWebViewExample(this.linkUrl, {super.key});

  @override
  InAppWebViewExampleState createState() => InAppWebViewExampleState();
}

class WebViewExampleState extends State<WebViewExample> {
  late final WebViewController _controller;

  @override
  void dispose() {
    super.dispose();
    _controller.clearCache();
    _controller.clearLocalStorage();
  }

  @override
  void initState() {
    super.initState();
    // 初始化 WebView

    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setNavigationDelegate(NavigationDelegate(
          onPageFinished: (String url) {
            print("onPageFinished: $url");
            isGoingBack = false;
          },
          onPageStarted: (String url) {
            currentUrl = url;
            print("onPageStarted: $url");
          },
          onUrlChange: (UrlChange change) {

          },
          //H5通过URL传递数据给APP,Url会被NavigationDelegate捕捉（涉及到跳转与否）
          onNavigationRequest: (NavigationRequest request) async {
            mOverrideLegalWebViewUrl = widget.linkUrl;
            if (request.url.startsWith("http") ||
                request.url.startsWith("https")) {
              mOverrideLegalWebViewUrl = request.url;
            }
            if (request.url.startsWith("market:") ||
                request.url.startsWith("https://play.google.com/store/") ||
                request.url.startsWith("http://play.google.com/store/")) {
              if (request.url.startsWith("market://details?")) {
                openMarket("com.android.vending", request.url);
              } else {
                openBrowser(request.url);
              }
              return NavigationDecision.prevent;
            } else if (!request.url.startsWith("http://") &&
                !request.url.startsWith("https://")) {
              if (request.url.startsWith("android-app://") ||
                  request.url.startsWith("intent://")) {
                openBrowser(mOverrideLegalWebViewUrl);
                if (await _controller.canGoBack()) {
                  _controller.goBack();
                }
              }
              return NavigationDecision.prevent;
            } else if (request.url.contains("lz_open_browser=1")) {
              openBrowser(request.url);
              return NavigationDecision.prevent;
            } else if (request.url.contains(".apk")) {
              openByScheme(request.url);
              return NavigationDecision.prevent;
            } else {
              if(isGoingBack) {
                isGoingBack = false;
                return NavigationDecision.prevent;
              }
              isGoingBack = false;
              return NavigationDecision.navigate;
            }
          }))
      ..addJavaScriptChannel("openBrowser",
          onMessageReceived: (javaScriptMessage) {
        print(javaScriptMessage.message);
        openBrowserFromJs(javaScriptMessage.message);
      })
      ..loadRequest(Uri.parse(widget.linkUrl));
  }

  @override
  Widget build(BuildContext context) {

    return WillPopScope(
      onWillPop: _handleBackPress,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Webview'),
        ),
        body: WebViewWidget(controller: _controller),
      ),
    );
  }

  // 返回键处理逻辑
  Future<bool> _handleBackPress() async {
    final canGoBack = await _controller.canGoBack();
    if (canGoBack) {
      isGoingBack = true;
      _controller.goBack();
      return false; // 阻止默认返回
    }
    isGoingBack = false;
    return true; // 允许退出 App
  }

  static const MethodChannel _channel = MethodChannel("openAppUtils");

  bool isGoingBack = false;
  String currentUrl = "";
  String mOverrideLegalWebViewUrl = "";

  void openMarket(String packageName, String marketPath) async {
    //传递一个方法名，即调用Android的原生方法
    await _channel.invokeMethod(
        "openMarket", {'packageName': packageName, 'marketPath': marketPath});
  }

  void openBrowser(String url) async {
    await _channel.invokeMethod("openBrowser", {'url': url});
  }

  void openByScheme(String url) async {
    await _channel.invokeMethod("openByScheme", {'url': url});
  }

  void openBrowserFromJs(String url) async {
    await _channel.invokeMethod("openBrowserFromJs", {'url': url});
  }
}

class InAppWebViewExampleState extends State<InAppWebViewExample> {
  late final InAppWebViewController _inAppWebViewController;

  @override
  void dispose() {
    super.dispose();
    _inAppWebViewController.clearCache();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('WebView'),
      ),
      body: WillPopScope(
        onWillPop: _handleBackPress,
        child: InAppWebView(
          initialUrlRequest: URLRequest(url: Uri.parse(widget.linkUrl)),
          initialOptions: InAppWebViewGroupOptions(
            crossPlatform: InAppWebViewOptions(
                useShouldOverrideUrlLoading: true,
              javaScriptEnabled: true
            ),
          ),
          onWebViewCreated: (controller) {
            _inAppWebViewController = controller;
            // 注册 JavaScript 方法
            controller.addJavaScriptHandler(
              handlerName: "openBrowser",
              callback: (args) {
                // 处理 H5 调用
                print('H5 called Flutter with: $args');
                openBrowserFromJs(args[0].toString());
              },
            );
          },
          onLoadStart: (controller,url) {
            currentUrl = url.toString();
            print("onStart: $url");
          },
          onLoadStop: (controller,url) {
            isGoingBack = false;
            print("onStop: $url");
          },
          shouldOverrideUrlLoading: (controller, navigationAction) async {
            final url = navigationAction.request.url.toString();
            print("over url: $url");
                    if (url.startsWith("http") ||
                       url.startsWith("https")) {
                      mOverrideLegalWebViewUrl = url;
                    }
                    if (url.startsWith("market:") ||
                        url.startsWith("https://play.google.com/store/") ||
                        url.startsWith("http://play.google.com/store/")) {

                      if (url.startsWith("market://details?")) {
                        openMarket("com.android.vending", url);
                      } else {
                        openBrowser(url);
                      }
                      return NavigationActionPolicy.CANCEL;
                    } else if (!url.startsWith("http://") &&
                        !url.startsWith("https://")) {
                      if (url.startsWith("android-app://") ||
                          url.startsWith("intent://")) {
                        openBrowser(mOverrideLegalWebViewUrl);
                        if (await _inAppWebViewController.canGoBack()) {
                          _inAppWebViewController.goBack();
                        }
                      }
                      return NavigationActionPolicy.CANCEL;
                    } else if (url.contains("lz_open_browser=1")) {
                      openBrowser(url);
                      return NavigationActionPolicy.CANCEL;
                    } else if (url.contains(".apk")) {
                      openByScheme(url);
                      return NavigationActionPolicy.CANCEL;
                    } else {
                      if(isGoingBack) {
                        isGoingBack = false;
                        return NavigationActionPolicy.CANCEL;
                      }
                      isGoingBack = false;
                      return NavigationActionPolicy.ALLOW;
                    }
          },
        ),
      ),
    );
  }

  // 返回键处理逻辑
  Future<bool> _handleBackPress() async {
    final canGoBack = await _inAppWebViewController.canGoBack();
    if (canGoBack) {
      isGoingBack = true;
      _inAppWebViewController.goBack();
      return false; // 阻止默认返回
    }
    isGoingBack = false;
    return true; // 允许退出 App
  }

  static const MethodChannel _channel = MethodChannel("openAppUtils");

  bool isGoingBack = false;
  String currentUrl = "";
  String mOverrideLegalWebViewUrl = "";

  void openMarket(String packageName, String marketPath) async {
    //传递一个方法名，即调用Android的原生方法
    await _channel.invokeMethod(
        "openMarket", {'packageName': packageName, 'marketPath': marketPath});
  }

  void openBrowser(String url) async {
    await _channel.invokeMethod("openBrowser", {'url': url});
  }

  void openByScheme(String url) async {
    await _channel.invokeMethod("openByScheme", {'url': url});
  }

  void openBrowserFromJs(String url) async {
    await _channel.invokeMethod("openBrowserFromJs", {'url': url});
  }
}
