package com.inslink.controller.element;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RobotLogicTreeController  {

    private static boolean flag = true;

    private static List<List<Map<String, Object>>> excellist = new ArrayList<List<Map<String,Object>>>();

    private static Map<String, Object> parentMap = new HashMap<String, Object>();

    private static List<Map<String,Object>> mlist = new ArrayList<Map<String,Object>>();

    //遍历所有分支
    public static List<Map<String,Object>> getSonMap(Map<String,Object> parentMap1,List<Map<String,Object>> treeElements,List<Map<String,Object>> list) {

        if (parentMap1.get("parent")==null) {
            list.add(parentMap1);
        }

        for (int i=0;i<treeElements.size();i++) {

            Map<String,Object> map = treeElements.get(i);
            if (map.get("parent")!=null && parentMap1.get("id").toString().equals(map.get("parent").toString())) {
                flag = true;

                list.add(map);
                getSonMap(map,treeElements,list);
            }
            //不存在子节点
            else {
                flag = false;
                continue;
            }

            if (!flag) {

                if (list.size()>1) {

                    //判断新增的list中的在树中是否存在子节点
                    boolean exist = false;
                    for (Map<String,Object> s : mlist) {

                        System.out.println(s.get("parent"));
                        if (s.get("parent")!=null && s.get("parent").toString().equals(map.get("id").toString())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        excellist.add(list);
                    }

                }
                //删除此分支上所有只有一个子节点的节点或末支节点
                treeElements.remove(map);

                list = new ArrayList<>();
                getSonMap(parentMap,treeElements,list);
                break;
            }

        }
        return list;

    }





    public static void main(String[] args) {
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("name", "乳腺");
        map1.put("level", "0");
        map1.put("id", "1");
        map1.put("parent", null);

        Map<String,Object> map2 = new HashMap<String,Object>();

        map2.put("name", "乳腺1");
        map2.put("level", "1");
        map2.put("id", "2");
        map2.put("parent", "1");

        Map<String,Object> map3 = new HashMap<String,Object>();

        map3.put("name", "乳腺2-1");
        map3.put("level", "2");
        map3.put("id", "3");
        map3.put("parent", "2");


        Map<String,Object> map4 = new HashMap<String,Object>();

        map4.put("name", "乳腺2-2");
        map4.put("level", "2");
        map4.put("id", "4");
        map4.put("parent", "2");


        Map<String,Object> map5 = new HashMap<String,Object>();

        map5.put("name", "乳腺2-3");
        map5.put("level", "2");
        map5.put("id", "5");
        map5.put("parent", "2");

        Map<String,Object> map6 = new HashMap<String,Object>();

        map6.put("name", "乳腺3-1");
        map6.put("level", "3");
        map6.put("id", "6");
        map6.put("parent", "3");

        Map<String,Object> map8 = new HashMap<String,Object>();

        map8.put("name", "乳腺3-8");
        map8.put("level", "3");
        map8.put("id", "8");
        map8.put("parent", "3");


        Map<String,Object> map7 = new HashMap<String,Object>();

        map7.put("name", "乳腺3-2");
        map7.put("level", "3");
        map7.put("id", "7");
        map7.put("parent", "5");

        List<Map<String,Object>> treeElements = new ArrayList<Map<String,Object>>();
        treeElements.add(map1);
        treeElements.add(map2);
        treeElements.add(map3);
        treeElements.add(map4);
        treeElements.add(map5);
        treeElements.add(map6);
        treeElements.add(map7);
        treeElements.add(map8);

//        Map<String,Object> treeMap = new HashMap<String,Object>();
//        map = sortTreeMap(map1, treeElements,treeMap);

        mlist  =   new  ArrayList<Map<String,Object>>();

        // mlist = new ArrayList<Map<String,Object>>((Collection<? extends Map<String, Object>>) Arrays.asList(new Map[treeElements.size()]));

        //Collections.copy(mlist, treeElements);
        mlist.addAll(treeElements);

        parentMap = map1;

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        list = getSonMap(parentMap, treeElements,list);

        for ( List<Map<String, Object>> slist : excellist) {
            System.out.println("=====");
            for (Map<String,Object> smap : slist) {
                System.out.print(smap.get("id")+" ");
            }
            System.out.println("\n");
        }

    }

}