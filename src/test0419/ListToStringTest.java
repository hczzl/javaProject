package test0419;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhongzhilong
 * @date 2021/4/19
 * @decription
 */
public class ListToStringTest {
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "小明");
        map.put("id", "1");
        list.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "小张");
        map2.put("id", "2");
        list.add(map2);
        // 1、List<Map<String,Object>>中获取List<String>
        List<String> stringList = list.stream().map(e -> e.get("id").toString()).collect(Collectors.toList());
        stringList.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println("\n");
        // 2、List<Map<String,Object>>中获取String
        String idString = list.stream().map(e -> e.get("id").toString()).collect(Collectors.joining(","));
        System.out.println("idString = "+idString);
    }
}
