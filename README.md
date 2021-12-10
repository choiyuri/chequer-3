# 게시판 API
## 소개
- 게시판에 필요한 REST API
- Spring Boot Version : 2.6.1
- JDK Version : 11
- Database : H2

## 구조
```

```

## 시작하기
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

## Swagger
- URL : http://localhost:8080/swagger-ui.html
1. [사용자 생성] 사용자 API - /api/v1/signup
2. [사용자 인증] 인증 API - /api/v1/auth
3. [게시글] 게시글  API > Try it out > Access Token = 인증 Response

