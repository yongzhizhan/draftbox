#!/usr/bin/env bash

eval $(docker-machine env)
docker build --tag=123.56.168.19:5000/status-server .
docker push 123.56.168.19:5000/status-server