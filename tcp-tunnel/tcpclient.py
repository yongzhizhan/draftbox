#!/usr/bin/env python2.7
# -*- coding: utf-8 -*-

from tornado import ioloop, httpclient, gen
from tornado.gen import Task
import pdb, time, logging
import tornado.ioloop
import tornado.iostream
import socket

#Init logging
def init_logging():
    logger = logging.getLogger()
    logger.setLevel(logging.DEBUG)

    sh = logging.StreamHandler()

    formatter = logging.Formatter('%(asctime)s -%(module)s:%(filename)s-L%(lineno)d-%(levelname)s: %(message)s')
    sh.setFormatter(formatter)

    logger.addHandler(sh)
    logging.info("Current log level is : %s", logging.getLevelName(logger.getEffectiveLevel()))


class TCPClient(object):
    io_loop = None
    connect_stream = None

    def __init__(self, host, port, stream):
        self.host = host
        self.port = port

        self.shutdown = False
        self.stream = None
        self.sock_fd = None
        self.connect_stream = stream

        self.EOF = b' END'

        init_logging()

        self.io_loop = tornado.ioloop.IOLoop.instance()


    def get_stream(self):
        self.sock_fd = socket.socket(socket.AF_INET, socket.SOCK_STREAM, 0)
        self.stream = tornado.iostream.IOStream(self.sock_fd)
        self.stream.set_close_callback(self.on_close)

    def connect(self):
        self.get_stream()
        self.stream.connect((self.host, self.port), self.after_connect)

        # 客户端收到数据，让服务器代理发送
        self.stream.read_bytes(10240, self.connect_stream_write, None, True)

        # 服务器接收到数据，让客户端代理发送
        data = self.connect_stream.read_bytes(10240, self.stream_write, None, True)

        abc = 1

        #self.io_loop.start()

    def on_receive(self, data):
        logging.info("Received: %s", data)

        # 输出数据到连接方
        self.stream.write(data);

    def on_close(self):
        if self.shutdown:
            self.io_loop.stop()

    def connect_stream_write(self, data):
        self.connect_stream.write(data)
        self.stream.read_bytes(10240, self.connect_stream_write, None, True)

    def stream_write(self, data):
        self.stream.write(data)
        data = self.connect_stream.read_bytes(10240, self.stream_write, None, True)

    def after_connect(self):
        pass

    def set_shutdown(self):
        self.shutdown = True

def main():
    init_logging()

    io_loop = tornado.ioloop.IOLoop.instance()
    c1 = TCPClient("127.0.0.1", 8001, io_loop)
    c2 = TCPClient("127.0.0.1", 8001, io_loop)

    c1.connect()
    c2.connect()

    c2.set_shutdown()

    logging.info("**********************start ioloop******************")
    io_loop.start()

if __name__ == "__main__":
    try:
        main()
    except Exception, ex:
        print "Ocurred Exception: %s" % str(ex)
        quit()