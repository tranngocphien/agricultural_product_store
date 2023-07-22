FROM openjdk:8-alpine

COPY target/*.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp

EXPOSE 10000

CMD ["java","-Dlog4j2.formatMsgNoLookups=true","-Xms256m", "-Xmx2048m", "-jar", "app.jar"]