FROM adoptopenjdk/openjdk11
MAINTAINER KIMMYUNGHO <kimzzan1234@gmail.com>
VOLUME /tmp
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} partnel.jar
ENTRYPOINT ["java", "-jar", "partnel.jar"]
EXPOSE 8081
