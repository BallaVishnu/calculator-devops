#stage 1 - build executable jar file
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

#download
RUN ./mvnw dependency:go-offline -B

#copy code
COPY src src

#build app
RUN ./mvnw clean package -DskipTests

#2nd stage - runtime containing only jre and jar
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --create-home --shell /usr/sbin/nologin appuser

COPY --from=build /app/target/*.jar app.jar
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
