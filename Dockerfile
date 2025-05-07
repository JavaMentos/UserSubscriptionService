FROM maven:3.8.4-openjdk-17 AS build
VOLUME /tmp
COPY target/UserSubscriptionService-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]