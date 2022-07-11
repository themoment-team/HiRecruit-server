# docker start with a base image containing java runtime
FROM openjdk:11

# Add Author information
LABEL repository="https://github.com/themoment-team/HiRecruit-server"
LABEL maintainer="@jyeonjyan, @siwony"

# Add a volume to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/hirecruit-1.0.jar
ADD ${JAR_FILE} hirecruit-1.0.jar
RUN java -Djarmode=layertools -jar hirecruit-1.0.jar extract

COPY build/libs/dependencies/ ./
COPY build/libs/spring-boot-loader ./
COPY build/libs/snapshot-dependencies ./
COPY build/libs/application ./

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hirecruit-1.0.jar","--spring.profiles.active=prod","--redis.host=10.0.4.66"]
