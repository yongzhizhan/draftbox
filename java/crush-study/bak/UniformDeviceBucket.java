package com.github.yongzhizhan.crushstudy;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用hash算法直接查找
 * 优点:
 *      查找速度快
 *
 * 缺点:
 *      当节点发生变化时,需要将所有数据重新分布
 */
public class UniformDeviceBucket implements DeviceBucket {
    private final List<Object> items = new ArrayList<Object>();

    private long bucketId;
    private long weight;

    public UniformDeviceBucket(long bucketId){
        setId(bucketId);
    }

    public boolean addItem(DeviceBucket bucket) {
        items.add(bucket);
        calWeight();

        return true;
    }

    public boolean addItem(Device device) {
        items.add(device);
        calWeight();

        return true;
    }

    public long getId() {
        return bucketId;
    }

    public void setId(long id) {
        this.bucketId = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        //
    }

    private void calWeight(){
        long totalWeight = 0;

        for(Object item : items){
            if(item instanceof DeviceBucket){
                DeviceBucket bucket = (DeviceBucket) items;
                totalWeight += bucket.getWeight();
            }else if(item instanceof Device){
                Device device = (Device) items;
                totalWeight += device.getWeight();
            }
        }

        //set total weight
        weight = totalWeight;
    }
}
