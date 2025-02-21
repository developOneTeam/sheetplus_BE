# SCH Sheet+ 프로젝트

## 프로젝트 개요
- 교내 행사운영을 돕는 웹앱 서비스 개발
- 행사를 개최하거나 참여할 수 있는 플랫폼


## 백엔드 파트의 개발 목표
- 제출기한내로 요구기능 개발 완성
- 실사용자가 이용가능한 운영환경 구성
- 신뢰성을 갖춘 참여인증 프로세스 개발

## 전체 개발 기능
![백엔드개발기능](https://github.com/user-attachments/assets/673d4a15-b41a-49df-b8fe-40abf39017be)

## 주요 개발 기능
### 웹 푸시 알림 기능

![알림기능](https://github.com/user-attachments/assets/cc994050-cc6b-46bc-b094-16f2dc24dfa9)

#### 기능 개요
즐겨찾기 이벤트의 시작 시간을 알려주는 편의 기능

#### 기능 개발 목적
즐겨찾기한 이벤트의 시작시간을 인지시켜, 이벤트 참여율을 높이기 위한 목적


### 이메일 인증 시스템
![이메일인증](https://github.com/user-attachments/assets/ea1cafbe-df06-4c03-8805-58ba660f6a4f)

#### 기능 개요
QR코드로 이벤트 참여를 인증하는 시스템

#### 기능 개발 목적
- 기존 서명부 인증방식 절차 간소화 목적
- 참여자 관리 비용 절감 목적


#### 기능 개요
대학생/교직원 확인을 위한 이메일 기반 인증 시스템

#### 기능 개발 목적
- SCH 대학생/교직원인지 구분하기 위한 목적
- 별도의 비밀번호 관리부담을 줄이기 위한 목적

### QR코드 참여 인증 시스템
![qr코드인증로직](https://github.com/user-attachments/assets/3e083895-af4f-4bfc-893a-d22b63d08a6f)


## 백엔드 배포 아키텍처
![백엔드배포아키텍처](https://github.com/user-attachments/assets/7cd69c6f-5c5a-4af6-88c8-301eebe596eb)

## 백엔드 서버 아키텍처
![백엔드아키텍처](https://github.com/user-attachments/assets/b8d62fc5-744b-44f4-b165-30191fad7a4d)

## DB ERD
![erd추가](https://github.com/user-attachments/assets/16e109d6-99c8-4383-8dd3-b9b03a1c02b5)


## 협업 방법
![협업swagger](https://github.com/user-attachments/assets/1308fbd6-dcea-4493-b135-68172babe7ec)
- Swagger로 REST API 문서화


## 주요 문제 해결과정
- 프로젝트 문제 해결과정에 대해서는 개인 기술 블로그에 정리했습니다
    - [이메일 발송 서비스](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%95%BC%EA%B8%B0)
    - [웹 푸시알림 서비스](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%95%BC%EA%B8%B0-%EC%9B%B9-%ED%91%B8%EC%8B%9C%EC%95%8C%EB%A6%BC-%EC%84%9C%EB%B9%84%EC%8A%A4)
    - [REST API](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A0%95%EB%A6%AC-REST-API)
    - [레거시 코드 개선](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%95%BC%EA%B8%B0-%EB%A0%88%EA%B1%B0%EC%8B%9C-%EC%BD%94%EB%93%9C-%EA%B0%9C%EC%84%A0)
    - [단편 모음](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%95%BC%EA%B8%B0-%EB%8B%A8%ED%8E%B8%EB%AA%A8%EC%9D%8C)

## 기술 스택
| **Java 21** | **MySQL** | **Docker**                     | **Mailgun** |
| --- | --- |--------------------------------| ---|
| **Spring boot 3** | **Redis** | **AWS (EC2, RDS, CloudWatch)** | **Firebase Cloud Message** |
| **Spring Data JPA** | **Git** | **Github Actions**             |  |
| **QueyDSL** | **Swagger** |                                |  |
| **Spring Security** |  |                                |  |

## 개발 기간
- 2024.10. ~ 2024.11. 

## 팀 구성
- 백엔드 (1인), 프론트엔드 (1인)   |   2인팀 구성

| **백엔드** |
|:------------------------------------------------------------------------------:|
|<img src="https://github.com/user-attachments/assets/fbb50a3d-9b16-48d9-a202-5ceea62d16e0" width=130px alt="황제연">|
|[hwangjeyeon](https://github.com/hwangjeyeon)|
