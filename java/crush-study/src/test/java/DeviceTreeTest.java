import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wentian on 16/6/29.
 */
public class DeviceTreeTest {
    @Test
    public void testDefault(){
        UniformBucket tRootBucket = new UniformBucket(DeviceTree.generateId(), BucketType.ROOM);

        //Add root bucket
        DeviceTree tDeviceTree = new DeviceTree();
        tDeviceTree.addBucket(null, tRootBucket);

        //
        tDeviceTree.printTree();
    }
}