package test0507;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhongzhilong
 * @date 2021/5/7
 * @description
 */
public class DateTest {
    public static void main(String[] args) {
        // 日期格式为yudp日期格式
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String msg = format.format(date);
        System.out.println("msg=" + msg);
        // 字符串日期格式为yudp日期格式
        String stringDate = "2021-05-07 16:35:50";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date temp = sdf.parse(stringDate);
            msg = format.format(temp);
            System.out.println("msg = " + msg);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
