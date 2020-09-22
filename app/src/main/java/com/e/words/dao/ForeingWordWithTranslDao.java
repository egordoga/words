package com.e.words.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.e.words.entity.ForeignWordWithTranslate;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Transcription;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.Word;

import java.util.List;

@Dao
public abstract class ForeingWordWithTranslDao {

    @Query("select * from word, transcription, translation where word.word == :word " +
            "and transcription.wordId == word.id " +
            "and translation.wordId == word.id")
    public abstract List<ForeignWordWithTranslate> getWordWithTranslate(String word);

    @Insert
    public abstract int insertWord(Word word);

    @Insert
    public abstract int insertTranslation(Translation translation);

    @Insert
    public abstract void insertTranscription(Transcription transcription);

    @Insert
    public abstract void insertExample(Example example);

//    @Transaction
//    public void saveWordDto(WordDto wordDto) {
//        int wordId = insertWord(new Word(wordDto.word.str));
//        List<TranslWithExDto> tweList = wordDto.twes;
//        for (TranslWithExDto twe : tweList) {
//            int translId = insertTranslation(new Translation(twe.transl.str, wordId));
//            for (ExampleDto ex : twe.exs) {
//
//            }
//        }
//    }


}
