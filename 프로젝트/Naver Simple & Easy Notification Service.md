---
create_at: 2024-06-27
status: 일시정지
진행중: false
일시정지: true
완료: false
reference:
update_at: 2024-06-29
기획: false
중요도: "1"
---

[SMS API 가이드](https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2)

# 1. 가입 및 키 발급

1. nCloud 콘솔에서 프로텍스트젝트를 생성 후 발급되는 ServiceID
2. nCloud에서 발급받는 인증키 중 access key
3. nCloud에서 발급받는 인증키 중 secret key

# 2. API sms 가이드 기본 정보 확인

## API URL : `{http}https://sens.apigw.ntruss.com/sms/v2`

## API Header

| 항목                       | Mandatory | 설명                                                                                                                          |
| ------------------------ | --------- | --------------------------------------------------------------------------------------------------------------------------- |
| Content-Type             | Mandatory | 요청 Body Content Type을 application/json으로 지정 (POST)                                                                          |
| x-ncp-apigw-timestamp    | Mandatory | - 1970년 1월 1일 00:00:00 협정 세계시(UTC)부터의 경과 시간을 밀리초(Millisecond)로 나타냄  <br>- API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주 |
| x-ncp-iam-access-key     | Mandatory | 포털 또는 Sub Account에서 발급받은 Access Key ID                                                                                      |
| x-ncp-apigw-signature-v2 | Mandatory | - Body를 Access Key ID와 맵핑되는 Secret Key로 암호화한 서명값<br>- HMAC 암호화 알고리즘은 HmacSHA256 사용                                          |

## 헤더 시그니처 생성 방법
시그니처는 공통 헤더 마지막 필드(x-ncp-apigw-signature-v2)에 들어가는 값입니다. 시그니처 생성 방법은 다음과 같습니다.

- 개행문자는 `\n`을 사용
- 요청에 맞게 StringToSign을 생성하고 SecretKey로 HmacSHA256 알고리즘으로 암호화한 후 Base64로 인코딩
- 인코딩한 값을 x-ncp-apigw-signature-v2로 사용

```java
public String makeSignature() {
	String space = " ";					// one space
	String newLine = "\n";					// new line
	String method = "GET";					// method
	String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
	String timestamp = "{timestamp}";			// current timestamp (epoch)
	String accessKey = "{accessKey}";			// access key id (from portal or Sub Account)
	String secretKey = "{secretKey}";

	String message = new StringBuilder()
		.append(method)
		.append(space)
		.append(url)
		.append(newLine)
		.append(timestamp)
		.append(newLine)
		.append(accessKey)
		.toString();

	SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
	Mac mac = Mac.getInstance("HmacSHA256");
	mac.init(signingKey);

	byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
	String encodeBase64String = Base64.encodeBase64String(rawHmac);

  return encodeBase64String;
}
```


# 3. API sms 발송 가이드 확인

## 요청 URL : `{http}POST https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/messages`

## 요청 헤더 형식
```http
Content-Type: application/json; charset=utf-8
x-ncp-apigw-timestamp: {Timestamp}
x-ncp-iam-access-key: {Sub Account Access Key}
x-ncp-apigw-signature-v2: {API Gateway Signature}
```

## 요청 Body 형식
```json
{
    "type":"(SMS | LMS | MMS)",
    "contentType":"(COMM | AD)",
    "countryCode":"string",
    "from":"string",
    "subject":"string",
    "content":"string",
    "messages":[
        {
            "to":"string",
            "subject":"string",
            "content":"string"
        }
    ],
    "files":[
        {
             "fileId": "string"
        }
    ],
    "reserveTime": "yyyy-MM-dd HH:mm",
    "reserveTimeZone": "string"
}
```

| 항목               | Mandatory | Type   | 설명        | 비고                                                                                                                                              |
| ---------------- | --------- | ------ | --------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| type             | Mandatory | String | SMS Type  | SMS, LMS, MMS (소문자 가능)                                                                                                                          |
| contentType      | Optional  | String | 메시지 Type  | - COMM: 일반메시지  <br>- AD: 광고메시지  <br>- default: COMM                                                                                             |
| countryCode      | Optional  | String | 국가 번호     | - SENS에서 제공하는 국가로의 발송만 가능  <br>- default: 82  <br>- [국제 SMS 발송 국가 목록](https://guide.ncloud-docs.com/docs/sens-smspolicy)                        |
| from             | Mandatory | String | 발신번호      | 사전 등록된 발신번호만 사용 가능                                                                                                                              |
| subject          | Optional  | String | 기본 메시지 제목 | LMS, MMS에서만 사용 가능  <br>- LMS, MMS: 최대 40byte                                                                                                    |
| content          | Mandatory | String | 기본 메시지 내용 | - SMS: 최대 90byte  <br>- LMS, MMS: 최대 2000byte                                                                                                   |
| messages         | Mandatory | Object | 메시지 정보    | - 아래 항목 참조 (messages.XXX)  <br>- 최대 100개                                                                                                        |
| messages.to      | Mandatory | String | 수신번호      | 붙임표 ( - )를 제외한 숫자만 입력 가능                                                                                                                        |
| messages.subject | Optional  | String | 개별 메시지 제목 | LMS, MMS에서만 사용 가능  <br>- LMS, MMS: 최대 40byte                                                                                                    |
| messages.content | Optional  | String | 개별 메시지 내용 | - SMS: 최대 90byte  <br>- LMS, MMS: 최대 2000byte                                                                                                   |
| files.fileId     | Optional  | String | 파일 아이디    | MMS에서만 사용 가능  <br>[파일 업로드](https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2#%ED%8C%8C%EC%9D%BC%EC%97%85%EB%A1%9C%EB%93%9C) 참조 |
| reserveTime      | Optional  | String | 예약 일시     | 메시지 발송 예약 일시 (yyyy-MM-dd HH:mm)                                                                                                                 |
| reserveTimeZone  | Optional  | String | 예약 일시 타임존 | - 예약 일시 타임존 (기본: Asia/Seoul)  <br>- [지원 타임존 목록](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)  <br>- TZ database name 값 사용      |

## 응답 Body 형식
```Json
{
    "requestId":"string",
    "requestTime":"string",
    "statusCode":"string",
    "statusName":"string"
}
```

|항목|Mandatory|Type|설명|비고|
|---|---|---|---|---|
|requestId|Mandatory|String|요청 아이디||
|requestTime|Mandatory|DateTime|요청 시간|yyyy-MM-dd'T'HH:mm:ss.SSS|
|statusCode|Mandatory|String|요청 상태 코드|- 202: 성공  <br>- 그 외: 실패  <br>- HTTP Status 규격을 따름|
|statusName|Mandatory|String|요청 상태명|- success: 성공  <br>- fail: 실패|

## 응답 Status 종류

| HTTP Status | Desc                  |
| ----------- | --------------------- |
| 202         | Accept (요청 완료)        |
| 400         | Bad Request           |
| 401         | Unauthorized          |
| 403         | Forbidden             |
| 404         | Not Found             |
| 429         | Too Many Requests     |
| 500         | Internal Server Error |


# 4. 요약 흐름 작성

1.	**클라이언트 요청** : 클라이언트에서 JavaScript를 통해 휴대폰 번호 입력 및 `MessageDto` 생성, `XHR` 설정통해 `/sms/send`로 POST 요청 전송.
2.	**요청 수신 및 초기 설정** : `SmsController`에서 요청 수신, `SmsService`에서 인증번호 및 설정 값 로드.
3.	**현재 시간 및 HTTP 헤더 설정** : `System.currentTimeMillis(),` `HttpHeaders `설정.
4.	**서명 생성** : `getSignature(time) `메서드로 서명 생성.
5.	**요청 메시지 객체 생성** : `인증번호`와 `MessageDto`를 포함한 `SmsRequestDto` 생성.
6.	**JSON 변환** : `ObjectMapper`로 `SmsRequestDto`를 JSON 문자열로 변환.
7.	**HTTP 요청 준비** : JSON 문자열과 헤더를 합쳐 `HttpEntity<String>` 생성.
8.	**외부 SMS API 요청** : RestTemplate으로 외부 SMS API에 POST 요청.
9.	**응답 처리** : 외부 API 응답을 `SmsResponseDto`로 처리.
10. **데이터베이스 저장** : `SmsRepository`로 `Sms` 객체 저장.
11. **클라이언트 응답 반환** : 클라이언트에 SMS 발송 결과 응답.

# 5. 코드 작성

## 5-1. 클라이언트 요청



```js

```