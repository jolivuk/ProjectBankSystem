FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /app

# Копируем только необходимые файлы для Maven сначала
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Устанавливаем права и проверяем Maven wrapper
RUN chmod +x mvnw
RUN echo "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.5/apache-maven-3.9.5-bin.zip" > .mvn/wrapper/maven-wrapper.properties

# Копируем исходный код
COPY src ./src

RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-jammy

VOLUME /tmp
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/BankSystem
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1111

ENTRYPOINT ["java","-cp","app:app/lib/*","bank.app.BankApplication"]

# docker-compose up --build