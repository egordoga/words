package com.e.words.entity;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TranslationAndExample implements Serializable {
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

    public List<Example> getCheckedExamples() {
        List<Example> checkedList = new LinkedList<>();
        for (Example example : examples) {
            if (example.isChecked) {
                checkedList.add(new Example(example.example, example.index, false));
            }
        }
        return checkedList;
    }

    @NotNull
    @Override
    public String toString() {
        return "TranslationAndExample{" +
                "translation=" + translation.translation +
                ", examples=" + examples.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationAndExample)) return false;
        TranslationAndExample that = (TranslationAndExample) o;
        return translation.equals(that.translation) &&
                Objects.equals(examples, that.examples);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translation, examples);
    }
}
