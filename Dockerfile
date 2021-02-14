FROM maven:3.6.3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -U clean package -Dmaven.test.skip=true 

FROM azul/zulu-openjdk-alpine:8

COPY --from=build /home/app/target/backend-recruitment-0.0.1-SNAPSHOT.jar app.jar

EXPOSE $PORT