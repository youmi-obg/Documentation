import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:youmi_flutter_plugin/youmi_flutter_plugin_method_channel.dart';

void main() {
  MethodChannelYoumiFlutterPlugin platform = MethodChannelYoumiFlutterPlugin();
  const MethodChannel channel = MethodChannel('youmi_flutter_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
