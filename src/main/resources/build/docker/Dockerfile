FROM java:openjdk-8-jdk
MAINTAINER blujay <elroman@elroman.com>

# Define mount points.
VOLUME ["/my_proj/logs", "/my_proj/conf", "/my_proj/apps"]

# Define working directory.
WORKDIR /opt/mule

COPY target/my-proj.zip /my_proj/apps/
COPY application.properties /my_proj/conf/

CMD [ "my_proj/..." ]

# Default http port
EXPOSE 8080