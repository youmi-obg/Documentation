---
title: 链接追踪 API
---

# 链接追踪 API

本文档介绍链接追踪 API 的使用方法。

## API

```
url: https://api.trailmi.com/track?token=your_token&country=JP&os=1&zone=1&carrier=target_carrier&url=www.bing.com

method: GET
```

### 请求参数

| 参数 | 类型 | 必填 | 示例 | 描述 |
|------|------|------|------|------|
| token | string | Y | we468GEH | 访问 API 的唯一令牌。 |
| country | string | Y | US | [ISO 国家代码。](https://en.wikipedia.org/wiki/ISO_3166-1) |
| zone | int | Y | 0 | 流量类型：0-3G/4G，1-WIFI。 |
| os | int | Y | 1 | 操作系统：1-iOS，2-Android。 |
| url | string | Y | https://domain/path?query | 您要追踪的链接。 |

注意：
- 1 次 WIFI 追踪：1 积分
- 1 次 3G/4G 追踪：20 积分

### 响应参数

| 参数 | 类型 | 示例 | 描述 |
|------|------|------|------|
| code | int | 0 | 消息代码。 |
| msg | string | success | 追踪结果消息。 |
| task_id | string | ge553GRO | 您的追踪任务的随机 ID。 |
| result | Result |  | 追踪结果。 |

#### Result

| 参数 | 类型 | 示例 | 描述 |
|------|------|------|------|
| urls | Url array |  | 重定向信息。 |
| delta | int | 3241 | 耗时（毫秒）。 |

### Url

| 参数 | 类型 | 示例 | 描述 |
|------|------|------|------|
| url | string | http://domain/path?query | 请求 URL 或重定向 URL。 |
| status | int | 200 | HTTP 状态码。 |
| delta | int | 352 | 此次重定向或追踪的耗时（毫秒）。 |

## 示例

请求：

```bash
curl https://api.trailmi.com/track?token=your_token&os=2&country=id&zone=1&carrier=&source=2&url=http://github.com
```

响应：

```json
{
    "msg": "success",
    "result": {
        "urls": [
            {
                "url": "http://github.com",
                "status": 301,
                "delta": 779
            },
            {
                "url": "https://github.com/",
                "status": 200,
                "delta": 1821
            }
        ],
        "delta": 2600
    }
}
```
