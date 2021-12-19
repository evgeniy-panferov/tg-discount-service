FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tg-discount-service.jar
CMD ["java","-jar","/tg-discount-service.jar"]