#!/bin/bash

cd `dirname $0`

sed -i "s#^spring.datasource.username.*#spring.datasource.username=$DB_USER#g" application.properties
sed -i "s#^spring.datasource.password.*#spring.datasource.password=$DB_PWD#g" application.properties
sed -i "s#^spring.datasource.url.*#spring.datasource.url=$DB_CONN#g"  application.properties

set JAVA_OPTS="-Xms128m -Xmx256m"
java ${JAVA_OPTS} -cp .:./*:lib/* com.zhidiandata.yueyuso.webserver.MainServer