
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 设备树
 *
 * @author yongzhi.zhan
 */

public class DeviceTree {
    private static AtomicInteger m_UniqueId;
    private Map<Integer, Bucket> m_BucketMap = new HashMap<>();
    private Bucket m_RootBucket;

    public static Integer generateId() {
        Integer tCurId = m_UniqueId.incrementAndGet();
        return tCurId;
    }

    public boolean addBucket(Bucket vParent, Bucket vCurrent) {
        if(null == vParent) {
            m_RootBucket = vCurrent;
            return true;
        }

        Integer tPid = vParent.getId();
        if(false == m_BucketMap.containsKey(tPid))
            return false;

        vParent.addItem(vCurrent);
    }

    public void removeBucket(Bucket vBucket) {

    }

    public void printTree() {
        //
    }
}
