FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add curl

LABEL maintainer="udhayapps@gmail.com"

ARG PORT=$EUREKA_SERVER_PORT

ENV JAVA_OPTS $JAVA_OPTS
ENV PORT $PORT

EXPOSE $PORT

COPY eureka-server.jar /

CMD java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /eureka-server.jar \
    --server.port=$PORT
