#!/usr/bin/env python
# -*- coding: UTF-8 -*-


import unittest


class FooTestCase(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def testDefault(self):
        """hello doc"""
        print("test default")
        pass

    def testAssert(self):
        print("test assert")
        self.assertTrue(1 == 1)
        pass

    def runTest(self):
        pass
