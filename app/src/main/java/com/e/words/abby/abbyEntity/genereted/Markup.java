
package com.e.words.abby.abbyEntity.genereted;

import java.util.List;

import com.e.words.abby.abbyEntity.NodeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Markup {

    @JsonProperty("IsAccent")
    public Boolean isAccent;
    @JsonProperty("IsItalics")
    public Boolean isItalics;
    @JsonProperty("IsOptional")
    public Boolean isOptional;
    @JsonProperty("Markup")
    public List<Markup> markupList;
    @JsonProperty("Node")
    public NodeType node;
    @JsonProperty("Text")
    public String text;

    @JsonProperty("FileName")
    public String fileName;
    @JsonProperty("FullText")
    public String fullText;
    @JsonProperty("Type")
    public int type;
    @JsonProperty("Items")
    public List<Item> items;


    @Override
    public String toString() {
        return "Markup{" +
                "\nisAccent=" + isAccent +
                ",\n isItalics=" + isItalics +
                ",\n isOptional=" + isOptional +
                ",\n markup=" + markupList +
                ",\n node='" + node + '\'' +
                ",\n text='" + text + '\'' +
                ",\n fileName='" + fileName + '\'' +
                ",\n fullText='" + fullText + '\'' +
                '}';
    }
}