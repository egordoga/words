package com.e.words.abby.abbyEntity.dto;

import java.util.ArrayList;
import java.util.List;

public class TranslWithExDto {
    public String transl;
    public List<ExampleDto> exs = new ArrayList<ExampleDto>();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(transl + "\n");
        for (ExampleDto ex : exs) {
            s.append("  ").append(ex.en).append(" -- ").append(ex.ru).append("\n");
        }
        return s.toString();
    }
}
