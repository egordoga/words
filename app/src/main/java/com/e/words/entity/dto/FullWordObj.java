package com.e.words.entity.dto;

public class FullWordObj {

    public WordObj wordObj;
    public String json;

    public FullWordObj(WordObj wordObj, String json) {
        this.wordObj = wordObj;
        this.json = json;
    }
}
