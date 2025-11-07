# Dockerfile para Spring Boot en Render - Versión 2025 con imágenes estables
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml mvnw ./
COPY .mvn ./.mvn

# Make mvnw executable
RUN chmod +x mvnw

# Copy source code
COPY src ./src

# Build the app
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR
COPY --from=build /app/target/comiteolimpicoapi-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]