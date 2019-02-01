FROM openjdk:8-jdk-slim
LABEL service=agordo
VOLUME /tmp
COPY build/libs/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]