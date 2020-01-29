package com.e.words.entity.apiEntity;

public enum NodeType {
    COMMENT(0),          //  Комментарий.
    PARAGRAPH(1),        //  Абзац.
    TEXT(2),             //  Простой текст.
    LIST(3),             //  Список.
    LIST_ITEM(4),        //  Элемент списка.
    EXAMPLES(5),         //  Примеры.
    EXAMPLE_ITEM(6),     //  Элемент списка примеров.
    EXAMPLE(7),          //  Пример.
    CARDREFS(8),         //  Ссылки на карточки.
    CARDREF_ITEM(9),     //  Элемент списка ссылок на карточки.
    CARD_REF(10),        //  Ссылка на карточку.
    TRANSCRIPTION(11),   //  Транскрипция.
    ABBREV(12),          //  Аббревиатура.
    CAPTION(13),         //  Заголовок.
    SOUND(14),           //  Ссылка на звуковой файл.
    REF(15),             //  Ссылка.
    UNSUPPORTED(16);     //  Неподдерживаемый тип элемента.

    public int nodeType;

    NodeType(int nodeType) {
        this.nodeType = nodeType;
    }
}
