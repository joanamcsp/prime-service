FROM openjdk:11-jre-slim

VOLUME /tmp

ADD target/proxy-server.jar proxy-server.jar

ENTRYPOINT ["java", "-jar", "/proxy-server.jar"]