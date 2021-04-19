package test0419;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhongzhilong
 * @date 2021/4/19
 * @decription
 */
public class ListSort {
    public static void main(String[] args) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        resultList.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        resultList.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        resultList.add(map3);
        // 方法一
        System.out.println("方法一");
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer id1 = (Integer) o1.get("id");
                Integer id2 = (Integer) o2.get("id");
                // 升序
                // return id1.compareTo(id2);
                // 降序
                return id2.compareTo(id1);
            }
        });
        resultList.forEach(e -> {
            System.out.print(e.get("id") + " ");
        });
        System.out.println("\n");
        // 方法二
        System.out.println("方法二");
        List<Map<String, Object>> list = resultList.stream().sorted((e1, e2) -> {
            // 升序
            // return Integer.compare((Integer) e1.get("id"), (Integer) e2.get("id"));
            // 降序
            return -Integer.compare((Integer) e1.get("id"), (Integer) e2.get("id"));
        }).collect(Collectors.toList());
        list.forEach(e->{
            System.out.print(e.get("id") + " ");
        });
        System.out.println("\n");
    }
}
