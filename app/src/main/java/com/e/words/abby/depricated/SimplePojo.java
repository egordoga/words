package com.e.words.abby.depricated;

public class SimplePojo {
    public String str;
    public String str1;
    public int number;

    public static String POJO = "{\"str\":\"aaaa\",\"str1\":\"bbb\",\"number\":5555}";


    @Override
    public String toString() {
        return "SimplePojo{" +
                "str='" + str + '\'' +
                ", str1='" + str1 + '\'' +
                ", number=" + number +
                '}';
    }
}
