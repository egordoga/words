package com.e.words.entity.apiEntity;

import com.e.words.entity.apiEntity.WordListItemType;

public class WordListItem {

    public String heading;   // Заголовок из словника.
    public String translation;  //  Набор переводов (несколько первых переводов карточки).
    public String dictionaryName;  //  Имя словаря из которого вытащили переводы.
    public String soundName;  //  Имя звукового файла для данного элемента.
    public WordListItemType type;  //  Тип элемента словника.
    public String originalWord; //   Оригинальное слово, из которго был получен данный элемент. Для однословных запросов совпадает с запросом.
                                // Для запросов фраз может быть одним из слов в фразе или подфразой
}
