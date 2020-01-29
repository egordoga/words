
package com.e.words.abby.abbyEntity.genereted;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonObj {

    @JsonProperty("ArticleId")
    public String articleId;
    @JsonProperty("Body")
    public List<Body> bodies;
    @JsonProperty("Dictionary")
    public String dictionary;
    @JsonProperty("Title")
    public String title;
    @JsonProperty("TitleMarkup")
    public List<TitleMarkup> titleMarkupList;


}
