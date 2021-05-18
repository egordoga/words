package com.e.words.entity.dto;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.e.words.entity.Translation;
import com.e.words.entity.TranslationAndExample;
import com.e.words.entity.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordObj implements Serializable {

    @Embedded
    public Word word = new Word();   // TODO thinking

    @Relation(parentColumn = "id", entityColumn = "wordId", entity = Translation.class)
    public List<TranslationAndExample> translations = new ArrayList<>();


    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        int i = 1;
        for (TranslationAndExample translation : translations) {
            sb.append(i++).append(". ").append(translation).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordObj)) return false;
        WordObj wordObj = (WordObj) o;
        return Objects.equals(word, wordObj.word) &&
                Objects.equals(translations, wordObj.translations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, translations);
    }
}
