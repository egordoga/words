
package com.e.words.abby.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {

    @JsonProperty("IsOptional")
    public Boolean isOptional;
    @JsonProperty("Items")
    public List<Item> items;
    @JsonProperty("Node")
    public NodeType node;
    @JsonProperty("Text")
    public String text;
    @JsonProperty("Type")
    public int type;

    @JsonProperty("Markup")
    public List<Markup> markups;

    @Override
    public String toString() {
        return "Body{" +
                "\n isOptional=" + isOptional +
                ",\n items=" + items +
                ",\n node='" + node + '\'' +
                ",\n text=" + text +
                ",\n type=" + type +
                ",\n markups=" + markups +
                '}';
    }
}
