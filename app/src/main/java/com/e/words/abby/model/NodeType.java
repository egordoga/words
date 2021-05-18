package com.e.words.abby.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NodeType {
    @JsonProperty("Comment")
    COMMENT(0),                     //    Comment.
    @JsonProperty("Paragraph")
    PARAGRAPH(1),                   //    Paragraph.
    @JsonProperty("Text")
    TEXT(2),                        //    Plain text.
    @JsonProperty("List")
    LIST(3),                        //    List.
    @JsonProperty("ListItem")
    LIST_ITEM(4),                   //    List item.
    @JsonProperty("Examples")
    EXAMPLES(5),                    //    Examples.
    @JsonProperty("ExampleItem")
    EXAMPLE_ITEM(6),                //    Examples list item.
    @JsonProperty("Example")
    EXAMPLE(7),                     //    Example.
    @JsonProperty("CardRefs")
    CARD_REFS(8),                   //    Cards references.
    @JsonProperty("CardRefItem")
    CARD_REF_ITEM(9),               //    Card reference list item.
    @JsonProperty("CardRef")
    CARD_REF(10),                   //    Card reference.
    @JsonProperty("Transcription")
    TRANSCRIPTION(11),              //    Transcription.
    @JsonProperty("Abbrev")
    ABBREV(12),                     //    Abbreviation.
    @JsonProperty("Caption")
    CAPTION(13),                    //    Caption.
    @JsonProperty("Sound")
    SOUND(14),                      //    Sound file link.
    @JsonProperty("Ref")
    REF(15),                        //    Reference.
    @JsonProperty("Unsupported")
    UNSUPPORTED(16);                //    Unsupported element type.

    NodeType(int type) {
    }
}
