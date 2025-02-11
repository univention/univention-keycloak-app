# Use an official Maven image with JDK 17
FROM maven:3.9-eclipse-temurin-17

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the tests
CMD ["mvn", "test"]
