package com.e.words.abby.abbyEntity;

public enum WordListItemType {
    NONE(0),                //  Nothing found. Invalid element
    EXACTWORD(1),           //  Exact match for word or phrase.
    LEMMATIZED_VARIANT(2),  //  Lemmatized variant (word inflected form).
    SUBPHRASE(4),           //  Subphrase (lemmatized).
    SPELLING_VARIANT(8),    //  Spelling vatiant.
    PREFIX_VARIANT(16);     //  Prefix variant (for last word of phrase)

    WordListItemType(int type) {

    }
}
