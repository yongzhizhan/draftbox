#!/usr/bin/env python
# coding: utf-8


#'''
# LocalParam可以在查询语法中，加入一些元数据信息，增加查询功能，当然这些功能也可以通过增加参数的方式来实现，但使
# 用LocalParam的方式，会更加的方便。
#
# ## 基本语法：
# LocalParam的基本使用形式：{!参数}值。
#
# ## 简写
#
# ## 变量引用
#
#
# '''
import unittest

import pysolr


class LocalParam(unittest.TestCase):
    solr = None

    def setUp(self):
        # 动态字段定义
        # <dynamicField name="*_s" type="string" indexed="true" stored="true" multiValued="false"/>
        # <dynamicField name="*_i" type="int" indexed="true" stored="true" multiValued="false"/>

        self.solr = pysolr.Solr('http://10.10.17.117:8983/solr/test1', timeout=10)
        list = []
        for i in xrange(1000):
            list.append({
                "xid": "xid_{}".format(i),
                "foo_s": "foo_{}".format(i)
            })

        self.solr.add(list, commit=True)
        pass

    def testBaseSyntax(self):
        queryStr = "{!df='foo_s'}foo*"
        results = self.solr.search(queryStr)
        docs = results.docs
        self.assertNotEqual(len(docs), 0)

if __name__ == '__main__':
    unittest.main()
