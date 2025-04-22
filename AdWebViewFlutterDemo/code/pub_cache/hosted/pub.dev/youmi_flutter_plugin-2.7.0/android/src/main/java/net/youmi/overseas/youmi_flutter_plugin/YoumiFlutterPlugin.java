package net.youmi.overseas.youmi_flutter_plugin;

import android.app.Activity;

import androidx.annotation.NonNull;

import net.youmi.overseas.android.YoumiOffersWallSdk;
import net.youmi.overseas.android.ui.activity.YoumiOffersWallActivity;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** YoumiFlutterPlugin */
public class YoumiFlutterPlugin implements FlutterPlugin, MethodCallHandler,ActivityAware{
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
//    youmi_flutter_plugin
//    youmi_offers_wall_sdk_plugin
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "youmi_flutter_plugin");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("init")) {
      String appId = call.argument("appId");
      YoumiOffersWallSdk.getInstance().init(activity.getApplication(), appId);
    } else if (call.method.equals("startOffersWall")) {
      String userId = call.argument("userId");
      YoumiOffersWallSdk.getInstance().startOffersWall(activity, userId);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {

  }
}
