FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY mvnw .
COPY pom.xml .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080 SPRING_DATA_MONGO_URI=NOT_SET SPRING_REDIS_HOST=NOT_SET SPRING_REDIS_PORT=NOT_SET 
ENV SPRING_REDIS_USERNAME=NOT_SET SPRING_REDIS_PASSWORD=NOT_SET APIKEY=NOT_SET

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar