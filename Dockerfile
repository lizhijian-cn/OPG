# gradle 好大
FROM gradle:jdk14
WORKDIR /app
COPY build.gradle gradle settings.gradle /app/
COPY src /app/src
RUN gradle build --no-daemon