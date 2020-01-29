package com.e.words.entity.apiEntity;

public enum WordListItemType {

    NONE(0),
    EXACT_WORD(1), // Точное совпадение со словом/фразой.
    LEMMATIZED_VARIANT(2), // Лемматизированный вариант.
    SUBPHRASE(4), // Подфраза (лемматизированная).
    SPELLING_VARIANT(8), // Вариант орфокоррекции.
    PREFIX_VARIANT(16);   //  Префиксный вариант (для последнего слова фразы).

    public int type;
    WordListItemType(int type) {
        this.type = type;
    }
}
