# ---------- Build Stage ----------
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Maven wrapper & config
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml
COPY pom.xml .

# Download dependencies (cache optimization)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build jar
RUN ./mvnw clean package -DskipTests


# ---------- Run Stage ----------
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
