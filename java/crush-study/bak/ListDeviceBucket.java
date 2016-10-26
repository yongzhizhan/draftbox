package com.github.yongzhizhan.crushstudy;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用链表作为bucket的存储结构
 * 1. 链表头存储
 */
public class ListDeviceBucket implements DeviceBucket {
    long id;

    class Item {
        Device device;
        long sumWeight;
    }

    private List<Item> itemList = new ArrayList<>();
    private long totalWeight = 0;

    public ListDeviceBucket(long id) {
        this.id = id;
    }

    public boolean addItem(Device device) {
        totalWeight += device.getWeight();

        Item tItem = new Item();
        tItem.device = device;
        tItem.sumWeight = device.getWeight();

        int size = itemList.size();
        if (0 == size) {
            itemList.add(tItem);
            return true;
        }

        Item tPrvItem = itemList.get(size - 1);
        tItem.sumWeight = tPrvItem.sumWeight;
        itemList.add(tItem);

        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeight() {
        return totalWeight;
    }

    public void setWeight(long weight) {
        //
    }
}
