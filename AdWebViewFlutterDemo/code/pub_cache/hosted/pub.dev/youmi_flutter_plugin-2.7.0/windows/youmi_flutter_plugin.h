#ifndef FLUTTER_PLUGIN_YOUMI_FLUTTER_PLUGIN_H_
#define FLUTTER_PLUGIN_YOUMI_FLUTTER_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace youmi_flutter_plugin {

class YoumiFlutterPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  YoumiFlutterPlugin();

  virtual ~YoumiFlutterPlugin();

  // Disallow copy and assign.
  YoumiFlutterPlugin(const YoumiFlutterPlugin&) = delete;
  YoumiFlutterPlugin& operator=(const YoumiFlutterPlugin&) = delete;

 private:
  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace youmi_flutter_plugin

#endif  // FLUTTER_PLUGIN_YOUMI_FLUTTER_PLUGIN_H_
