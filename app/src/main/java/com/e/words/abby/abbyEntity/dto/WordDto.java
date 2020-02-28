package com.e.words.abby.abbyEntity.dto;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WordDto {
    public EnString word = new EnString();
    public List<TranslWithExDto> twes;
    public List<String> transcripts = new ArrayList<>();
    public List<String> sounds = new ArrayList<>();


    public List<String> getTrack() {
        List<String> track = new ArrayList<>();
//        track.add(word);
//        track.add(twes.get(3).transl);
//        track.add(twes.get(3).exs.get(0).en);
//        track.add(twes.get(3).exs.get(0).ru);
        List<String> list = new ArrayList<>();

//        list.add("track");
//        list.add("issue");
//        list.add("table");
//        list.add("mother");
//        list.add("toy");

        list.add("стол");
        list.add("вилка");
        list.add("телевизор");
        list.add("енот");
        list.add("Игорь");
        list.add("кот");
        //return track;
        return list;
    }

    @NotNull
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
