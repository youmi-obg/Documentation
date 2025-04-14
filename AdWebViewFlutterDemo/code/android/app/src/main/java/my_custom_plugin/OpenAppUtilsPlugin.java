package my_custom_plugin;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class OpenAppUtilsPlugin implements FlutterPlugin,MethodChannel.MethodCallHandler, ActivityAware {

    static MethodChannel channel;
    private Activity activity;
    public static String CHANNEL = "openAppUtils";


    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        if (methodCall.method.equals("openMarket")) {
            String packageName = methodCall.argument("packageName");
            String marketPath = methodCall.argument("marketPath");
            try {
                openByMarket(packageName,marketPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.success(true);
        } else if (methodCall.method.equals("openBrowser")) {
            String url = methodCall.argument("url");
            try {
                goToBrowser(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (methodCall.method.equals("openByScheme")) {
            String url = methodCall.argument("url");
            try {
                openByScheme(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (methodCall.method.equals("openBrowserFromJs")) {
            String url = methodCall.argument("url");
            try {
                openBrowser(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //打开商店
    public boolean openByMarket(String marketPkg, String marketPath) throws Exception {
        if (activity == null || TextUtils.isEmpty(marketPkg) || TextUtils.isEmpty(marketPath) ||
                !marketPath.startsWith("market://details?")) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(marketPath));
            intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean openByScheme(String url) throws Exception {
        if (activity == null || TextUtils.isEmpty(url)) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    //打开浏览器，默认优先打开谷歌浏览器
    public boolean goToBrowser(String playPath) throws Exception {
        boolean result = openByGoogleBrowser(playPath);
        if (!result) {
            result = openByScheme(playPath);
        }

        return result;
    }

    public boolean openByGoogleBrowser(String url) {
        if (activity == null || TextUtils.isEmpty(url)) {
            return false;
        }
        if (!isInstalledApp("com.android.chrome")) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.android.chrome");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //根据Intent启动 activity
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean openByIntent(String intentPath) throws Exception {
        if (activity == null || TextUtils.isEmpty(intentPath) ||
                (!intentPath.startsWith("android-app://") && !intentPath.startsWith("intent://"))) {
            return false;
        }
        try {
            Intent intent = null;
            if (intentPath.startsWith("android-app://")) {
                intent = Intent.parseUri(intentPath, Intent.URI_ANDROID_APP_SCHEME);
            } else if (intentPath.startsWith("intent://")) {
                intent = Intent.parseUri(intentPath, Intent.URI_INTENT_SCHEME);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e("OpenAppUtils", " openByIntent " + e.toString());
            throw e;
        }
    }

    public boolean openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ActivityInfo activityInfo = intent.resolveActivityInfo(activity.getPackageManager(), 0);
        try {
            if (activityInfo.exported) {
                intent.setData(Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isInstalledApp(String packageName) {
        boolean installed = false;
        PackageManager packageManager = activity.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return installed;
    }

    public void jsAction(String event_name, String params) {
        try {
            if ("OPEN_URL".equals(event_name)) {
                activity.startActivity(
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
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //点击互动广告关闭按钮时调用该方法。
    public void close() {
        //close the ad page or activity.
    }

    //接入Gspace-Fully广告时需实现此方法。该方法用于打开新的Webview页面。
    public void openWebview(String url) {
        //open a new page to display landingpage.
        //使用展示GSpace的Activity新实例打开当前url.
        //webView.loadUrl(url);
    }

    private boolean isHw() {
        return "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }

    private String getDefaultBrowser() {
        String packageName = null, systemApp = null, userApp = null;
        List<String> userAppList = new ArrayList<>();
        Context context = activity.getApplicationContext();
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

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {

    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(),CHANNEL);
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel.setMethodCallHandler(null);
    }
}
