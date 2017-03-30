package cn.zhanyongzhi.validationidea;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTest {
    @Test
    public void testTimezone() throws ParseException {
        Date current = new Date();

        SimpleDateFormat formatterZone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatterZone.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date newDate = formatterZone.parse("2017-03-28T06:00:00Z");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String str = formatter.format(newDate);
        System.out.println(str);

    }
}
