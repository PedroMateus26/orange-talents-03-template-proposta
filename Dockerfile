FROM openjdk:11.0.11-jdk
EXPOSE 8080
ARG JAR_FILE=target/Microservico-Propota-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
