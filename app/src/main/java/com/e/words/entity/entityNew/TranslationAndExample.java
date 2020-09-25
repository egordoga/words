package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.e.words.abby.abbyEntity.dto.dto_new.ExampleDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TranslationAndExample implements Serializable {
//    public int id;
//    public String translation;
//    @ColumnInfo(name = "idx")
//    public int index;
//    @Ignore
//    public Boolean isChecked = false;
    @Embedded
    public Translation translation = new Translation();  //TODO thinking

    @Relation(parentColumn = "id", entity = Example.class, entityColumn = "translId")
    public List<Example> examples = new ArrayList<>();

    public TranslationAndExample() {
    }


    @Ignore
    public TranslationAndExample(String transl, int index, List<Example> examples, Boolean isChecked) {
        this.translation.translation = transl;
        this.translation.index = index;
        this.examples = examples;
        this.translation.isChecked = isChecked;
    }

    public TranslationAndExample(String transl, List<Example> examples, int index) {
        this.translation.translation = transl;
        this.examples = examples;
        this.translation.index = index;
    }

    public List<Example> getCheckedExamples() {
        List<Example> checkedList = new LinkedList<>();
        for (Example example : examples) {
            if (example.isChecked) {
                checkedList.add(new Example(example.example, example.index, false));
            }
        }
        return checkedList;
    }
}
