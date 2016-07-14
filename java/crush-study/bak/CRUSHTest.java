import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class CRUSHTest {

    @Test
    public void testDefault(){

        Integer val = this.xx((ThreadLocalRandom.current().nextInt() % 2 == 0)?null : 1);
        val = val + 1;

        System.out.println(val.toString());

        Map test = new HashMap();

        //noinspection unchecked
        xxxkjkj((Map<String, Object>)test);

        File directory = new File("/");
    }

    private void xxxkjkj(Map<String, Object> mmm){

    }
    /**
     *
     * @param val 123
     * @return {@code null}
     */
    @Nullable
    private Integer xx(@SuppressWarnings("UnusedParameters") Integer val){
        if((ThreadLocalRandom.current().nextInt() % 2 == 0))
            return 2;

        return 1;
    }


    /**
     * 避免使用浮点数计算
     */
    @Test
    public void testListHash(){

        long sumWeight = 100000;
        long weight = 100;

        long randVal = FNVHash1("12345");
        randVal &= 0xffff;  //([0 - 65535] * sum_weight / 65535) < weight  ==> [0 - 1] < weight / sum_weight

        randVal *= sumWeight;
        randVal = randVal >> 16;

        System.out.format("randVal small than weight: %b\n", randVal < weight);

    }

    private static int FNVHash1(String data) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(int i=0;i<data.length();i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }
}
