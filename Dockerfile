FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY .env .env

COPY build/libs/*.jar pricetaglist.jar

EXPOSE 8080

CMD ["sh", "-c", "source .env && java -jar pricetaglist.jar"]