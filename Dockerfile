FROM amazoncorretto:11-alpine-jdk
COPY /target/beta.jar beta.jar
ENTRYPOINT ["java","-jar","/beta.jar"]