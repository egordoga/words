package com.e.words.abby.abbyEntity.dto;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TranslAndEx {
  //  public String word;
    public String transl;
    public List<String> examples = new ArrayList<>();

    public Boolean isChecked = false;

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TR  ").append(transl).append("\n");
        for (String example : examples) {
            sb.append("EX   ").append(example).append("\n");
        }
        return sb.toString();
    }
}
