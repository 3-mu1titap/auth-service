
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/auth-0.0.1-SNAPSHOT.jar auth-service.jar

EXPOSE 9100

ENTRYPOINT ["java", "-jar", "auth-service.jar"]

ENV TZ=Asia/Seoul