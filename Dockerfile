FROM maven:3.9.9-eclipse-temurin-21-alpine as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/synhubbackend-0.0.1-SNAPSHOT.jar synhubbackend.jar
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "synhubbackend.jar"]