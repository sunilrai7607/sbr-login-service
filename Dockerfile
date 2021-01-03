FROM openjdk:8-jdk-alpine
MAINTAINER Sunil Rai <sunilrai7607@gmail.com>
VOLUME /app
ARG version
ENV version_number=$version
COPY ./build/libs/sbr-login-service-$version_number.jar sbr-login-service.jar
ENTRYPOINT ["java", "-jar","/sbr-login-service.jar"]