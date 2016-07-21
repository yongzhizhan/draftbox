package com.github.yongzhizhan.crushstudy;

/**
 * 支持对bucket的添加和查找,每个bucket拥有id
 */
public interface DeviceBucket extends Device{
    public boolean addItem(Device device);
}