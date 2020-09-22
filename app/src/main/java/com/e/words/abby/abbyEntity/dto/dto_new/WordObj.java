package com.e.words.abby.abbyEntity.dto.dto_new;

import androidx.annotation.NonNull;

import com.e.words.abby.abbyEntity.dto.TranslAndEx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WordObj implements Serializable {
    public String json;
    public String word;
  //  public String transcr;
    public String article;
 //   public List<TranscriptionNew> transcriptions = new ArrayList<>();
    public List<String> transcriptions = new ArrayList<>();
    public List<TranslAndEx> translations = new ArrayList<>();




    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
      //  for (TranscriptionNew transcription : transcriptions) {
        for (String transcription : transcriptions) {
            sb.append(transcription);
        }
        sb.append("\n");
        int i = 1;
        for (TranslAndEx translation : translations) {
            sb.append(i++).append(". ").append(translation).append("\n");
        }
        return sb.toString(); //transcriptions.toString()/* + translations.toString()*/;
    }
}
