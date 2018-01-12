#!/usr/bin/env bash

cd `dirname ../../../../`

project="yueyuso-$(basename `pwd`)"

eval $(docker-machine env)
docker push 123.56.168.19:5000/$project