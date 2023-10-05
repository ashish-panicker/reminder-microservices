# This is a multi stage build

# Stage 1: Build the application
FROM maven as build
# This is the working directory inside the container
WORKDIR /app
# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src
# Build the application and skip the tests
RUN mvn clean package -DskipTests

# Stage 2: Extract the application
FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR extracted
# Copy the application jar file from the build stage
COPY --from=build /app/target/*.jar app.jar
# Extract the application jar file
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 3: Run the application
FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR application
# Copy the extracted application from the builder stage
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8761
# Run the application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
# Spring framework JarLauncher is used to launch the application
# https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-executable-jar-format.html#executable-jar-jarlauncher
