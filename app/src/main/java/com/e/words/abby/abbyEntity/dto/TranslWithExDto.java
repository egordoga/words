package com.e.words.abby.abbyEntity.dto;

import java.util.ArrayList;
import java.util.List;

public class TranslWithExDto {
    public RuString transl = new RuString();
    public List<ExampleDto> exs = new ArrayList<ExampleDto>();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(transl.str + "\n");
        for (ExampleDto ex : exs) {
            s.append("  ").append(ex.en.str).append(ex.ru.str).append("\n");
        }
        return s.toString();
    }
}
