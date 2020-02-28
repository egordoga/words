package com.e.words.abby.abbyEntity.dto;

import java.util.Locale;

public class RuString {

    public String str;
    public Locale locale = new Locale("ru");

    public RuString(String str) {
        this.str = str;
    }

    public RuString() {
    }
}
