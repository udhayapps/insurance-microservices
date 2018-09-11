FROM golang:alpine

RUN apk add --update go git

ADD . /go/src/app

RUN apk add --update go git
RUN cd /go/src/app/ && go get -v
RUN cd /go/src/app/ && go run ./main.go
