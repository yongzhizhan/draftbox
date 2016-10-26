FROM 10.10.8.166:5000/centos:6

RUN yum -y update
RUN yum -y install unzip

RUN cd /opt
ADD entry.sh /opt/entry.sh
RUN chmod +x /opt/entry.sh

ADD EasyDarwin-CentOS-x64-7.0.5-Build16.0518.zip /opt/EasyDarwin-CentOS-x64-7.0.5-Build16.0518.zip
RUN unzip /opt/EasyDarwin-CentOS-x64-7.0.5-Build16.0518.zip -d /opt/
RUN mv /opt/EasyDarwin-CentOS-x64-7.0.5-Build16.0518 /opt/easy_darwin
RUN rm -f /opt/EasyDarwin-CentOS-x64-7.0.5-Build16.0518.zip
RUN chmod +x /opt/easy_darwin/easydarwin

WORKDIR /opt/easy_darwin
EXPOSE 80 8080 554 554/udp
ENTRYPOINT ["/opt/entry.sh"]