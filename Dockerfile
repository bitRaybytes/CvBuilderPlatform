# -------- BUILD STAGE --------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /cv-builderplatform

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# -------- RUNTIME STAGE --------
FROM eclipse-temurin:21-jdk
WORKDIR /cv-builderplatform

COPY --from=build /cv-builderplatform/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]