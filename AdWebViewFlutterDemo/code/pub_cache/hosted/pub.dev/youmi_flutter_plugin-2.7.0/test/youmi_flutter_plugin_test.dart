import 'package:flutter_test/flutter_test.dart';
import 'package:youmi_flutter_plugin/youmi_flutter_plugin.dart';
import 'package:youmi_flutter_plugin/youmi_flutter_plugin_platform_interface.dart';
import 'package:youmi_flutter_plugin/youmi_flutter_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockYoumiFlutterPluginPlatform
    with MockPlatformInterfaceMixin
    implements YoumiFlutterPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final YoumiFlutterPluginPlatform initialPlatform = YoumiFlutterPluginPlatform.instance;

  test('$MethodChannelYoumiFlutterPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelYoumiFlutterPlugin>());
  });

  test('getPlatformVersion', () async {
    YoumiFlutterPlugin youmiFlutterPlugin = YoumiFlutterPlugin();
    MockYoumiFlutterPluginPlatform fakePlatform = MockYoumiFlutterPluginPlatform();
    YoumiFlutterPluginPlatform.instance = fakePlatform;

    expect(await youmiFlutterPlugin.getPlatformVersion(), '42');
  });
}
