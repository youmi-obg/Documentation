
**Flutter SDK 对接**

1.在pubspec.yaml文件中的dependencies:中添加youmi_flutter_plugin依赖

dependencies:
  flutter:
    sdk: flutter

  youmi_flutter_plugin: ^2.7.0

2.引入youmi_flutter_plugin的package

 import 'package:youmi_flutter_plugin/youmi_flutter_plugin.dart';

3.进行积分墙的sdk初始化 ('appId'为你在有米官网注册成功后的渠道aid，该aid不能为空，如果为空无法正常使用SDK功能)

 YoumiOffersWallSdk().init(appId: 'appId');

4.SDK广告墙的启动方式，在需要跳转到SDK的地方，添加代码 YoumiOffersWallSdk().startOffersWall(userId) context为Context类的实例，userId为String类型，userId为该APP用户的唯一Id，userId不为空

 YoumiOffersWallSdk().startOffersWall("userId");

5.在项目的android文件夹中的app目录中的build.gradle中的defaultConfig中添加multiDexEnabled true并且设置sdk的最低版本为      minSdkVersion 19

    defaultConfig {
        applicationId "com.example.sdk_flutter_demo"
        minSdkVersion 19
        targetSdkVersion flutter.targetSdkVersion
        versionCode flutterVersionCode.toInteger()
        versionName flutterVersionName
        multiDexEnabled true
    }

