FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .

# 🔥 Make mvnw executable
RUN chmod +x mvnw

# 👇 Build your app
RUN ./mvnw clean package -DskipTests

# ---- Runtime image ----
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
