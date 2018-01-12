#!/usr/bin/env bash

cd `dirname ../../../../`

project="yueyuso-$(basename `pwd`)"
archive="$(basename `pwd`)-1.0-SNAPSHOT"
eval $(docker-machine env)
echo ${project}
docker build --build-arg archive=${archive} --tag 123.56.168.19:5000/$project .