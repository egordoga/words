package com.e.words.entity.apiEntity;

public class ArticleModel {

    public String title;                //  Заголовок словарной статьи.
    public ArticleNode[] titleMarkup;   //  Разметка заголовка. Может использоваться, например, для расстановки ударений.
    public String dictionary;           //  Словарь, к которому относится данная словарная статья.
    public String articleId;            //  Идентификатор статьи.
    public ArticleNode[] body;          //  Тело статьи.
}
