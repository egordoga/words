package com.e.words.abby.abbyEntity.dto;

import java.util.Locale;

public class EnString {

    public String str;
    public Locale locale = new Locale("en");

    public EnString(String str) {
        this.str = str;
    }

    public EnString() {
    }
}
