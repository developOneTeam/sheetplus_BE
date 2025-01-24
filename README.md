# 행사 출석체크 애플리케이션
순천향대학교 주관 행사 출석체크를 참여/관리할 수 있는 웹애플리케이션입니다


## 백엔드 파트의 개발 목표
- 순천향대학교 학생/교직원만 서비스를 이용할 수 있도록, 학교 이메일 기반 인증시스템 개발
- 가입 유저를 관리자와 학생으로 등급을 나눠야하며, 등급에 따른 인가 시스템 개발
- QR코드 인증 로직 기능 개발과 유지보수
- 이벤트 활성화를 위한 이벤트 푸시알림 시스템 개발 
- 보안과 안정적인 데이터 관리 환경 구성

## 백엔드 배포 아키텍처
![백엔드배포아키텍처](https://github.com/user-attachments/assets/7cd69c6f-5c5a-4af6-88c8-301eebe596eb)

## 백엔드 서버 아키텍처
![백엔드아키텍처](https://github.com/user-attachments/assets/8d3fe22f-0357-478c-8181-4130e73adeed)

## DB ERD
![erd추가](https://github.com/user-attachments/assets/16e109d6-99c8-4383-8dd3-b9b03a1c02b5)

## 주요기능
### 요약
![백엔드주요기능](https://github.com/user-attachments/assets/ee88091c-8743-4a62-8848-5e3ec5e0048d)

### 시퀀스 다이어그램
백엔드 서버 주요 기능들의 시퀀스 다이어그램입니다.
#### E-mail 인증
![이메일검증](https://github.com/user-attachments/assets/6c4e4990-cb05-4ee2-a7dc-85254762d42f)

#### 회원가입
![회원가입 로직](https://github.com/user-attachments/assets/012a711d-a3fd-4266-bdb1-198092df2192)

#### 로그인
![로그인 로직](https://github.com/user-attachments/assets/80d4debf-9fb0-44f7-963e-e937baf9f508)

#### 리프래시 토큰 갱신
![토큰갱신과정](https://github.com/user-attachments/assets/dac2bceb-add8-46c7-8d0a-173e477ea671)

## 협업 방법
![협업swagger](https://github.com/user-attachments/assets/1308fbd6-dcea-4493-b135-68172babe7ec)
- Swagger로 REST API 문서화


## 주요 문제 해결과정
- 프로젝트 문제 해결과정에 대해서는 개인 기술 블로그에 정리했습니다
    - [개인 기술블로그](https://velog.io/@hwangjeyeon/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%95%BC%EA%B8%B0)


## 기술 스택
| **Java 21** | **MySQL** | **Docker** | **Mailgun** |
| --- | --- | --- | ---|
| **Spring boot 3** | **Redis** | **AWS (EC2, RDS)** | **Firebase Cloud Message** |
| **Spring Data JPA** | **Git** | **Jenkins** | **Apache JMeter** |
| **QueyDSL** | **Swagger** |  |  |
| **Spring Security** |  |  |  |

## 프로젝트 기간
- 2024.10. ~ 2024.11.

## 팀 구성
- 백엔드 (1인), 프론트엔드 (1인)   |   2인팀 구성

| **백엔드** |
|:------------------------------------------------------------------------------:|
|<img src="https://github.com/user-attachments/assets/fbb50a3d-9b16-48d9-a202-5ceea62d16e0" width=130px alt="황제연">|
|[hwangjeyeon](https://github.com/hwangjeyeon)|
