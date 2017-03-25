#!/usr/bin/env python
#conding: utf-8

import SocketServer
from threading import Timer
from time import sleep


class MyTCPHandler(SocketServer.BaseRequestHandler):
    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """

    def handle(self):
        #Timer(0, self._handle, args = [self.request]).start()
        self._handle(self.request)

    def _handle(self, request):
        #sleep(3)

        # self.request is the TCP socket connected to the client
        data = request.recv(1024).strip()
        print "{} wrote:".format(self.client_address[0])
        print data

        print "wait ...."


        try:
            # just send back the same data, but upper-cased
            request.sendall(data.upper())
        except Exception as e:
            print e

        print "send complete"

if __name__ == "__main__":
    HOST, PORT = "localhost", 9999

    # Create the server, binding to localhost on port 9999
    server = SocketServer.TCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()