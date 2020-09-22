package com.e.words.abby.depricated.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StrWithLocaleDto {

    public String str;
    public Locale locale;

    public StrWithLocaleDto(String str, Locale locale) {
        this.str = str;
        this.locale = locale;
    }

    public StrWithLocaleDto(RuString ruString) {
        this.str = ruString.str;
        this.locale = ruString.locale;
    }

    public StrWithLocaleDto(EnString enString) {
        this.str = enString.str;
        this.locale = enString.locale;
    }

    public static List<StrWithLocaleDto> getTestData() {
        List<StrWithLocaleDto> list = new ArrayList<>();
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");

        list.add(new StrWithLocaleDto("issue", enLoc));
        list.add(new StrWithLocaleDto("Вася", ruLoc));
        list.add(new StrWithLocaleDto("look for", enLoc));
        list.add(new StrWithLocaleDto("кресло", ruLoc));
        list.add(new StrWithLocaleDto("mouse", enLoc));
        list.add(new StrWithLocaleDto("телевизор", ruLoc));

        return list;
    }

    public static List<StrWithLocaleDto> getTestData(WordDto word) {
        List<StrWithLocaleDto> list = new ArrayList<>();

        list.add(new StrWithLocaleDto(word.word));
        list.add(new StrWithLocaleDto(word.word));
        if (word.twes.size() > 1) {
            list.add(new StrWithLocaleDto(word.twes.get(0).transl));
            list.add(new StrWithLocaleDto(word.twes.get(1).transl));
        } else {
            list.add(new StrWithLocaleDto(word.twes.get(0).transl));
        }
        if (!word.twes.get(0).exs.isEmpty()) {
            list.add(new StrWithLocaleDto(word.twes.get(0).exs.get(0).en));
            list.add(new StrWithLocaleDto(word.twes.get(0).exs.get(0).ru));
        }
        return list;
    }
}
