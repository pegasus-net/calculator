package com.icarus.calculator.util;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class SplitExp {
    @NotNull
    public static LinkedList<String> splitExp(String exp) {
        LinkedList<String> list = new LinkedList<>();
        String regex = "(?=[+\\-*/()^!√])|(?<=[+\\-*/()^!√])";
        String[] arr = exp.split(regex);
        for (String s : arr) {
            if (s.isEmpty() || ".".equals(s)) {
                continue;
            }
            if (s.charAt(s.length() - 1) == '.') {
                //数字 xxx. 转为 xxx。
                list.add(s.substring(0, s.length() - 1));
            } else if (s.charAt(0) == '.') {
                //数字 .xxx 转为 0.xxx。
                list.add("0" + s);
            } else if (s.equals("-")) {
                //开头为负数或者 （-1）这种情况，在表达式中加0以免发生错误。
                if (list.isEmpty() || list.getLast().equals("(")) {
                    list.add("0");
                }
                list.add("-");
            } else {
                list.add(s);
            }
        }
        return list;
    }
}
