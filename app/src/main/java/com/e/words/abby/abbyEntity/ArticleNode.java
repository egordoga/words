package com.e.words.abby.abbyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleNode {

    @JsonProperty("Node")
    public NodeType node;
    @JsonProperty("Text")
    public String text;
    @JsonProperty("IsOptional")
    public boolean isOptional;

    @Override
    public String toString() {
        return "ArticleNode{" +
                "node=" + node +
                ", text='" + text + '\'' +
                ", isOptional=" + isOptional +
                '}';
    }
}
