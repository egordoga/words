package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Json;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.entity.entityNew.Word;

import java.util.LinkedList;
import java.util.List;

@Dao
public abstract class WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addAllExample(List<Example> examples);

    @Insert
    public abstract long addTranslation(Translation translation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addWord(Word word);

    @Insert
    public abstract void addJson(Json json);

    private void addTranslationWithExamples(Translation translation, List<Example> examples) {
        long id = addTranslation(translation);
        for (Example example : examples) {
            example.translId = id;
        }
        addAllExample(examples);
    }

    @Transaction
    public void addWord(WordObj wordObj, String jsonStr) {
        long wordId = addWord(wordObj.word);
        Json json = new Json(jsonStr, wordId);
        addJson(json);
        for (TranslationAndExample tae : wordObj.translations) {
            tae.translation.wordId = wordId;
            addTranslationWithExamples(tae.translation, tae.examples);
        }
    }

    @Query("select fileNames from Word where word = :word")
    public abstract String findFileNamesByWord(String word);

    @Query("select * from Json where wordId = :wordId")
    public abstract Json findJsonByWordId(long wordId);

    @Query("select * from Word where trackName = :trackName")
    public abstract List<Word> findWordsByTrackName(String trackName);

    @Transaction
    @Query("select * from Word where word = :word")
    public abstract WordObj findWordObjByWord(String word);

    @Query("select * from Word where id = :id")
    public abstract Word findWordById(long id);

    @Transaction
    @Query("select * from Word order by word")
    public abstract List<WordObj> findAllWordObj();

    @Transaction
    @Query("select * from Word where word in (:in) order by word")
    public abstract List<WordObj> findAllWordByWords(String[] in);

    public List<VocabularyDto> findAllVocabularyDto() {
        List<VocabularyDto> list = new LinkedList<>();
        List<WordObj> words = findAllWordObj();
        for (WordObj word : words) {
            list.add(new VocabularyDto(word.word.id, word.word.word, word.word.transcript,
                    word.translations.get(0).translation.translation));
        }
        return list;
    }

    @Query("SELECT COUNT(id) FROM Word")
    public abstract int getCountWord();

    @Query("SELECT COUNT(id) FROM Translation")
    public abstract int getCountTr();

    @Query("SELECT COUNT(id) FROM Example")
    public abstract int getCountEx();

    @Update
    public abstract void updateWord(Word word);

    @Transaction
    public void updateTranslationWithExample(long wordId) {

    }

    @Query("delete from Word where id = :id")
    public abstract void deleteWordById(long id);

    @Query("delete from Word where word = :word")
    public abstract void deleteWordByWord(String word);

    @Query("delete from Translation where wordId = :wordId")
    public abstract void deleteTranslationByWordId(long wordId);


    @Transaction
    public void deleteWord(WordObj wordObj) {
        //deleteWordById(wordObj.word.id);
    }

    @Transaction
    public void updateTranslationAndExample(WordObj wordObj) {
        long wordId = wordObj.word.id;
        deleteTranslationByWordId(wordId);
        for (TranslationAndExample tae : wordObj.translations) {
            tae.translation.wordId = wordId;
            addTranslationWithExamples(tae.translation, tae.examples);
        }
    }
}
