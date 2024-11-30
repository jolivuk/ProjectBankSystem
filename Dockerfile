FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline -B
#скачать все зависимости

COPY src ./src

RUN ./mvnw clean package -DskipTests
#собрать все приложение


# 2 stage
#FROM openjdk:17-jre-slim не находит причина не понятна
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
# app.jar мы переименовали наш demo 0.01-snapshot.jar

ENV DB_URL=jdbc:mysql://localhost:3306/BankSystem
ENV DB_USERNAME=root
ENV DB_PASSWORD=1111

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

# $ docker build -t bankapp .       cборка
# docker run -d -p 8080:8080 bankapp     запуск