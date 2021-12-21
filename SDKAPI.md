# Server To Server Offer API For Publisher

Endpoint: https://api.mygo-free.com/v1/aff/offers


## Request Parameter

| Parameter   | Type   | Mandatory | Description       |
|-------------|--------|-----------|-------------------------------------------------------------------------------------------------------------------|
| app_id      | string | Y         | Identification key, available from our publisher website              |
| os          | string  | Y         | Filter offer by target OS: android / ios |
| page_size   | int    | N | Define the number of offers per page, page_size should be no greater than 10000 in case of request timeout             |
| page        | int    | N | Define which page to fetch, starting from 1      |
| gaid        | string    | Y | Android gaid. Required when os is android      |
| andid       | string    | Y | Android andid, Required when os is android      |
| thirdparty_id       | string    | N | thirdparty id      |
| country       | string    | N | Filter offer by target country. When it is empty, the country where the IP is located will be used  |

## Response Parameter of an Offer

| Parameter | Description | Type |
| ---- | ---- | ---- |
| id | Offer id | string |
| icon | Offer icon url | string |
| name | Offer name | string |
| conversion_flow | Publisher can get a conversion only if the user complete this conversion flow.| string |
| tracking_link | The tracking link of the offer.| string |
| payout | The revenue of the offer at the time request | string |
| preview_url | preview url of offer | string |
| package | The package name of the offer  | string |
| offer_type | CPI/CPA/CPL | string |
| payout_type | CPI: This means the offer is from an app store.<br> CPA: This means user will be redirected to a web task.<br> CPL: This means the offer is paid for an explicit sign-up | string |
| os | Target os: android / ios | string |
| countries | Targeting countries of offer | array |
| os_version | Targeting countries of os version | string |


## Other Resonse Parameter
| Parameter | Description | Type |
| ---- | ---- | ---- |
| p | Page | int |
| n | Page Size | int |
| tot | Total number | int |

## Example

```
GET https://api.mygo-free.com/v1/aff/offers?app_id=265b244af438f5833ce0a25fc45a6532&os=android&page_size=10&page=1  (only for test)

{
	"c": 0,
	"d": {
		"p": 1,
		"tot": 28,
		"n": 1,
		"data": [{
			"id": 2957231058,
			"icon": "https://lh3.googleusercontent.com/FxDVLdfxxBHwIonjxWrkmIt4NUguCVS1NUWwo9CWTsXSLChEx7asvT2lxI6m1q3zsx4=w96",
			"name": "Plus500: CFD Online Trading on Forex and Stocks",
			"conversion_flow": "1.Download and open the app\n2.Sign up for the app and try for a loan\n3.Redeem your points! Attention: New Users Only!",
			"tracking_link": "https://track.adxmel.com/aff_c?s=CI3BTRDSh4-CCxgKIAE&aff_sub1=&andid=&advid=&aff_sub4=sdk&aff_sub5=0",
			"payout": "3.60",
			"preview_url": "https://play.google.com/store/apps/details?id=com.Plus500",
			"package": "com.Plus500",
			"offer_type": "app",
			"payout_type": "fixed",
			"os": "android",
			"countries": [
				"BH",
				"CH",
				"DE",
				"HK",
				"KW",
				"NL",
				"QA"
			],
			"os_version": ""
		}]
	}
}
```


