import java.util.ArrayList;
import java.util.List;

/**
 * 使用余数的方式定位bucket
 * 优点:
 * 1.快,能够在O(1)的时间内定位节点
 * <p>
 * 缺点:
 * 1.更改节点时,会导致所有的hash定位改变
 * <p>
 * Features:
 * cache
 *
 * @author yongzhi.zhan
 */

public class UniformBucket extends Bucket {
    private List<Bucket> items = new ArrayList<>();

    UniformBucket(Integer vId, BucketType vBucketType) {
        super(vId, vBucketType);
    }

    public boolean add(Bucket bucket) {
        return items.add(bucket);
    }

    public boolean remove(Bucket bucket) {
        return items.remove(bucket);
    }

    public Bucket choose(int id, int replicate) {
        Integer position = 0;//hash % items.size();
        return items.get(position);
    }

    private int hash(int x1) {
        //return FNVHash.hash32(x1.)
        return 0;
    }
}
