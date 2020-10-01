package com.e.words.abby.abbyEntity.dto.dto_new;

public class VocabularyDto {
    public String word;
    public String transcript;
    public String translate;

    public VocabularyDto(String word, String transcript, String translate) {
        this.word = word;
        this.transcript = transcript;
        this.translate = translate;
    }
}
