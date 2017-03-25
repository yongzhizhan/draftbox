#!/usr/bin/env python
# -*- coding: UTF-8 -*-


import unittest
import time


class BaseTest(unittest.TestCase):
    def testTimestamp(self):
        ticket = time.time()
        print("{}".format(ticket))
        pass


if __name__ == '__main__':
    unittest.main()
