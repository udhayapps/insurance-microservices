FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add curl

LABEL maintainer="udhayapps@gmail.com"

ARG PORT=$CONFIG_SERVER_PORT

ENV JAVA_OPTS $JAVA_OPTS
ENV PORT $PORT

EXPOSE $PORT

COPY config-server.jar /

CMD java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -Deureka.client.service-url.defaultZone=$EUREKA_SERVER_URI \
    -jar /config-server.jar \
    --server.port=$PORT
