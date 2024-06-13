# 가격표 - 쿠팡 가격 변동 추적

**[가격표 바로가기](https://www.pricetaglist.com)**

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [주요기능/구현](#주요기능구현)
- [후기/개선점](#후기개선점)

### 프로젝트 개요


#### 프로젝트 소개

    쿠팡 가격 변동 추적하고 한눈에 확인하는 웹사이트 제작

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

#### 구조
<img width="881" alt="구조" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/b0407db4-f05e-4fdf-a914-70b1a76ec6c8">

#### ERD
<img width="1201" alt="pricetag-ERD" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/417cdb4c-901b-4d4c-b7c9-6c29600b4261">

### 주요기능/구현
##### [메인화면]
![스크린샷_6-6-2024_02554_pricetaglist com](https://github.com/yi5oyu/pricetaglist/assets/111046436/9fd560c7-ccf1-4501-8a95-9dcdf91db5fb)
##### [검색화면]
<img width="1275" alt="search" src="https://github.com/yi5oyu/pricetaglist/assets/111046436/2a3bc163-7b73-4e73-9511-616abf415aae">

### 후기/개선점

- CI/CD 파이프라인
  \
  Travis CI, AWS S3, Codedeploy를 이용한 복잡한 배포 방식에서 비교적 간단한 Travis CI, Docker Hub 배포 방식 선택


- 로드밸런싱
  \
  Nginx를 이용한 요청을 순서대로 처리하는 로드밸런싱


- 배포
  \
  
