#!/usr/bin/env python
# coding: utf-8

from kafka import KafkaProducer
import time

kafka_host = "10.10.17.117"
kafka_port = 9092

server_str = '{kafka_host}:{kafka_port}'.format(kafka_host = kafka_host, kafka_port = kafka_port)
producer = KafkaProducer(bootstrap_servers = [server_str])

message_string = "[{}]This is a message".format(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
response = producer.send("test", message_string.encode("utf-8"))


