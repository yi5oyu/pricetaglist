# 가격표 - 쿠팡 가격 변동 추적

**[https://www.pricetaglist.com](https://www.pricetaglist.com)**

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [주요기능/구현](#주요기능구현)
- [후기/개선점](#후기개선점)

<hr>

### 프로젝트 개요


#### 프로젝트 소개

    쿠팡 가격 변동 추적하고 한눈에 확인하는 웹사이트

#### 개발 기간

    2024.03 ~ 
    2024.05.31 (배포)

#### 개발 환경

<img src="https://img.shields.io/badge/html5-E34F26?style=flat-square&logo=html5&logoColor=white"/><img src="https://img.shields.io/badge/css3-1572B6?style=flat-square&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/>
<img src="https://img.shields.io/badge/jquery-0769AD?style=flat-square&logo=jquery&logoColor=white"/>
<img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white">
<br>
<img src="https://img.shields.io/badge/java 17-007396?style=flat-square&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/Spring Boot 3-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white">
<br>
<img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/travis%20ci-3EAAAF?style=flat-square&logo=travis-ci&logoColor=white">
<img src="https://img.shields.io/badge/Linux-FCC624?style=flat-square&logo=Linux&logoColor=black"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/Tomcat-F8DC75?style=flat-square&logo=Apache Tomcat&logoColor=black"/>
<img src="https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=Nginx&logoColor=white">
<br>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS_EC2-232F3E?style=flat-square&logo=Amazon-AWS&logoColor=white">
<img src="https://img.shields.io/badge/AWS_RDS-232F3E?style=flat-square&logo=Amazon-AWS&logoColor=white">
<img src="https://img.shields.io/badge/CentOS%207-262577?style=flat-square&logo=CentOS&logoColor=white">

#### 주요 라이브러리
<img src="https://img.shields.io/badge/Jsoup-2370FF?style=flat-square&logo=Jsoup&logoColor=white"><img src="https://img.shields.io/badge/Swiper-6332F6?style=flat-square&logo=Swiper&logoColor=white">
<img src="https://img.shields.io/badge/Chart.js-FF6384?style=flat-square&logo=Chart.js&logoColor=white">
<img src="https://img.shields.io/badge/Spin.js-FF4500?style=flat-square&logo=Spin.js&logoColor=white">

<hr>

### 구조
<img width="881" alt="구조" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/b0407db4-f05e-4fdf-a914-70b1a76ec6c8">


### ERD
<img width="1201" alt="pricetag-ERD" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/417cdb4c-901b-4d4c-b7c9-6c29600b4261">

### 주요기능/구현

<details>
<summary>접기/펼치기</summary>
    
#### 메인화면
<img width="1275" alt="search" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/9fd560c7-ccf1-4501-8a95-9dcdf91db5fb">

#### 검색화면
<img width="1275" alt="search" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/2a3bc163-7b73-4e73-9511-616abf415aae">
</details>

<hr>

### 후기/개선점

- **CI/CD 파이프라인**
  \
  Travis CI, AWS S3, Codedeploy를 이용한 복잡한 배포 방식에서 비교적 간단한 Travis CI, Docker Hub, Docker Compose를 사용한 배포 방식으로 변경
  \
  AWS EC2 키페어(.pem) 파일을 base64 형식으로 인코딩하여 Travis CI 환경변수로 설정하여 스크립트를 이용한 배포
  \
  [배포 스크립트](https://github.com/yi5oyu/pricetaglist/wiki/%EB%B0%B0%ED%8F%AC-%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8)
  
- **로드밸런싱**
  \
  Nginx를 이용한 요청을 순서대로 처리(라운드 로빈)하는 로드밸런싱

  
- **쿼리 실행 시간**
  \
  데이터 검색속도 향상을 위한 인덱싱 / 페이징 구현
  \
  Spring AOP를 활용한 search 메소드 실행 시간 측정 (약 50번 확인)

   | 적용 | 최소시간 | 최대시간 |
   |---|:---:|---:|
   | - | 6600ms | 7200ms |
   | 인덱싱 | 5800ms | 7000ms |
   | 페이징 | 200ms | 400ms |
  
 > Service 영역에서 search 메소드의 주요 기능 : 쿼리 실행 / Entity -> DTO 데이터 변환  
 > 약 90000개의 데이터 중 3000개의 데이터를 조회  

- **AWS**
  \
  AWS 정책변경으로 인한 IPv4 주소로 VPC 구성 시 과금
  \
  RDS 서버 private으로 변경 예정 (쿠팡 파트너스 open API 사용 가능 시)

> 보안 그룹   
> 1   
> RDS 서버를 private으로 변경 시 로컬 환경에서 접근 불가   
> EC2 서버를 통해 RDS에 접근해야 함   
> 2   
> 보안 그룹 관리    
> EC2와 RDS에 공통적으로 사용되는 인바이드 규칙(SSH, HTTP, HTTPS, 특정포트 번호)을 포함한 보안 그룹   
> RDS의 MySQL 3306 포트에 내 IP 추가   


- **크롤링**
  \
  비동기 크롤링: 여러 사이트에서 동시 크롤링
  \
  스케쥴링: @Scheduled을 사용한 크롤링 스케쥴링 설정
  \
  Jsoup vs Selenium: 정적 콘텐츠를 빠르고 가볍게 사용하기 위해 Jsoup 선택

> Selenium  
> 웹 브라우저 자동화/테스트  
> 실제 브라우저를 열고 제어(클릭, 입력 등 요소 상호 작용)  
> JavaScript 및 AJAX 요청 처리 가능  

- **Git**
  \
  **Branch 분할**
  \
  master 브랜치만 사용하고 있어 모든 변경 사항에 Travis CI 크레딧 소진됨
  \
  CI 변경 예정(Github Action or Jenkins)
  \
  <br>
  **git 컨벤션**
  \
  코드 오류 발생 후 수정할 부분 찾기 힘들고 가독성 매우 좋지 않음
  \
  Git 컨벤션 적용 필요

- **Spring MVC**
  \
  계층간 명확한 역할 분리
  \
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<- &nbsp;&nbsp; DTO <-> Entity &nbsp;&nbsp; ->
  \
  &nbsp;&nbsp;&nbsp;&nbsp;Controller - Service - Repository
  \
  요청 처리 - 비즈니스 로직 - 데이터 접근

<!--

- **SEO**
  \

- **TDD**
  \
  
-->
