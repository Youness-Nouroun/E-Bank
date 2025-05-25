# Stage 1: Build the application
FROM maven:3.9.1-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Config BDD (MySQL instead of PostgreSQL)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://ebanking-mysql:3306/ebanking_db?createDatabaseIfNotExist=true
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=

# Secret JWT
ENV JWT_SECRET=8970B93C8970B93C8970B93C8970B93C8970B93C8970B93C8970B93C8970B93C

# Server Port (matches application.properties)
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]
