FROM openjdk:21
ARG JAR_VERSION=1
COPY target/sample-service-${JAR_VERSION}.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]