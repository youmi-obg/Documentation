package net.youmi.overseas.youmi_flutter_plugin_example;

import android.app.Activity;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {

    private Activity activity;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
//        if(call.method.equals("init")){
//            String appId = call.argument("appId");
//            YoumiOffersWallSdk.init(activity.getApplication(), appId);
//        }else if(call.method.equals("startOffersWall")){
//            String userId = call.argument("userId");
//            YoumiOffersWallSdk.startOffersWall(activity, userId);
//        }else{
//            result.notImplemented();
//        }
    }
}
