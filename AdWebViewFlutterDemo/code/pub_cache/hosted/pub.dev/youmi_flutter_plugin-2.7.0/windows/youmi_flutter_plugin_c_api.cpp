#include "include/youmi_flutter_plugin/youmi_flutter_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "youmi_flutter_plugin.h"

void YoumiFlutterPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  youmi_flutter_plugin::YoumiFlutterPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
