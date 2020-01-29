
package com.e.words.abby.abbyEntity.genereted;

import java.util.List;

import com.e.words.abby.abbyEntity.NodeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @JsonProperty("IsOptional")
    public Boolean isOptional;
    @JsonProperty("Markup")
    public List<Markup> markupList;
    @JsonProperty("Node")
    public NodeType node;
    @JsonProperty("Text")
    public Object text;

}