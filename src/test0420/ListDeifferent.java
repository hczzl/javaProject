package test0420;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhongzhilong
 * @date 2021/4/20
 * @description 判断两个list的元素值是否完全一样
 */
public class ListDeifferent {
    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("1", "2", "3");
        List<String> list2 = Arrays.asList("2", "1", "3");
        List<String> list3 = Arrays.asList("1", "2", "4");
        System.out.println("方法1：list的stream方法");
        boolean equal = list1.stream().sorted().collect(Collectors.joining())
                .equals(list2.stream().sorted().collect(Collectors.joining()));
        System.out.println("list1和list2比较：" + (equal == true ? "相等" : "不相等"));
        equal = list1.stream().sorted().collect(Collectors.joining())
                .equals(list3.stream().sorted().collect(Collectors.joining()));
        System.out.println("list1和list3比较：" + (equal == true ? "相等" : "不相等"));

        System.out.println("方法2：list的sort方法");
        list1.sort(Comparator.comparing(String::hashCode));
        list2.sort(Comparator.comparing(String::hashCode));
        list3.sort(Comparator.comparing(String::hashCode));
        equal = list1.toString().equals(list2.toString());
        System.out.println("list1和list2比较：" + (equal == true ? "相等" : "不相等"));
        equal = list2.toString().equals(list3.toString());
        System.out.println("list2和list3比较：" + (equal == true ? "相等" : "不相等"));
    }
}
