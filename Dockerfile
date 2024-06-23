FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t springio/gs-spring-boot-docker .
#docker run -p 8081:8080 springio/gs-spring-boot-docker