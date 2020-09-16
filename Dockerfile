#FROM adoptopenjdk/maven-openjdk11:latest AS MAVEN_BUILD
#MAINTAINER Oleksandr Turchanovskyi
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#RUN mvn package
FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp
#WORKDIR /app
#COPY --from=MAVEN_BUILD /build/target/Turchanovskyi-0.0.1-SNAPSHOT.jar /app/
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
