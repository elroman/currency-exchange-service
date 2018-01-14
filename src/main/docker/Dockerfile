FROM openjdk:8-jdk-alpine
MAINTAINER elroman <elroman@elroman.com>

# Define mount points.
VOLUME /tmp
ADD currency-exchange-service-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dapp.port=${app.port}", "-jar","/app.jar"]

# Default http port
EXPOSE 8080
