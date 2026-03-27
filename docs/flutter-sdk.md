---
title: Flutter SDK Integration
---

# Flutter SDK Integration

1. Add the `youmi_flutter_plugin` dependency under `dependencies:` in the `pubspec.yaml` file

```yaml
dependencies:
  flutter:
    sdk: flutter

  youmi_flutter_plugin: ^2.7.0
```

2. Import the `youmi_flutter_plugin` package

```dart
 import 'package:youmi_flutter_plugin/youmi_flutter_plugin.dart';
```

3. Initialize the offers wall SDK ('appId' is your channel aid obtained after successful registration on Youmi's official website. This aid must not be empty; otherwise, the SDK functions will not work properly)

```dart
 YoumiOffersWallSdk().init(appId: 'appId');
```

4. To launch the SDK offers wall, add the following code where you need to navigate to the SDK. `userId` is of type String and must be a unique ID for the app user (it must not be empty)

```dart
 YoumiOffersWallSdk().startOffersWall("userId");
```

5. In your project's `android/app/build.gradle`, add `multiDexEnabled true` inside `defaultConfig` and set the minimum SDK version to `minSdkVersion 19`

```gradle
    defaultConfig {
        applicationId "com.example.sdk_flutter_demo"
        minSdkVersion 19
        targetSdkVersion flutter.targetSdkVersion
        versionCode flutterVersionCode.toInteger()
        versionName flutterVersionName
        multiDexEnabled true
    }
```

6. Open the android module in your project, and register the plugin in the `GeneratedPluginRegistrant` file located under `android/app/src/main/java/io/flutter/plugins/`:

```java
    @Keep
    public final class GeneratedPluginRegistrant {
      private static final String TAG = "GeneratedPluginRegistrant";
      public static void registerWith(@NonNull FlutterEngine flutterEngine) {
       try {
         flutterEngine.getPlugins().add(new net.youmi.overseas.youmi_flutter_plugin.YoumiFlutterPlugin());
       } catch (Exception e) {
         Log.e(TAG, "Error registering plugin youmi_flutter_plugin, net.youmi.overseas.youmi_flutter_plugin.YoumiFlutterPlugin", e);
       }
     }
    }
```

7. After registering, sync or rebuild the project. Once the `youmi_flutter_plugin` module is generated, sync to the latest SDK version in `build.gradle (Module: youmi_flutter_plugin)`:

```gradle
     dependencies{
         implementation 'io.github.youmi-obg:offerswall:2.7.6'
     }
```
