FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY .env .

COPY build/libs/*.jar pricetaglist.jar

EXPOSE 8080

CMD ["sh", "-c", "source .env && exec java -jar pricetaglist.jar"]