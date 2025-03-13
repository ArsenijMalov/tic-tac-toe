# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the parent POM and module directories
COPY / ./

# Run the Maven build
RUN mvn clean package -DskipTests

# Stage 2: Package the application
FROM amazoncorretto:21

WORKDIR /app
COPY --from=build /app/target/game-engine-service-*-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
