package com.e.words.abby.abbyEntity.dto.dto_new;

import com.e.words.entity.entityNew.Example;

import java.io.Serializable;
import java.util.List;

public class ExampleDto implements Serializable {
    public String example;
    public Boolean isChecked = false;
    public int index;

    public ExampleDto(String example, int index) {
        this.example = example;
        this.index = index;
    }

    public ExampleDto(String example, int index, Boolean isChecked) {
        this.example = example;
        this.index = index;
        this.isChecked = isChecked;
    }

    public ExampleDto(List<Example> examples) {

    }
}
