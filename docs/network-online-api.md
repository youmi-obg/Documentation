---
title: Online API
---

# Online API

*API can only be accessed via HTTP and returns data in JSON format.*

## API Request

### API Request Description

```
URL: https://jt.nemoka.com/v1/ol/ads
Method: POST
Headers: 'content-type: application/json'
```

### Request (JSON)

#### Request

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| access_token | String | Y | access_token=s61o92s72m0d71bj | Global unique API call credential |
| app_id | String | Y | app_id=3201162 | User Aid |
| client_info | [ClientInfo](#ClientInfo) | Y |  | Client basic information |
| ad_slots | [][AdSlot](#AdSlot) | Y |  | Ad slot basic information, maximum 10 ad slots per request |
| source | String | N |  | Sub-channel |

#### <a name="ClientInfo">ClientInfo</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| ip | String | Y | 184.34.21.5 | Client IP |
| ua | String | Y | Mozilla/5.0 (Linux;) (KHTML) Chrome/69 Mobile Safari/5 | Client user agent string |
| gaid | String | N |  | Google Advertising ID |
| idfa | String | N |  | iOS IDFA |

#### <a name="AdSlot">AdSlot</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| slot_id | Integer | Y | 8466110400 | Ad slot unique ID |
| dimensions | String | Y | 320×50 | Ad slot dimensions (optional: 320×50/300×250/320×480/728×90), if no matching ad, a similar ad will be selected. |

## Response (JSON)

#### Response

| Parameter | Type | Description |
|-----------|------|-------------|
| code | Integer | Error code |
| msg | String | Error message |
| ad_slot | [][AdSlotRes](#AdSlotRes) | Ad information |

#### <a name="AdSlotRes">AdSlot</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| slot_id | Integer | Y | 84610400 | Ad slot unique ID |
| ads | [][Ad](#Ad) | Y |  | Ad information |

#### <a name="Ad">Ad</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| oid | Integer | Y | 84610400 | Ad unique ID |
| app_info | [AppInfo](#AppInfo) | N |  | APP information (may only contain material information) |
| tracking_urls | [TrackingUrls](#TrackingUrls) | Y |  | Contains impression and click URLs |
| creatives | [Creatives](#Creatives) | N |  | Material information (may only contain APP information) |

#### <a name="AppInfo">AppInfo</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| app_name | String | Y |  | APP name |
| app_native_id | String | N |  | package/bundle ID |
| app_description | String | N |  | APP description |
| app_icon | String | N |  | APP icon link |

#### <a name="TrackingUrls">TrackingUrls</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| impression_url | String | Y | https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT | Ad impression callback URL, must be called after ad is displayed. |
| click_url | String | Y | https://jt.nemoka.com/v1/adj/log?f=ol&r=mc291cmNlPTk3MDMwMzA0MDAY | Ad click URL, must be redirected to after ad is clicked. |

#### <a name="Creatives">Creatives</a>

| Parameter | Type | Mandatory | Example | Description |
|-----------|------|-----------|---------|-------------|
| img_url | String | Y | https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT | Image URL |
| dimensions | String | Y | 728×90 | Image dimension information |

### Example:

#### Request

```bash
curl -X POST \
  https://jt.nemoka.com/v1/ol/ads \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
            "access_token":"{Your Access Token}",
            "app_id":10000,
            "source": "123",
            "client_info":{
                    "ip":"192.168.1.3",
                    "ua":"Mozilla/5.0 (Linux; Android 7.0.0;) AppleWebKit/527 (KHTML, like Gecko) Chrome/69.0.0.0 Mobile Safari/5",
                    "idfa": "",
                    "gaid": ""
            },
            "ad_slots":[
                    {
                        "slot_id":100000400,
                        "dimensions":"300x250"
                    },
                    {
                        "slot_id":100000401,
                        "dimensions":"300x50"
                    }
            ]
            }'
```

#### Response

```json
{
    "ad_slot": [
        {
            "slot_id": 100000400,
            "ads": [
                {
                    "oid": 100,
                    "app_info": {
                        "app_name":"xxx",
                        "app_category":"",
                        "app_description":"",
                        "app_icon":"http://google.cdn.com/1.png"
                    },
                    "tracking_urls": {
                        "impression_url": "https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT2RMRUwtY094aUFfZUdTSkNDc0FpajZBV2dEGIHYFiAC",
                        "click_url": "https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT2RMRUwt91cmNlPTk3MDMwMzA0MDAYgdgWIAM"
                    }
                }
            ]
        },
        {
            "slot_id": 100000401,
            "ads": [
                {
                    "oid": 200,
                    "tracking_urls": {
                        "impression_url": "https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT2tY094aU",
                        "click_url": "https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT2_cz1DTC1jTgdgWIAM"
                    },
                    "creatives": {
                        "img_url": "http://x.cdn.net/public/ad/creative/298825232acf53dfb3666b8963f011860c4-.jpg",
                        "dimensions": "300x250"
                    }
                }
            ]
        }
    ]
}
```
