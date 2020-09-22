package com.e.words.abby.abbyEntity.dto;

import com.e.words.abby.depricated.dto.ExampleDtoOld;
import com.e.words.abby.depricated.dto.RuString;

import java.util.ArrayList;
import java.util.List;

public class TranslWithExDto {
    public RuString transl = new RuString();
    public List<ExampleDtoOld> exs = new ArrayList<ExampleDtoOld>();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(transl.str + "\n");
        for (ExampleDtoOld ex : exs) {
            s.append("  ").append(ex.en.str).append(ex.ru.str).append("\n");
        }
        return s.toString();
    }
}
