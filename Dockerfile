# OpenJDK 17을 기반으로 하는 알파인 리눅스 이미지를 사용
FROM openjdk:17-jdk-alpine

# 작업 디렉토리를 설정
WORKDIR /app

# 애플리케이션 JAR 파일을 이미지에 복사
COPY build/libs/*.jar app.jar

# 컨테이너가 시작될 때 실행할 명령어 설정
ENTRYPOINT ["java","-jar","/app/app.jar"]

# 컨테이너 외부에서 접근할 포트를 노출
EXPOSE 8888
