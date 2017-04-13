package com.example.tby.myapplication.utils;

/**
 * FragmentManager存储Fragment用的tag的枚举
 * tag表示Fragment的完整类名
 */
public enum FragmentTag {

    TAG_HOME("com.example.tby.myapplication.fragment.IndexActivity"),
    TAG_CART("com.example.tby.myapplication.fragment.CartFragment"),
    TAG_MY("com.example.tby.myapplication.fragment.MyFragment");

    String tag;

    private FragmentTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
