# Online API

*API只能通过HTTP访问，并以JSON格式返回数据。

## API Request

### API 请求说明
    URL: https://jt.nemoka.com/v1/ol/ads
    Method: POST
    Headers: 'content-type: application/json'

### Request (JSON)

#### Request
| 参数    | 类型                                   | 必须 | 样例                                     |描述               |
|--------------|----------------------------------------|-----------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| access_token | String                                 | Y         | access_token=s61o92s72m0d71bj      | 全局唯一接口调用凭据                                                                                      |
| app_id       | String                                 | Y         | app_id=3201162                              | 用户Aid      |
| client_info  | <a href="#ClientInfo">ClientInfo</a>   | Y         |                 | 客户端基本信息   |
| ad_slots     | []<a href="#AdSlot">AdSlot</a>         | Y         |             | 广告位基本信息，一次请求最多10个广告位 |
source     | String         | N         |             | 子渠道 |

#### <a name="ClientInfo">ClientInfo</a>
| 参数    | 类型     | 必须 | 样例                                                 |描述               |
|--------------|----------|-------|---------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| ip           | String   | Y     | 184.34.21.5                                             | 客户端IP                                                                                     |
| ua           | String   | Y     | Mozilla/5.0 (Linux;) (KHTML) Chrome/69 Mobile Safari/5  | 客户端user agent字符串                                                                                      |
| gaid         | String   | N     |                                                         | Google Advertising ID                                                                                    |
| idfa         | String   | N     |                                                         | iOS IDFA                                                                                    |

#### <a name="AdSlot">AdSlot</a>
| 参数    | 类型     | 必须 | 样例           |描述               |
|--------------|----------|-----------|------------------ |---------------------------------------------------- |
| slot_id      | Integer   | Y         | 8466110400        | 广告位唯一id                                         |
| dimensions   | String   | Y         | 320×50            | 广告位长宽尺寸（可选320×50/300×250/320×480/728×90），若无匹配的广告，则会选择一个类似的广告。    |


## Response (JSON)
#### Response
| 参数 | 类型                                  | 描述                       |
| ----      | ----                                  | ----                              |
| code      | Integer                               | 错误码             |
| msg       | String                               | 错误信息             |
| ad_slot   | []<a href="#AdSlotRes">AdSlot</a>     | 广告信息  |


#### <a name="AdSlotRes">AdSlot</a>
| 参数     | 类型                      | 必须 | 样例        |描述               |
|-------------- |----------                 |-----------|----------------|------------------------------------------------------------------------------------------------------------------|
| slot_id       | Integer                   | Y         | 84610400       | 广告位唯一id                                                                                     |
| ads           | []<a href="#Ad">Ad</a>    | Y         |                | 广告信息                                                                                      |


#### <a name="Ad">Ad</a>
| 参数     | 类型                                      | 必须 | 样例        |描述               |
|-------------- |----------                                 |-----------|----------------|------------------------------------------------------------------------------------------------------------------|
| oid           | Integer                                   | Y         | 84610400       | 广告唯一id                                                                                     |
| app_info      | <a href="#AppInfo">AppInfo</a>            | N         |                | APP信息（可能仅含素材信息）                                                                                      |
| tracking_urls | <a href="#TrackingUrls">TrackingUrls</a>  | Y         |                | 包含展示及跳转链接                                                                                      |
| creatives     | <a href="#Creatives">Creatives</a>        | N         |                | 素材信息（可能仅含APP信息）                                                                                      |


#### <a name="AppInfo">AppInfo</a>
| 参数         | 类型     | 必须 | 样例                                     |描述               |
|--------------     |----------|-----------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| app_name          | String   | Y         |                 | APP名称                                                                                 |
| app_native_id     | String   | N         |                 | package/bundle ID                                                                                |
| app_description   | String   | N         |                 | APP的描述                                                                                 |
| app_icon          | String   | N         |                 | APP的icon链接                                                                                 |


#### <a name="TrackingUrls">TrackingUrls</a>
| 参数               | 类型         | 必须    | 样例                                     |描述               |
|--------------     |----------    |--------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| impression_url    | String       | Y      | https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT       | 广告展示回调链接，在广告展示完后必须进行回调。 |
| click_url         | String       | Y      | https://jt.nemoka.com/v1/adj/log?f=ol&r=mc291cmNlPTk3MDMwMzA0MDAY       | 广告跳转链接，在点击广告后必须进行跳转。  |


#### <a name="Creatives">Creatives</a>
| 参数               | 类型         | 必须    | 样例                                     |描述               |
|--------------     |----------    |--------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| img_url           | String       | Y      | https://jt.nemoka.com/v1/adj/log?f=ol&r=ChxDT       | 图片链接 |
| dimensions        | String       | Y      | 728×90      | 图片尺寸信息  |



### 样例:
#### Request
```
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
```
-H 'content-type: application/json'
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


