#!/usr/bin/env python
# -*- coding: UTF-8 -*-


import unittest
from python.unittest.base_unittest import FooTestCase

fooTestSuite = unittest.TestSuite()
fooTestSuite.addTest(FooTestCase("testDefault"))


runner = unittest.TextTestRunner()
runner.run(fooTestSuite)
