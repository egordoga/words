package com.e.words.abby.abbyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Minicard {
    @JsonProperty("SourceLanguage")
    private int sourceLanguage;          //  Source language.
    @JsonProperty("TargetLanguage")
    private int targetLanguage;          //  Target language.
    @JsonProperty("Heading")
    private String heading;              //  Heading.
    @JsonProperty("Translation")
    private WordListItem translation;    //  Translation.
    @JsonProperty("SeeAlso")
    private String[] seeAlso;            //  See also list.

    @Override
    public String toString() {
        return "Minicard{" +
                "sourceLanguage=" + sourceLanguage +
                ", targetLanguage=" + targetLanguage +
                ", heading='" + heading + '\'' +
                ", translation=" + translation +
                ", seeAlso=" + Arrays.toString(seeAlso) +
                '}';
    }
}
