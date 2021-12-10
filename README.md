# 게시판 REST API
## 1. 소개
- Spring Boot Version : 2.6.1
- JDK Version : 11
- Database : H2

## 2. 구조
```
├─main
│  ├─generated
│  ├─java
│  │  └─com
│  │      └─chequer
│  │          ├─config
│  │          │  ├─aspect
│  │          │  └─jwt
│  │          ├─domain
│  │          │  ├─auth
│  │          │  ├─board
│  │          │  ├─common
│  │          │  └─member
│  │          ├─exception
│  │          ├─service
│  │          └─web
│  │              ├─common
│  │              └─controller
│  └─resources
│      ├─static
│      └─templates
└─test
    ├─java
    │  └─com
    │      └─chequer
    │          └─web
    │              └─controller
    │                  └─common
    └─resources

```

## 3. 시작하기
```bash
# 클론
$ git clone https://github.com/choiyuri/chequer.git

# 경로로 이동
$ cd chequer

# 빌드
$ ./gradlew build

# JDK 버전 안맞을 경우 (jdk-11)
$ ./gradlew build -Dorg.gradle.java.home=/JDK_PATH

# 시작
$ ./gradlew bootRun
```

## 4. 응답 포맷

### 4.1. 사용자 API - 사용자 생성
- 요청
```bash
curl -X POST "http://localhost:8080/api/v1/signup" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"choiyuri9107@gmail.com\", \"firstName\": \"유리\", \"lastName\": \"최\", \"password\": 1234}"
```
- 응답
```json
{
  "result": "SUCCESS",
  "data": {
    "id": 1,
    "firstName": "유리",
    "lastName": "최",
    "email": "choiyuri9107@gmail.com",
    "role": "USER"
  }
}
```

### 4.2. 사용자 API - 사용자 인증
- 요청
```bash
curl -X POST "http://localhost:8080/api/v1/auth" 
-H "accept: */*" 
-H "Content-Type: application/json" 
-d "{ \"email\": \"choiyuri9107@gmail.com\", \"password\": 1234}"
```
- 응답
```json
{
  "result": "SUCCESS",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ ... hI70vS03N6XF3Nn-hwDGw"
  }
}
```

### 4.3. 게시글 API
- 요청
```bash
curl -X GET "Board API URL" 
-H "accept: */*" 
-H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ ... hI70vS03N6XF3Nn-hwDGw"
```
- 응답(성공)
```json
{
  "result": "SUCCESS",
  "data": {  }
}
```
- 응답(실패)
```json
{
  "result": "FAIL",
  "error": "ERROR_CODE"
}
```

## 5. API 명세
### 5.1. Swagger URL : http://localhost:8080/swagger-ui.html
### 5.2. 우측 상단 Authorize "Bearer "+token 입력 후 게시글 API Try it out
