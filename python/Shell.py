#!/usr/bin/env python
# coding:utf-8

import subprocess


class Shell:
    def __init__(self, cmd):
        self.cmd = cmd
        self.output = None
        self.ret_code = None

    def run_background(self):
        self.output = None
        self.ret_code = None

        self.__process = subprocess.Popen(self.cmd, stdin=subprocess.PIPE, stdout=subprocess.PIPE)

    def wait_for_terminate(self, timeoutS):
        self.__process.wait(timeout=timeoutS)

    def get_output(self):
        if not self.is_terminate():
            return ""

        if self.output is None:
            self.output = ""
            while True:
                line = self.__process.stdout.readline()
                if "" == line:
                    break

                self.output += line

        return self.output

    def terminate(self):
        self.__process.terminate()

    def is_terminate(self):
        return not self.get_ret_code() is None

    def get_ret_code(self):
        if self.ret_code is None:
            self.ret_code = self.__process.poll()

        return self.ret_code
