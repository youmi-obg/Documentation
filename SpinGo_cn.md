# SpinGo互动广告接入指南

![SpinGo业务流程](https://github.com/youmi-obg/Documentation/raw/main/images/spingo.png)

## 1. 接入方式

- SpinGo互动广告采用H5形式对接，适配各种形式的展示位置。我们为每个广告位提供一个H5链接，如" https://lp.spingomi.com/lp/spingo/c1.html?app_key={your_app_key}&advid={gaid} "，开发者伙伴仅需提供一个广告位，并填充链接中 {gaid} 谷歌广告id信息即可，点击广告位后在外部浏览器或app内webview打开相应的H5页面。
- 其中 WebView 形式接入建议参考以下文档进行调整，以兼容各种广告跳转场景：
  - [互动广告安卓WebView对接](https://github.com/youmi-obg/Documentation/blob/main/InteractiveAdsWebView_cn.md)
  - [互动广告安卓Flutter WebView对接](https://github.com/youmi-obg/Documentation/blob/main/InteractiveAdsWebViewFlutter_cn.md)
  - [互动广告IOS WebView对接](https://github.com/youmi-obg/Documentation/blob/main/AdWebviewIOSDemo/README.md)

## 2. 广告位选择

- 互动广告除了能以开屏、插屏、Banner、信息流等常规形式展现外，还可在各种非标广告位进行展现，如悬浮ICON、推送消息、Tab功能入口、签到弹窗等，其中能吸引用户主动点击的广告位更为推荐，如悬浮ICON，Banner等广告位，eCPM相比其他广告位整体也会更高些

## 3. 数据展示

- 对接上线后，可在开发者后台（https://offers.youmi.net/snapshot） 查看相关展示收入数据
![开发者后台](https://github.com/youmi-obg/Documentation/raw/main/images/backend2.png)
