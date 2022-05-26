package com.mxy.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestController {
    public static void main(String[] args) {
        // String数组 转 int数组
        String s = "1,2,3";
        String[] split = s.split(",");
        int[] ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
        System.out.println(collect);
    }
}
