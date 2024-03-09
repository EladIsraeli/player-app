# Use the official Maven image for a build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the project files to the container
COPY src ./src
COPY pom.xml .

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK for the application image
FROM openjdk:17-slim
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/large_sample.csv src/main/resources/large_sample.csv

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
