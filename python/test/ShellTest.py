#!/usr/bin/env python
# coding:utf-8

import unittest
import time

from Shell import Shell


class ShellTest(unittest.TestCase):
    def testDefault(self):
        test = Shell(cmd="echo 123")
        test.run_background()

        for i in range(3):
            time.sleep(1)

        self.assertTrue(test.is_terminate())

        output = test.get_output()
        self.assertTrue(output.startswith("123"))

    def testTerminate(self):
        test = Shell(cmd="calc")
        test.run_background()

        for i in range(2):
            time.sleep(1)

        test.terminate()
        time.sleep(1)

        self.assertTrue(test.is_terminate())

if __name__ == '__main__':
    unittest.main()