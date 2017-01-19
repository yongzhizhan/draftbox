#!/usr/bin/env python
# coding:utf-8

import unittest
import time


class GPSTest(unittest.TestCase):
    def testDefault(self):
        for i in range(3):
            time.sleep(1)

if __name__ == '__main__':
    unittest.main()
