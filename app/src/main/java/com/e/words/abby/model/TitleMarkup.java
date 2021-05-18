
package com.e.words.abby.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TitleMarkup {

    @JsonProperty("IsAccent")
    public Boolean isAccent;
    @JsonProperty("IsItalics")
    public Boolean isItalics;
    @JsonProperty("IsOptional")
    public Boolean isOptional;
    @JsonProperty("Node")
    public String node;
    @JsonProperty("Text")
    public String text;

}
