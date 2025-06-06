# SpinGo Interactive Advertising Integration Guide

![SpinGo Business Process](https://github.com/youmi-obg/Documentation/raw/main/images/spingo.png)

## 1. Integration Method

- SpinGo interactive advertising integrates in H5 format, suitable for various display positions. We provide an H5 link for each ad space, such as "https://lp.spingomi.com/lp/spingo/c1.html?app_key={your_app_key}&advid={gaid}". Developer partners only need to provide an ad space and fill in the {gaid} Google Ads ID in the link. Once clicked, the ad space will open the corresponding H5 page in an external browser or app's webview.
- For WebView integration, it is recommended to refer to the following documentation for necessary adjustments to accommodate various ad jump scenarios:
  - [Interactive Ads Android WebView Integration](https://github.com/youmi-obg/Documentation/blob/main/InteractiveAdsWebView.md)
  - [Interactive Ads Flutter WebView Integration](https://github.com/youmi-obg/Documentation/blob/main/InteractiveAdsWebviewFlutter.md)
  - [Interactive Ads iOS WebView Integration](https://github.com/youmi-obg/Documentation/blob/main/AdWebviewIOSDemo/README.md)

## 2. Ad Space Selection

- Interactive ads can be displayed in regular formats such as splash screens, interstitials, banners, and news feeds, as well as in various non-standard ad spaces, such as floating icons, push notifications, tab function entries, and sign-in pop-ups. Ad spaces that can attract users to click, such as floating icons and banners, are particularly recommended, as the eCPM for these ad spaces is generally higher than for others.

## 3. Data Display

- After the integration is live, relevant revenue data can be viewed in the developer backend at (https://offers.youmi.net/snapshot).
![Developer Backend](https://github.com/youmi-obg/Documentation/raw/main/images/backend4.png)
