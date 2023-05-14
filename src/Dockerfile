FROM docker.io/alpine:3
LABEL maintainer="Jonas Cahenzli <jonas.cahenzli@gmail.com>" \
      description="Internal Docker image for PlantUML"

ENV PLANTUML_VERSION 1.2022.2

RUN apk update
RUN apk add --no-cache graphviz openjdk8-jre curl ttf-droid
RUN mkdir /app
RUN curl -L https://sourceforge.net/projects/plantuml/files/plantuml.${PLANTUML_VERSION}.jar/download -o /app/plantuml.jar
RUN apk del curl

ENTRYPOINT [ "java", "-jar", "/app/plantuml.jar" ]
