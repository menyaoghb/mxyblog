package com.mxy.test.base.child;

import com.mxy.test.base.base.Base;

import java.util.Arrays;

public class ChildTwo {
    private static int i = 0;
    private static Base base;
    public static void main(String[] args) {
        if (i==0){
            base = ChildOne.setBase();
        }else {
//            base.setName();/
        }

        System.err.println(Arrays.toString(base.getName()));
    }

}
