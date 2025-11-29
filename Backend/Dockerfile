# --- build stage ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

# --- runtime stage ---
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app

# имя артефакта подставь своё, если отличается
COPY --from=build /app/target/*.jar app.jar

# debug-порт 5005 (для IntelliJ)
EXPOSE 8080 5005

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/app/app.jar"]
