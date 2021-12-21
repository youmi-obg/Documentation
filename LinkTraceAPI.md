This the document for link trace api. 


## Api
```
url: https://api.trailmi.com/track?token=your_token&country=JP&os=1&zone=1&carrier=target_carrier&url=www.bing.com

method: GET
```

### Request
| Parameter | Type | Mandatory | Example | Description |
|--------------|-------------|--------------|---------------|---------------|
| token | string | Y | we468GEH | Unique token to access API.|
| country | string | Y | US | [ISO Country code.](https://en.wikipedia.org/wiki/ISO_3166-1) | 
| zone | int | Y | 0 | Traffic type: 0-3G/4G, 1-WIFI. |
| os | int | Y | 1 | Operation system: 1-iOS, 2-Android. |
| url | string | Y | https://domain/path?query | The link you want to trace. |

Notes:
- 1 WIFI Trace: 1 Credit
- 1 3G/4G Trace: 20 Credit

### Response
| Parameter | Type | Example | Description |
|--------------|--------------|---------------|---------------|
| code | int | 0 | Message code. |
| msg | string | success | Message of trace result. |
| task_id | string | ge553GRO | A random id of your trace task. |
| result | Result |  | Trace result. |

#### Result
| Parameter | Type | Example | Description |
|--------------|--------------|---------------|---------------|
| urls | Url array |  | Redirect information. |
| delta | int | 3241 | Time cost(millsecond). |

### Url
| Parameter | Type | Example | Description |
|--------------|--------------|---------------|---------------|
| url | string | http://domain/path?query | Request url or redirect url. |
| status | int | 200 | Http status code. |
| delta | int | 352 | Time cost(millsecond) for this redirection or trace. |

## Example
Request:
```
curl https://api.trailmi.com/track?token=your_token&os=2&country=id&zone=1&carrier=&source=2&url=http://github.com
```

Response
```
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

