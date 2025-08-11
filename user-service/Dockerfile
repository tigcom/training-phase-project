# Stage 1: Use Maven for building common library
FROM maven:3.9.9-sapmachine-17 AS common-lib-build
WORKDIR /app
COPY ./common-library/ ./common-library
WORKDIR /app/common-library
RUN mvn clean package


# Stage 2: Use Maven for building authenticate service
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="khang"
WORKDIR /app
COPY ./user-service/ ./user
WORKDIR /app/user
COPY --from=common-lib-build /app/common-library/target/common-library-1.0-SNAPSHOT.jar ./lib/
RUN mvn install:install-file -Dfile=./lib/common-library-1.0-SNAPSHOT.jar \
    -DgroupId=org.example \
    -DartifactId=common-library \
    -Dversion=1.0-SNAPSHOT \
    -Dpackaging=jar
RUN mvn clean package -DskipTests

# Stage 3: Use OpenJDK for running
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/user/target/*.jar app.jar
EXPOSE 5119
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build command: docker build -t auth_image -f auth-service/Dockerfile .
# Run command: docker run --name auth_container -p 8081:5119 auth_image