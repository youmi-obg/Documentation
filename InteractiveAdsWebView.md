# SpinGo Android WebView Integration

## 1. Add Internet Permission

Add the internet permission in the `AndroidManifest.xml` manifest file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 2. Configure WebView to Support http Links

In `AndroidManifest.xml`, add the configuration to the `application` node to support http links in WebView:

```xml
<application
        android:usesCleartextTraffic="true"
        android:networkSecurityConfig="@xml/network_security_config">
</application>
```

`network_security_config`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

## 3. Visibility Configuration for Android 11 and Above

For Android target versions 30 and above (Android 11 and above), add package visibility configuration in `AndroidManifest.xml` because WebView might involve APK installation redirections to Google Play or a browser. Configure the following to obtain application installation information and perform third-party redirections:

```xml
<queries>
    <intent>
        <action android:name="android.intent.action.MAIN" />
    </intent>
</queries>
```

Or configure by package name:

```xml
<queries>
    <package android:name="com.android.chrome" />
    <package android:name="com.android.vending" />
</queries>
```

## 4. Initialize WebView Configuration

Initialize WebView related configurations in the `Activity` where WebView is located, and set it according to the actual H5 situation:

```java
WebSettings webSettings = webView.getSettings();
webSettings.setJavaScriptEnabled(true); // Enable JS
webSettings.setDomStorageEnabled(true); // Enable DOM storage API
webSettings.setDatabaseEnabled(true); // Enable database storage API
webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // Decide whether to fetch data from the network based on cache-control
webSettings.setUseWideViewPort(true); // Adjust images to fit the WebView size
webSettings.setLoadWithOverviewMode(true); // Scale to screen size
webSettings.setLoadsImagesAutomatically(true); // Automatically load image resources
webSettings.setDefaultTextEncodingName("utf-8"); // Set encoding format
webSettings.setSupportZoom(true); // Support zooming using screen controls or gestures
webSettings.setBuiltInZoomControls(true); // Use built-in zoom mechanisms
webSettings.setDisplayZoomControls(false); // Do not display screen zoom controls

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
}

webSettings.setUserAgentString(String.format(
    Locale.getDefault(),
    "Mozilla/5.0 (Linux; <Android %s>; %s Build/%s) AppleWebKit/537.36 " +
    "(KHTML, like Gecko) Chrome/80.0.3987.149 Mobile Safari/537.36",
    Build.VERSION.RELEASE,
    Build.MODEL,
    Build.DISPLAY
));
```

## 5. Set APK Download Listener for WebView

```java
webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
    try {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    } catch (Exception e) {
        e.printStackTrace();
    }
});
```

## 6. Set WebViewClient

Override the `shouldOverrideUrlLoading()` method to get the URL based on redirection, and handle different domain names or protocol headers/prefixes, such as redirecting to GP, browser, etc.:

```java
webView.setWebViewClient(new WebViewClient() {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        boolean result = false;
        try {
            if (url.startsWith("market:")
                || url.startsWith("https://play.google.com/store/")
                || url.startsWith("http://play.google.com/store/")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (url.startsWith("market://details?")) {
                    result = OpenAppUtils.openByMarket(MainActivity.this, "com.android.vending", url);
                } else {
                    intent.setData(Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    result = true;
                }
            } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
                result = OpenAppUtils.openUrl(MainActivity.this, url);
            } else if (url.contains("lz_open_browser=1")) {
                result = OpenAppUtils.openBrowser(WebViewActivity.this, url);
            }
        } catch (Exception e) {
            return false;
        }
        return result || super.shouldOverrideUrlLoading(view, url);
    }
});
```

## 7. Add JavaScriptInterface Android Object

Support JS calling Android methods:

```java
String nameSpace = "android";
webView.addJavascriptInterface(new JsBridge(), nameSpace);

private class JsBridge {

    @JavascriptInterface
    public void jsAction(String event_name, String params) {
        try {
            if ("OPEN_URL".equals(event_name)) {
                startActivity(
                    params.startsWith("intent") ?
                        Intent.parseUri(params, Intent.URI_INTENT_SCHEME)
                        :
                        new Intent(Intent.ACTION_VIEW, Uri.parse(params))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void openBrowser(String url) {
        try {
            Intent intent = null;
            if (url.startsWith("intent")) {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } else {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            }
            if (intent != null) {
                if (isHw()) {
                    intent.setPackage(getDefaultBrowser());
                }
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void close() {
        finish();
    }

    @JavascriptInterface
    public void openWebview(String url) {
        // open a new page to display landing page
        // webView.loadUrl(url);
    }
}

private boolean isHw() {
    return "huawei".equalsIgnoreCase(Build.MANUFACTURER);
}

private String getDefaultBrowser() {
    String packageName = null, systemApp = null, userApp = null;
    List<String> userAppList = new ArrayList<>();
    Context context = getApplicationContext();
    Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://"));
    ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
    if (resolveInfo != null && resolveInfo.activityInfo != null) {
        packageName = resolveInfo.activityInfo.packageName;
    }
    if (packageName == null || packageName.equals("android")) {
        List<ResolveInfo> lists = context.getPackageManager().queryIntentActivities(browserIntent, 0);
        for (ResolveInfo app : lists) {
            if ((app.activityInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                systemApp = app.activityInfo.packageName;
            } else {
                userApp = app.activityInfo.packageName;
                userAppList.add(userApp);
            }
        }
        if (userAppList.contains("com.android.chrome")) {
            packageName = "com.android.chrome";
        } else {
            if (systemApp != null) {
                packageName = systemApp;
            }
            if (userApp != null) {
                packageName = userApp;
            }
        }
    }
    return packageName;
}
```

## 8. Support WebView Web Page Back Navigation

Please support web page back navigation instead of directly closing the page:

```java
@Override
public void onBackPressed() {
    if (webView == null) {
        super.onBackPressed();
        return;
    }
    if (webView.canGoBack()) {
        webView.goBack();
    } else {
        super.onBackPressed();
    }
}
```

## 9. Load URL

```java
webView.loadUrl(url);
```

## 10. Note: Demo App Instructions (see [SpinGo Demo](https://github.com/youmi-obg/Documentation/blob/main/AdWebViewDemo))

<img src="./images/AdWebViewDemo2.png" alt="load ads" width="300" style="aspect-ratio: auto;">

Support dynamic URL modification on the homepage: Enter/paste the WebView web page URL into the first input box. Also, support dynamically modifying the JS object name agreed with various interactive advertising platforms (corresponding to step 7). If not filled or handled, the default value is `android`. After entering the URL correctly, click the button to jump to the interface that loads WebView and display it.

<img src="./images/AdWebViewDemo.png" alt="load ads" width="300" style="aspect-ratio: auto;">

