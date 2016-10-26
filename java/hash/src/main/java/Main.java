import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wentian on 16/6/1.
 */
public class Main {
    private static void testMapPut(Map obj, int testCount) {
        long start = System.nanoTime();
        for (int i = 0; i < testCount; i++) {
            for (int j = 0; j < 10; j++) {
                obj.put("12345667890" + i, "12345667890");
            }

        }

        double erase = (System.nanoTime() - start) / 1000000000.0;
        System.out.println(String.format("[PUT]count:%d, time:%f", testCount, erase));
    }

    private static void testMapGet(Map obj, int testCount) {
        long start = System.nanoTime();
        for (int i = 0; i < testCount; i++) {
            for (int j = 0; j < 10; j++) {
                obj.get("12345667890" + i);
            }
        }

        double erase = (System.nanoTime() - start) / 1000000000.0;
        System.out.println(String.format("[GET]count:%d, time:%f", testCount, erase));
    }

    public static void main(String[] args) {
        int testCount = 100 * 10000;

        System.out.println("start hashMap test ... ");
        Map hashMap = new HashMap();
        testMapPut(hashMap, testCount);
        testMapGet(hashMap, testCount);

        System.out.println("start treeMap test ... ");
        Map treeMap = new TreeMap();
        testMapPut(treeMap, testCount);
        testMapGet(treeMap, testCount);
    }
}
