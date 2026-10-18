FROM amazoncorretto:17
LABEL authors="MSR"
# Set the working directory inside the container
#EXPOSE 8080
WORKDIR /app

# Copy your application's JAR file into the container
COPY ./target/product-gateway.jar /app

# Define the command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "product-gateway.jar"]