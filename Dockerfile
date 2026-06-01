FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

COPY src ./src

RUN ./gradlew --no-daemon bootJar

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
