package com.e.words.abby.depricated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordListItem {
    @JsonProperty("Heading")
    public String heading;          //  Word list item heading.
    @JsonProperty("Translation")
    public String translation;      //  Several first translations.
    @JsonProperty("DictionaryName")
    public String dictionaryName;   //  Item's dictionary name.
    @JsonProperty("SoundName")
    public String soundName;        //  Sound file name for this item.
    @JsonProperty("Type")
    public WordListItemType type;   //  Word list item type.
    @JsonProperty("OriginalWord")
    public String originalWord;     //  Original word used to build this item. For one-word requests matches the prefix.
                                    //  Can be one word from phrase or sub-phrase if prefix was a phrase.


    @Override
    public String toString() {
        return "WordListItem{" +
                "heading='" + heading + '\'' +
                ", translation='" + translation + '\'' +
                ", dictionaryName='" + dictionaryName + '\'' +
                ", soundName='" + soundName + '\'' +
                ", type=" + type +
                ", originalWord='" + originalWord + '\'' +
                '}';
    }
}
