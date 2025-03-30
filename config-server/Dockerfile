FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-XX:-UseContainerSupport", "-jar", "app.jar"]