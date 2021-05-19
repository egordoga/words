package com.e.words.entity.dto;

public class VocabularyDto {
    public long wordId;
    public String word;
    public String transcript;
    public String translate;

    public VocabularyDto(long wordId, String word, String transcript, String translate) {
        this.wordId = wordId;
        this.word = word;
        this.transcript = transcript;
        this.translate = translate;
    }
}