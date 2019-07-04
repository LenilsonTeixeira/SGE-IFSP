FROM openjdk:8-jre-alpine

LABEL maintainer="Lenilson Teixeira <lenilson.teixeira@sensedia.com>"

ADD target/ms-docentes-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]