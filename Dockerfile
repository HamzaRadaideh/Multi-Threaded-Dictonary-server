FROM gradle:8.7.0-jdk21-alpine AS build

COPY --chown=gradle:gradle . /project
WORKDIR /project

RUN gradle build --no-daemon 

FROM openjdk:23-slim-bullseye

COPY --from=build /project/app/build/libs/*.jar /app.jar

EXPOSE 8888
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
