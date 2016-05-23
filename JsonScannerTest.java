import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.SymbolTable;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alibaba.fastjson.parser.JSONToken.RBRACE;
import static org.junit.Assert.*;

/**
 * Created by wentian on 16/5/19.
 */
public class JsonScannerTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    //String jStr = "{\"count\":1111,\"gauge\":2222}";
    String jStr = "{\"count\":2222,\"count\":{\"a\":1,\"b\":2},\"count\":[3,4]}";
    //String jStr = "{\"min\":102,\"total\":11111110,\"count\":75,\"avg\":0.000,\"dist\":[{\"v\":5,\"d\":\"200\"},{\"v\":5,\"d\":\"200\"},{\"v\":10,\"d\":\"300\"},{\"v\":15,\"d\":\"400\"},{\"v\":20,\"d\":\"500\"},{\"v\":25,\"d\":\"600\"}],\"max\":0}";
    int count = 100 * 10000;

    //3.675ms 3.536ms
    //8.523ms 7.241ms     长jStr
    @Test
    public void testMap() {
        for (int i = 0; i < count; i++) {
            Map obj = JSONObject.parseObject(jStr);
            Integer count = (Integer) obj.get("count");
            System.out.println("" + count);
        }
    }

    //1.399ms 1.490ms
    //1.800ms 1.313ms
    //1.653ms 1.59ms     长jStr

    String jStr2 = "{\"count\": 2222 ,\"count\": {\"a\":1,\"b\":2} ,\"count\": [3,4] }";
    @Test
    public void testScanner() {
        for (int i = 0; i < 1; i++) {
            JSONScanner jsonScanner = new JSONScanner(jStr2);

            while (!jsonScanner.isEOF()) {
                String fieldStr = "\"count\":";
                if (false == jsonScanner.matchField(fieldStr.toCharArray())) {
                    jsonScanner.nextToken();
                    continue;
                }

                if (jsonScanner.token() == JSONToken.LBRACE) {
                    //对象
                    String fieldStrB = "\"b\":";
                    while(jsonScanner.token() != RBRACE){
                        if (false == jsonScanner.matchField(fieldStrB.toCharArray())) {
                            jsonScanner.nextToken();
                            continue;
                        }

                        Integer b = jsonScanner.intValue();
                        System.out.println(String.format("int b:%d", b));
                        break;
                    }
                }else if (jsonScanner.token() == JSONToken.LBRACKET) {
                    //数组
                    while(jsonScanner.token() != JSONToken.RBRACKET){
                        if(jsonScanner.token() == JSONToken.LITERAL_INT) {
                            Integer value = jsonScanner.intValue();
                            System.out.println(String.format("int array:%d", value));
                        }

                        jsonScanner.nextToken();

                    }
                }else{
                    Integer count = jsonScanner.intValue();
                    System.out.println(String.format("int:%d", count));
                }
            }
        }
    }
}
