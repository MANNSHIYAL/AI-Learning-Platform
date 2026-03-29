# STAGE 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 1. Copy the "infrastructure" files needed for any build
# COPY pom.xml .
# COPY mvnw .
# COPY .mvn .mvn
# COPY contracts/ ./contracts/
COPY . .

# 2. Accept the service name as a variable (e.g., auth-service)
ARG SERVICE_NAME

# 3. Copy only the code for the specific service being built
# COPY ${SERVICE_NAME}/ ./${SERVICE_NAME}/

# 4. Build only that service (and its local dependencies like contracts)
RUN mvn clean package -pl ${SERVICE_NAME} -am -DskipTests

# STAGE 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Re-declare ARG because it doesn't carry over from the build stage
ARG SERVICE_NAME

# 5. Copy the JAR from the specific service's target folder
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar

# Production-ready JVM settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
