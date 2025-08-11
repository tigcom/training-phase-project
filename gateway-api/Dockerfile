# Stage 1: Use Maven for building
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="khang"
WORKDIR /app
COPY ./gateway-api/ ./gateway
WORKDIR /app/gateway
RUN mvn clean package

# Stage 2: Use OpenJDK for running
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/gateway/target/*.jar app.jar
EXPOSE 5110
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build command: docker build -t gateway_image -f api-gateway/Dockerfile .
# Run command: docker run --name gateway_container -p 8080:5110 gateway_image