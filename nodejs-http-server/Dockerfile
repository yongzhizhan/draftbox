FROM node

RUN mkdir /opt/status-server
RUN mkdir /opt/status-server/db

WORKDIR /opt/status-server/

COPY ./package.json /opt/status-server/
RUN npm --registry=http://registry.npm.taobao.org i

#RUN apt-get update
#RUN apt-get -y install vim

COPY ./* /opt/status-server/

ENTRYPOINT "node /opt/status-server/index.js"