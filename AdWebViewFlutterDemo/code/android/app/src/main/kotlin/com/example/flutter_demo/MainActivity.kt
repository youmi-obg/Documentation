package com.example.flutter_demo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.NonNull
import com.pichillilorenzo.flutter_inappwebview.InAppWebViewFlutterPlugin
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugins.GeneratedPluginRegistrant
import my_custom_plugin.OpenAppUtilsPlugin

class MainActivity: FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        flutterEngine.plugins.add(OpenAppUtilsPlugin())
        flutterEngine.plugins.add(InAppWebViewFlutterPlugin())
        GeneratedPluginRegistrant.registerWith(flutterEngine)
    }
}
