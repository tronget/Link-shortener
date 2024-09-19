FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew build
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]