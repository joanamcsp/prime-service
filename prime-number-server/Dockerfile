FROM openjdk:11-jre-slim

VOLUME /tmp

ADD target/prime-number-server.jar prime-number-server.jar

ENTRYPOINT ["java", "-jar", "/prime-number-server.jar"]