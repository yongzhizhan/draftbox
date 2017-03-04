#!/usr/bin/env python
# coding: utf-8

import time

"""
author: yongzhi.zhan

在每一个类里面，都默认包含了变量__dict__，所用动态的属性，都会存储在__dict__中，但有时需要限制属性的
访问，可以通过引入__slots__，解决这样个问题。

声明了__slots__的类，将不再包含变量__dict__，也不能动态设置属性，适用于一些结构体的声明。

在Foo类中，foo.__dict__存储了动态变量x，而在Bar类中，通过slots声明了属性，对于Bar类的实例，不能在动
态新增变量，也不存在__dict__变量。

"""

class Foo(object):
    test = "foo"
    def __init__(self):
        pass

foo = Foo()
foo.x = "foo"
print(foo.__dict__)

class Bar(object):
    __slots__ = ("test")

    def __init__(self):
        pass

bar = Bar()
bar.test = "bar"

#bar.x = "bar" invalid access

print(bar.test)

try:
    print(bar.__dict__)     # not contain __dict__variable
except Exception as e:
    print("bar.__dict__ can not access")

