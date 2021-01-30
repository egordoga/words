package com.e.words.abby.abbyEntity.dto.dto_new;

import java.util.Locale;

public class StrLocale {
    public String str;
    public String fileName;
    public Locale locale;

    public StrLocale(String str, String fileName, Locale locale) {
        this.str = str;
        this.fileName = fileName;
        this.locale = locale;
    }
}
