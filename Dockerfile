FROM maven:3.9.9-eclipse-temurin:23-jdk AS build

COPY . /app

WORKDIR /app

RUN mvn clean package -DskipTests

FROM openjdk:23-jdk

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
