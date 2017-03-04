#!/usr/bin/env python
# coding: utf-8

import unittest
import time
import random

'''
author: yongzhi.zhan

跳跃表，在链表的基础上，通过概率的方式，解决链表的查询复杂度问题，其实现思路：
1.将链表划分为多层
2.下一层包含了上一层所有的数据
3.最底层包含了所有的数据
4.除了最底层，每层都随机包含下一层的部分数据
5.每层数据都是排序好的
6.除了最底层，每层都有指向下一层的指针
7.查找时，在最上层开始，当发现不在范围内，则转向下一层查找，直到最底层

这样做，可以降低了查询的复杂度，对插入的性能也没大影响，大大提高链表的性能

时间复杂度计算：


空间复杂度计算：


'''


class SkipList:
    class ListNode:
        __slots__ = ("next")

    class SkipListNode:
        __slots__ = ("nodes", "val")

    header = None
    level = None

    def __init__(self, level):
        self.level = level

    def insert(self, val):
        if not self.header:
            self.header = self.SkipListNode()
            self.header.val = val
            self.header.nodes = []

            for i in range(random.randint(0, self.level) + 1):
                node = self.ListNode()
                node.next = None
                self.header.nodes.append(node)

            return

        curr_level = self.level
        curr = self.header
        find_node = None

        while curr is not None or curr_level != 0:
            if curr is None:
                curr = curr.nodes[curr_level - 1]
                continue

            next = curr.nodes[curr_level].next
            if next is not None:
                next_val = next.val
                if next_val >= val:
                    find_node = curr
                    break



            curr = next

        pass

    def delete(self, pos):
        pass

    def get(self, pos):
        pass

    def exists(self, val):
        pass


class SkipListTest(unittest.TestCase):
    def test_insert(self):
        skip_list = SkipList(3)
        skip_list.insert(123)
        pass

    def test_delete(self):
        skip_list = SkipList(3)
        skip_list.insert(0, 123)

        skip_list.delete(0)

    def output_list(self, skip_list):
        print("result:%d", skip_list.get(0))
        pass

if __name__ == '__main__':
    unittest.main()





