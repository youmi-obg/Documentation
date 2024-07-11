package com.example.adwebviewdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

public class OpenAppUtils {

    //打开商店
    public static boolean openByMarket(Context context, String marketPkg, String marketPath) throws Exception {
        if (context == null || TextUtils.isEmpty(marketPkg) || TextUtils.isEmpty(marketPath) ||
                !marketPath.startsWith("market://details?")) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(marketPath));
            intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean openByScheme(Context context, String url) throws Exception {
        if (context == null || TextUtils.isEmpty(url)) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    //打开浏览器，默认优先打开谷歌浏览器
    public static boolean openBrowser(Context context, String playPath) throws Exception {
        boolean result = openByGoogleBrowser(context, playPath);
        if (!result) {
            result = openByScheme(context, playPath);
        }

        return result;
    }

    public static boolean openByGoogleBrowser(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return false;
        }
        if (!isInstalledApp(context, "com.android.chrome")) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.android.chrome");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), 0);
        try {
            if (activityInfo.exported) {
                intent.setData(Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean isInstalledApp(Context context, String packageName) {
        boolean installed = false;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return installed;
    }
}
