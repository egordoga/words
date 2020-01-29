package com.e.words.abby.abbyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleModel {

    @JsonProperty("Title")
    public String title;
    @JsonProperty("TitleMarkup")
    public List<ArticleNode> titleMarkup = new ArrayList<ArticleNode>();
    @JsonProperty("Dictionary")
    public String dictionary;
    @JsonProperty("ArticleId")
    public String articleId;
    @JsonProperty("Body")
    public List<ArticleNode> body = new ArrayList<ArticleNode>();

    @Override
    public String toString() {
        return "ArticleModel{" +
                "title='" + title + '\'' +
                ", titleMarkup=" + titleMarkup +
                ", dictionary='" + dictionary + '\'' +
                ", articleId='" + articleId + '\'' +
                ", body=" + body +
                '}';
    }
}
