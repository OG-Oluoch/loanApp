# Step 1: Use JDK 21 as the base image
FROM openjdk:21-slim

# Step 2: Set the working directory
WORKDIR /loanApp

# Step 3: Copy the JAR file
COPY target/loanApp-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the application's port
EXPOSE 8080

ENV PORT 8080

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
