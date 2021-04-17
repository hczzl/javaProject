import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongzhilong
 * @date 2021-04-17
 * @description 将一棵树的所有分支输出
 */
public class TreeTest {

    public static void main(String[] args) {

        List<Map<String, Object>> childList1 = new ArrayList<>();
        Map<String, Object> map4 = new HashMap<>();
        map4.put("number", "d");
        childList1.add(map4);

        List<Map<String, Object>> childList = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("number", "b");
        map2.put("childList", childList1);
        childList.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("number", "c");
        map3.put("childList", childList1);
        childList.add(map3);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("number", "c");
        childList.add(map5);

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("number", "a");
        map.put("childList", childList);
        list.add(map);
        List<List<Map<String, Object>>> resultList = new ArrayList<>();
        for (Map<String, Object> tree : list) {
            List<Map<String, Object>> tempList = new ArrayList<>();
            selectLine(resultList, tempList, tree);
        }
        System.out.println("开始输出树的所有分支\n");
        resultList.forEach(e -> {
            List<Map<String, Object>> tempList = e;
            tempList.forEach(e2 -> {
                System.out.print(e2.get("number") + " ");
            });
            System.out.println("\n");
        });
    }

    public static void selectLine(List<List<Map<String, Object>>> list, List<Map<String, Object>> beforeList, Map<String, Object> tree) {
        beforeList.add(tree);
        if (tree.containsKey("childList")) {
            List<Map<String, Object>> childList = (List<Map<String, Object>>) tree.get("childList");
            if (childList.size() >= 2) {
                for (Map<String, Object> temp : childList) {
                    List<Map<String, Object>> tempList = new ArrayList<>();
                    for (Map<String, Object> before : beforeList) {
                        Map<String, Object> tempMap = new HashMap<>();
                        tempMap.put("number", before.get("number"));
                        tempMap.put("childList", before.get("childList"));
                        tempList.add(tempMap);
                    }
                    selectLine(list, tempList, temp);
                }
            } else {
                selectLine(list, beforeList, childList.get(0));
            }
        } else {
            list.add(beforeList);
        }

    }

}
