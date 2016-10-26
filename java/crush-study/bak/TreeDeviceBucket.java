package com.github.yongzhizhan.crushstudy;

/**
 * Tree bucket,使用节点的weight构造决策树
 * 优点:
 *      1. 查找快
 *
 * 缺点:
 *      1. 插入删除慢
 */
public class TreeDeviceBucket implements DeviceBucket{
    @Override
    public boolean addItem(Device device) {
        return false;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public long getWeight() {
        return 0;
    }

    @Override
    public void setWeight(long weight) {

    }
}
