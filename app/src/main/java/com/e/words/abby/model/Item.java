
package com.e.words.abby.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
