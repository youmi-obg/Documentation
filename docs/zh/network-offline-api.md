---
title: Server To Server Offer API （媒体端）
---

# Server To Server Offer API （媒体端）

接口地址: http://ad.api.yyapi.net/v2/offline

## 请求参数

| 参数 | 类型 | 必填 | 描述 |
|------|------|------|------|
| app_id | string | Y | 识别密钥，可从我们的媒体网站获取 |
| page_size | int | N | 定义每页的 offer 数量，page_size 不应大于 10000 以防止请求超时 |
| page | int | N | 定义要获取的页面，从 1 开始 |
| payout_type | string | N | 过滤 offer 结算类型：CPA / CPI / CPL |
| os | string | N | 按目标操作系统过滤 offer：android / ios |
| country | string | N | 按目标国家过滤 offer，使用 `,` 分隔多个国家 |
| offer_ids | string | N | 按目标 offer 过滤，使用 , 分隔多个 offer |

## 响应参数

| 参数 | 类型 | 描述 |
|------|------|------|
| id | string | Offer 的 ID |
| name | string | Offer 的名称 |
| package | string | Offer 的包名 |
| kpi | string | Offer 的 KPI 描述 |
| adtxt | string | Offer 的介绍 |
| payout | double | Offer 的结算金额（美元），动态结算为 0。不一定是最终结算金额，我们建议在您的回调链接中添加 `{revenue}` 宏（见下文） |
| cap | int | 每日最大允许的总转化数，无限制为 0 |
| trackinglink | string | Offer 的追踪链接 |
| country | array | 目标国家，为空表示全球 |
| os | array | 目标操作系统：android / ios |
| traffic | string | 激励或非激励 |
| os_version | string | Offer 的最低操作系统版本（例如：4.2.2） |
| carrier | array | Offer 的目标运营商 |
| preview_url | string | Offer 的预览链接 |
| icon_url | string | Offer 的图标链接 |
| creative | array | Offer 的图片素材 |
| video | array | Offer 的视频素材 |
| store_label | array | Offer 的商店（AppStore/GooglePlay）标签 |
| store_rating | string | Offer 的商店（AppStore/GooglePlay）评分 |
| size | string | 包的大小 |
| conversion_flow | string | 只有当用户完成此转化流程后，媒体才能获得转化。 |
| payout_type | string | CPI：这意味着 offer 来自应用商店。<br> CPA：这意味着用户将被重定向到网页任务。<br> CPL：这意味着 offer 是为明确的注册付费 |
| mandatory_device | map<string, bool> | 如果标记为 "true"，则为必填设备参数，详见追踪链接宏，未能提供必填参数将导致无效点击响应 |
| stream_type | string | Offer 的流类型：APP / ADULT / SMARTLINK / SUBSCRIPTION |
| category | string | 有米的广告分类，类似于 Google Play |
| task_description_for_user | string | 给用户的任务描述 |
| events | array | Offer 的事件 |

### Events

| 参数 | 类型 | 描述 |
|------|------|------|
| event_name | string | "register"、"first_successful_login"、"deposit"、"level_achieved"、"install"、"subscribe"、"purchase"、"order"、"add_to_cart"、"video_stream"、"kyc"、"rejected"、"retention"、"retention_2"、"leads"、"cpd"、"gameplay"、"loan_apply"、"loan_approved"、"ftd"、"other" 之一 |
| event_type | string | "Settlement" 或 "Record" |

## 示例

```http
GET http://ad.api.yyapi.net/v2/offline?app_id=c46b362886e42385d30c83d76abc3c51&page=1&page_size=20&os=android&country=IN&payout_type=CPI
```

```json
{
    "c": 0,
    "total": 1,
    "page": 1,
    "page_size": 1,
    "n": 1,
    "offers": [
        {
            "id": "864378489573216256",
            "name": "Space Manager",
            "package": "com.mobileartsme.spacemanager",
            "kpi": "hard: CTIT less than 20 seconds will not be paid",
            "adtxt": "SPACE MANAGER mobile application allows you to effortlessly back up and organize data on your mobile deviceIt will automatically upload your data on cloud when limit set by you get reached, freeing up your device's internal memory and improving your phone's performance Space Manager is a utility app that provides numerous features: - View and organize your data on: Internal memory, SdCard memory and Online memory (Cloud storage) - Up to 500 GB of online space to backup your images, videos and audios- Get notified when internal phone storage is less than limit set in settings- Manage content automatically by choosing size of images/videos/audios to copy or move to cloud, then Space Manager will automatically select your oldest data from your internal memory- Run automatic free up space  Space Manager FREE version: - 2 GB of free online storage - All features provided by Space manager  Space Manager Premium: Subscribe once, and sit back and enjoy all FREE & Premium features of Space Manage",
            "payout": 1.12,
            "cap": 0,
            "trackinglink": "http://t.api.yyapi.net/v1/tracking?ad=864378489573216256&app_id=b3a3277b8fdd54bc&pid=3",
            "country": [
                "AE",
                "BH",
                "QA"
            ],
            "os": [
                "android"
            ],
            "traffic": "incentive",
            "os_version": "",
            "carrier": [],
            "device": [],
            "preview_url": "https://play.google.com/store/apps/details?id=com.mobileartsme.spacemanager",
            "icon_url": "https://lh3.googleusercontent.com/JErLykXFTGx8E88SwMzDE4m2jEFSXlrbEGrXm9rO-q5kkyJIXl9vvkzh9979NPNdDb0=w96",
            "creative": [
                {
                    "url": "https://lh3.googleusercontent.com/KyqDq7p4f_bKV5gJVpgayLVAXW8GXznwDYEpfVfI4nhHcf-nfwIl3WFXXnDcxk0kOKw",
                    "mime": "image/png",
                    "width": 288,
                    "height": 512
                }
            ],
            "video": [
                {
                    "url": "https://xxx.mp4",
                    "mime": "video/mp4"
                }
            ],
            "store_label": [
                "Tools"
            ],
            "store_rating": "4.2",
            "size": "16M",
            "conversion_flow": "",
            "payout_type": "CPI",
            "mandatory_device": {
                "imei": false,
                "mac": false,
                "andid": true,
                "advid": false,
                "idfa": false,
                "udid": false
            },
            "category": "APP",
            "task_description_for_user": {
                "en": "1. Download and Install App\n2. Create an account\n3. Open the App and play 15s at least"
            },
            "events": [
                {
                "event_name": "deposit",
                "event_type": "Record"
                },
                {
                "event_name": "order",
                "event_type": "Record"
                },
                {
                "event_name": "register",
                "event_type": "Settlement"
                }
            ]
        }
    ]
}
```

## 注意事项

- API 只能通过 HTTP GET 请求访问，并返回 JSON。
- 我们确保您获得的所有 offer 在您请求时都是有效的，但我们可能随时下线 offer。为了最小化对已下线 offer 的无效点击，我们建议您每 15-30 分钟请求一次，消失的 offer 应被视为无法访问。
- 短时间内过多请求可能会导致 429（请求过多）HTTP 响应码，'Retry-After' 响应头中提供了重试秒数。

## 追踪链接参数

| 参数 | 描述 |
|------|------|
| chn | 用于您的子渠道 ID 或位置 ID（最大 150） |
| imei | 用户的 IMEI，如果 "mandatory_device" 中为 "true" 则必填 |
| mac | 用户的 MAC 地址，如果 "mandatory_device" 中为 "true" 则必填 |
| andid | 用户的 Android ID，如果 "mandatory_device" 中为 "true" 则必填 |
| advid | 用户的 Google 广告 ID（GAID），如果 "mandatory_device" 中为 "true" 则必填 |
| idfa | 用户的 IDFA，如果 "mandatory_device" 中为 "true" 则必填 |
| udid | 用户的 UDID，如果 "mandatory_device" 中为 "true" 则必填 |
| device_id | 用户的设备 ID（例如 OAID） |
| app_name | 发起点击的应用的包名 |
| aff_sub | 用于您的点击 ID 或交易 ID，以唯一标识单次点击（最大 256） |
| aff_sub2 | 用于您的自定义参数（最大 256） |
| aff_sub3 | 用于您的自定义参数（最大 256） |
| ua | 不要提取 CFNetwork user-agent，提取另一个（URL 编码） |
| language | 提供语言和区域设置；例如，en-US |
| ip | 用户的 IP |
| os_version | 设备操作系统版本。<br />例如：<br />    Android: 12 <br />    iOS: 16.2 |
| device_model | 设备型号。<br />例如：<br />    Android: Pixel 5 <br />    iOS: iphone 或 ipad（全部小写） |
| brand | 设备品牌。例如：OPPO、XIAOMI、HUAWEI、APPLE |

### 注意事项

- 未能提供必填设备参数将导致无效点击。建议尽可能传递设备参数，因为这些信息将更好地识别点击来源，并带来更好的转化率。
- 过长的参数将在我们这边被截断，所以请遵守最大长度要求。

# 转化回调协议

通常我们会在转化发生时立即发送回调。
但是，在服务器故障、延迟转化确认等罕见情况下，可能会出现延迟回调（最多 7 天）。
我们通过 HTTP GET 方法发送回调请求，请求失败时重试（HTTP 响应码 5XX）。
可能会有多个与同一转化相关的回调，因此接收方有责任确保回调端点是幂等的。

## 回调宏

| 宏 | 描述 | 是否必需 |
|----|------|----------|
| {chn} | 您的子渠道 ID 或位置 ID | 否 |
| {oid} | Offer ID | 否 |
| {package} | 此 offer 的包名 | 否 |
| {payout} | 此次转化的结算价格（美元）。 | 是 |
| {aff_sub} | 您的点击 ID 或交易 ID。广告网络唯一点击标识符 | 是 |
| {aff_sub2} | 在追踪链接中传递 | 否 |
| {aff_sub3} | 在追踪链接中传递 | 否 |
| {advid} | Google 广告 ID | 否 |
| {idfa} | IOS 广告标识符 | 否 |
| {clk_ip} | 回调用户的点击 IP | 否 |
| {order_id} | 转化 ID，有米为每次转化生成的唯一标识符。相同的 order_id 意味着相同的转化。开发人员应注意，相同的 order_id 只能结算一次 | 否 |
| {package} | 移动应用的唯一标识符。此 offer 的包名 | 否 |
| {is_settle} | 如果是结算事件则 is_settle=1，如果是非结算事件则 is_settle=0 | 否 |
| {event_name} | 只有当广告主回调事件名称时，postback 才会带上事件名称 | 否 |

## 回调 IP 白名单

可按需提供。
