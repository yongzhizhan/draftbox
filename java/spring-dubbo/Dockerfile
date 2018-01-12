FROM cubesky/ssh-with-java

RUN apt update
RUN apt-get -y install unzip

ARG archive
COPY target/${archive}.zip /opt/${archive}.zip

WORKDIR /opt/
RUN unzip ${archive}.zip

WORKDIR /opt/${archive}/
RUN chmod +x startup.sh

EXPOSE 8080
ENTRYPOINT /opt/${archive}/startup.sh
