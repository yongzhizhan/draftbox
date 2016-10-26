import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class UniformBucketTest {
    @Test
    public void testChange(){
        UniformBucket rackBucket = new UniformBucket();

        //add 10 device to rack bucket
        for(int i=0; i<10; i++){
            Bucket device = new Bucket(i + 12340, BucketType.DEVICE);
            rackBucket.add(device);
        }
//
//        int hash = 12345;
//        Bucket destBucket = rackBucket.choose(hash);
//
//        if(null == destBucket)
//            Assert.fail("destBucket is null");
    }

    @Test
    public void testXorRange(){
        Map<Integer, Boolean> valueMap = new HashMap();
        final int hashSeek = 1315423911;
        final int seekX = 231232;
        final int seekY = 1232;

        for(int i=0; i<1000; i++){
            for(int j = 0; j < 1000; j++) {
                int ran1 = ThreadLocalRandom.current().nextInt();

                int ranFor = ThreadLocalRandom.current().nextInt() % 10;
                for(int randI = 0; randI<ranFor; randI++){
                    ThreadLocalRandom.current().nextInt();
                }

                int ran2 = ThreadLocalRandom.current().nextInt();

                Integer xorVal = ran1 ^ ran2 ^ hashSeek;

//                int hash1 = FNVHash.hash32(xorVal);
//                xorVal = ran1 ^ hash1 ^ seekX;
//
//                int hash2 = FNVHash.hash32(xorVal);
//                xorVal = ran2 ^ hash2 ^ seekY;

                int hash3 = FNVHash.hash32(xorVal);

                valueMap.put(hash3, true);
            }
        }

        //size:9988398
        System.out.format("size:%d\n", valueMap.size());
    }
}