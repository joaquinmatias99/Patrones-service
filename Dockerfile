FROM openjdk:17-jdk-slim
ARG JAR_FILE=patrones-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_patrones.jar
ENTRYPOINT ["java", "-jar", "app_patrones.jar"]
