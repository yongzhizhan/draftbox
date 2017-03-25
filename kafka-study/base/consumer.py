#!/usr/bin/env python
#coding: utf-8


from kafka import KafkaConsumer

kafka_host = "10.10.17.117"
kafka_port = 9092

server_str = '{kafka_host}:{kafka_port}'.format(kafka_host = kafka_host, kafka_port = kafka_port)

consumer = KafkaConsumer("test", group_id = "test-group", bootstrap_servers = [server_str])

for message in consumer:
    print(message.value)