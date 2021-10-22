FROM openjdk:8-jdk-alpine
RUN mvn clean package spring-boot:repackage
ENTRYPOINT ["mvn", ]
EXPOSE 8080