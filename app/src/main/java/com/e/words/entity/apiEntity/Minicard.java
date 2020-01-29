package com.e.words.entity.apiEntity;

public class Minicard {

    public int sourceLanguage;        //  Исходный язык.
    public int targetLanguage;        //  Целевой язык.
    public String heading;            //  Заголовок.
    public WordListItem translation;  //  Перевод.
    public String[] seeAlso;          //  Список рекомендаций.
}
