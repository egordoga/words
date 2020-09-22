package com.e.words.abby.abbyEntity.dto;

import com.e.words.abby.depricated.dto.EnString;

import java.util.ArrayList;
import java.util.List;

public class WordDtoNew {
    public EnString word = new EnString();
    public String article;
    public List<String> transcripts = new ArrayList<>();
    public List<String> sounds = new ArrayList<>();
}
