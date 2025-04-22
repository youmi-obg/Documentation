import 'dart:async';
import 'package:flutter/services.dart';

class YoumiOffersWallSdk {
  static const _channel = MethodChannel('youmi_flutter_plugin');
  static final YoumiOffersWallSdk _instance = YoumiOffersWallSdk._internal();

  factory YoumiOffersWallSdk() => _instance;

  YoumiOffersWallSdk._internal();

  Future<void> init({required String appId}) async {
    return _channel.invokeMethod("init", <String, dynamic>{'appId': appId});
  }

  Future<void> startOffersWall(String userId) async {
    return _channel.invokeMethod("startOffersWall", <String, dynamic>{
      'userId': userId,
    });
  }
}
