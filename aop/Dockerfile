ARG BUILD_HOME=/home/gradle/app

FROM gradle:jdk17-alpine as cache
ARG BUILD_HOME
WORKDIR $BUILD_HOME
ENV GRADLE_USER_HOME /cache
COPY . ./
RUN gradle build --no-daemon --stacktrace

FROM gradle:jdk17-alpine as builder
ARG BUILD_HOME
WORKDIR $BUILD_HOME
COPY --from=cache /cache /home/gradle/.gradle
COPY . $BUILD_HOME/
RUN gradle --no-daemon clean build --stacktrace

FROM eclipse-temurin:17-jre-alpine
ARG BUILD_HOME
WORKDIR $BUILD_HOME
COPY --from=builder $BUILD_HOME/build/libs/*jar aop.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","aop.jar"]