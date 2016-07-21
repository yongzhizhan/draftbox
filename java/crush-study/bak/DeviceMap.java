package com.github.yongzhizhan.crushstudy;

import java.util.List;

/**
 * 使用设备树描述各节点的情况,层次机构为: root/机房/机柜列/机架/主机,通过设备树和crush算法,就可以计算到obj id对应的存储位置
 *  1.划分为bucket和device,机房/机柜列,可以作为bucket,主机作为device
 *  2.device作为叶子节点
 *  3.bucket的items可以包含多个bucket或者device
 *  4.device有权重,bucket的权重是其子节点权重之和
 *  5.bucket的item有不同的存储结构,便于对子节点的变更和查找
 *
 *TODO:
 *  支持json加载
 */

public class DeviceMap {
    private DeviceBucket rootBucket;

    public DeviceMap(){
        //
    }

    /**
     * 通过obj id获取存储节点
     * @param objId
     * @return
     */
    List<Device> findDevice(String objId){
        return null;
    }

}
