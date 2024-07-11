package com.example.adwebviewdemo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.adwebviewdemo.utils.OpenAppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_WEBVIEW_URL = "extra_webview_url";

    public static final String EXTRA_JAVASCRIPT_INTERFACE_NAMESPACE = "extra_javascript_interface_namespace";

    private String url;

    private String nameSpace;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra(EXTRA_WEBVIEW_URL);
        //nameSpace不传或传为空则默认为android
        nameSpace = !TextUtils.isEmpty(getIntent().getStringExtra(EXTRA_JAVASCRIPT_INTERFACE_NAMESPACE)) ? getIntent().getStringExtra(EXTRA_JAVASCRIPT_INTERFACE_NAMESPACE) : "android";

        webView = findViewById(R.id.webView);

        initWebView();

        webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //根据实际使用情况可选配置
        webSettings.setDatabaseEnabled(true); // 开启database storage api功能
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 根据cache-control决定是否从网络上取数据
        // 设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true); // 自动加载图片资源
        webSettings.setDefaultTextEncodingName("utf-8"); // 设置编码格式
        webSettings.setSupportZoom(true); // 支持使用屏幕控件或手势进行缩放
        webSettings.setBuiltInZoomControls(true); // 使用其内置的变焦机制
        webSettings.setDisplayZoomControls(false); // 不显示屏幕缩放控件
        // Android 5.0以后的WebView加载的链接为Https开头，但是链接里面的内容有Http链接，
        // 比如图片为Http链接，这时候，图片就会加载不出来。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0及以上版本WebView默认不允许加载Http与Https混合内容
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            // MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器风格
            // MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源
            // MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的
        }

        webSettings.setUserAgentString(String.format(
                Locale.getDefault(),
                "Mozilla/5.0 (Linux; <Android %s>; %s Build/%s) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/80.0.3987.149" + " Mobile Safari/537.36",
                Build.VERSION.RELEASE,
                Build.MODEL,
                Build.DISPLAY
        ));

        //nameSpace:与前端协议好的对象名
        //添加JavaScriptInterface接口对象，可添加多个
        webView.addJavascriptInterface(new JsBridge(), nameSpace);

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
                            result = OpenAppUtils.openByMarket(WebViewActivity.this, "com.android.vending", url);
                        } else {
                            intent.setData(Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            result = true;
                        }
                    } else if (!url.startsWith("http://")
                            && !url.startsWith("https://")) {
                        result = OpenAppUtils.openUrl(WebViewActivity.this, url);
                    } else if (url.contains("lz_open_browser=1")) {
                        result = OpenAppUtils.openBrowser(WebViewActivity.this, url);
                    }
                } catch (Exception e) {
                    return false;
                }
                return result || super.shouldOverrideUrlLoading(view, url);
            }
        });

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
    }

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

    @Override
    protected void onDestroy() {
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.clearHistory();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
        webView = null;
        super.onDestroy();
    }

    private class JsBridge {

        @JavascriptInterface
        public void jsAction(String event_name, String params) {
            try {
                if ("OPEN_URL".equals(event_name)) {
                    startActivity(
                            params.startsWith("intent") ?
                                    Intent.parseUri(params, Intent.URI_INTENT_SCHEME)
                                    :
                                    new Intent(Intent.ACTION_VIEW, Uri.parse(params)
                                    ));
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
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //点击互动广告关闭按钮时调用该方法。
        @JavascriptInterface
        public void close() {
            //close the ad page or activity.
            finish();
        }

        //接入Gspace-Fully广告时需实现此方法。该方法用于打开新的Webview页面。
        @JavascriptInterface
        public void openWebview(String url) {
            //open a new page to display landingpage.
            //使用展示GSpace的Activity新实例打开当前url.
            //webView.loadUrl(url);
        }
    }

    private boolean isHw() {
        return "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }

    private String getDefaultBrowser() {
        String packageName = null, systemApp = null, userApp = null;
        List<String> userAppList = new ArrayList<>();
        Context context = getApplicationContext();
        Intent browserIntent = new Intent("android.intent.action.VIEW",
                Uri.parse("https://"));
        ResolveInfo resolveInfo =
                context.getPackageManager().resolveActivity(browserIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            packageName = resolveInfo.activityInfo.packageName;
        }
        if (packageName == null || packageName.equals("android")) {
            List<ResolveInfo> lists =
                    context.getPackageManager().queryIntentActivities(browserIntent, 0);
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
}