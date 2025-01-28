FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/employee-api.jar employee-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "employee-api.jar"]