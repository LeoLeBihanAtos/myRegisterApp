FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
