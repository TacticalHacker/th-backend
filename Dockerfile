FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy everything and build
COPY . .
RUN ./mvnw clean package -DskipTests

# ---- Runtime image ----
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Run the Spring Boot app
CMD ["java", "-jar", "app.jar"]
