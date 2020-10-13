# Login-Base-Token [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)[![Build Status](https://travis-ci.org/ber01/Login-Base-Token.svg?branch=master)](https://travis-ci.org/ber01/Login-Base-Token)[![Coverage Status](https://coveralls.io/repos/github/ber01/Login-Base-Token/badge.svg)](https://coveralls.io/github/ber01/Login-Base-Token)
자주 사용되는 로그인, 회원가입 기능에 대한 베이스 코드를 작성한 뒤 향후 프로젝트에 적용하기 위해 만든 저장소입니다.

## Index

- [Key Summary](#key-summary)
- [Folder structure](#folder-structure)
- [API Info](#apI-Info)
- [DataBase SCHEMA](#database-schema)
- [Run](#run)
- [Dev](#dev)

## Key Summary

작성중 ..

## Folder structure

#### main

```
.
└── me
    └── kyunghwan
        └── jwt
            ├── JwtApplication.java
            ├── LoginController.java
            ├── account
            │   ├── Account.java
            │   ├── AccountAdapter.java
            │   ├── AccountController.java
            │   ├── AccountRepository.java
            │   ├── AccountService.java
            │   └── dto
            │       └── AccountDTO.java
            ├── config
            │   ├── AppConfig.java
            │   └── SecurityConfig.java
            └── jwt
                ├── JwtTokenFilter.java
                └── JwtTokenProvider.java
```

#### test

```
.
└── me
    └── kyunghwan
        └── jwt
            ├── BaseTest.java
            ├── JwtApplicationTests.java
            ├── LoginControllerTest.java
            ├── account
            │   ├── AccountAdapterTest.java
            │   ├── AccountControllerTest.java
            │   ├── AccountTest.java
            │   └── dto
            │       └── AccountDTOTest.java
            └── jwt
                └── JwtTokenProviderTest.java
```

## API Info

작성중 ..

## DataBase SCHEMA

개발환경에서는 `H2` 를 운영환경에서는 `MySQL` 를 사용하였습니다. 이에 따른 설정파일 세팅 방법은 아래를 참고하세요.

### Account

|   필드   |     타입     | NULL |   KEY   |
| :------: | :----------: | :--: | :-----: |
|   IDX    |  bigint(20)  |  NO  | Primary |
|  EMAIL   | varchar(255) |  NO  | Unique  |
|   NAME   | varchar(255) | YES  |    -    |
| PASSWORD | varchar(255) |  NO  |    -    |
| PICTURE  | varchar(255) | YES  |    -    |

## Run

1. `/src/main/resources/application-oauth.yml` 을 아래와 같이 작성

   ```
   spring:
     datasource:
       url: jdbc:mysql://127.0.0.1:3306/데이터베이스_이름?serverTimezone=Asia/Seoul
       username: 유저
       password: 비밀번호
       driver-class-name: com.mysql.cj.jdbc.Driver
     jpa:
       hibernate:
         ddl-auto: create
       show-sql: true
       generate-ddl: true
   
   server:
     port: ${PORT:8080}
     error:
       whitelabel:
         enabled: false
   ```

2. **Gradle**

   ```
   $ git clone https://github.com/ber01/Spring-Security-Login-Skeleton.git
   $ cd Spring-Security-Login-Skeleton
   $ ./gradlew test
   $ ./gradlew build
   $ ./gradlew bootrun
   ```

## Dev

|     도구     |              버전               |
| :----------: | :-----------------------------: |
|    Spring    |    Spring Boot 2.3.4.RELEASE    |
|      OS      |            Mac OS X             |
|   개발 툴    | Intellij IDEA Ultimate 2020. 01 |
|     JDK      |              JDK 8(>=8)              |
| 데이터베이스 |               H2, MySQL                |
|   빌드 툴    |          gradle-6.6.1           |

## With

- [양기석](https://github.com/yks095), [임동훈](https://github.com/donghL-dev), [하상엽](https://github.com/ssayebee)