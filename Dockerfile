# STAGE 1 - Compilación de la aplicación
FROM gradle:jdk21 as builder

WORKDIR /app

COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./src ./src

# Generar el jar ejecutable de Spring Boot
RUN gradle clean bootJar -x test --no-daemon

# STAGE 2 - Ejecución de la aplicación
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar discografia-1.jar

EXPOSE 8080

CMD ["java", "-jar", "discografia-1.jar"]