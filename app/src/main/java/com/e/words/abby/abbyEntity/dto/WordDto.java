package com.e.words.abby.abbyEntity.dto;

import java.util.ArrayList;
import java.util.List;

public class WordDto {
    public String word;
    public List<TranslWithExDto> twes;
    public List<String> transcripts = new ArrayList<>();
    public List<String> sounds = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(word + "\n");
        for (String sound : sounds) {
            sb.append(sound).append("  ");
        }
        sb.append("\n");
        for (String transcript : transcripts) {
            sb.append("[").append(transcript).append("]").append("  ");
        }
        sb.append("\n");
        for (TranslWithExDto twe : twes) {
            sb.append(twe.toString());
        }
        return sb.toString();
    }
}
