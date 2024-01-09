FROM openjdk:21
VOLUME /tmp
COPY target/sample-service-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
