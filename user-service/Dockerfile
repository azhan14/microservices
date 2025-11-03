# Use an official OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/UserService-0.0.1-SNAPSHOT.jar user-service.jar

# Expose the app port
EXPOSE 8081

# Command to run the app
ENTRYPOINT ["java", "-jar", "user-service.jar"]
