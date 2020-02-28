package com.e.words.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.e.words.abby.abbyEntity.dto.ExampleDto;
import com.e.words.abby.abbyEntity.dto.TranslWithExDto;
import com.e.words.abby.abbyEntity.dto.WordDto;
import com.e.words.entity.ForeignWordWithTranslate;
import com.e.words.entity.entutyNew.Example;
import com.e.words.entity.entutyNew.Transcription;
import com.e.words.entity.entutyNew.Translation;
import com.e.words.entity.entutyNew.Word;

import java.util.List;

@Dao
public abstract class ForeingWordWithTranslDao {

    @Query("select * from word, transcription, translation where word.word == :word " +
            "and transcription.wordId == word.id " +
            "and translation.wordId == word.id")
    public abstract List<ForeignWordWithTranslate> getWordWithTranslate(String word);

    @Insert
    public abstract int issertWord(Word word);

    @Insert
    public abstract int insertTranslation(Translation translation);

    @Insert
    public abstract void insertTranscription(Transcription transcription);

    @Insert
    public abstract void insertExample(Example example);

    @Transaction
    public void saveWordDto(WordDto wordDto) {
        int wordId = issertWord(new Word(wordDto.word.str));
        List<TranslWithExDto> tweList = wordDto.twes;
        for (TranslWithExDto twe : tweList) {
            int translId = insertTranslation(new Translation(twe.transl.str, wordId));
            for (ExampleDto ex : twe.exs) {

            }
        }
    }


}
