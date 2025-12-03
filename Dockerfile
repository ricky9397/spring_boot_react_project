# 빌드 스테이지
FROM adoptopenjdk/openjdk11 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 실행 스테이지
FROM adoptopenjdk/openjdk11
MAINTAINER KIMMYUNGHO <kimzzan1234@gmail.com>
VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar ricky.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul","ricky.jar"]
EXPOSE 8080
