FROM openjdk:8-jdk-alpine

LABEL maintainer="udhayapps@gmail.com"

ARG PORT=$ZUUL_SERVER_PORT

ENV JAVA_OPTS $JAVA_OPTS
ENV PORT $PORT

EXPOSE $PORT

COPY zuul-server.jar /

CMD java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -Deureka.client.service-url.defaultZone=$EUREKA_SERVER_URI \
    -Dspring.cloud.config.uri=$CONFIG_SERVER_URI \
    -Dspring.profiles.active=$PROFILE \
    -jar /zuul-server.jar \
    --server.port=$PORT
