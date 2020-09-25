package com.e.words.abby.abbyEntity.dto.dto_new;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.entity.entityNew.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WordObj implements Serializable {
//    public String json;
//    public String word;
//  //  public String transcr;
//    public String article;
// //   public List<TranscriptionNew> transcriptions = new ArrayList<>();
//    public long wordId;
//  public List<String> transcriptions = new ArrayList<>();

    @Embedded
    public Word word = new Word();   // TODO thinking

    @Relation(parentColumn = "id", entityColumn = "wordId", entity = Translation.class)
    public List<TranslationAndExample> translations = new ArrayList<>();



    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
      //  for (TranscriptionNew transcription : transcriptions) {
//        for (String transcription : transcriptions) {
//            sb.append(transcription);
//        }
        sb.append("\n");
        int i = 1;
        for (TranslationAndExample translation : translations) {
            sb.append(i++).append(". ").append(translation).append("\n");
        }
        return sb.toString(); //transcriptions.toString()/* + translations.toString()*/;
    }
}
