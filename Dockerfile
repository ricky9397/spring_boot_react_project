FROM adoptopenjdk/openjdk11
MAINTAINER KIMMYUNGHO <kimzzan1234@gmail.com>
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} ricky.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul","ricky.jar"]
EXPOSE 8080
