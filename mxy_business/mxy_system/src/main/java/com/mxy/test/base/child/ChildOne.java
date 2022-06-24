package com.mxy.test.base.child;

import com.mxy.test.base.base.Base;

public class ChildOne {


    public static Base setBase(){
        Base base = new Base();
        String[] name = "张三,李四,王五".split(",");
        base.setName(name);
        return base;
    }
}
