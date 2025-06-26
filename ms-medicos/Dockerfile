FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/ms-medicos-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9092
RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["java", "-jar", "app.jar"]