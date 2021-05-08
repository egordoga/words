package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addTranslation(Translation translation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addWord(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

//    @Query("select * from Word where trackName = :trackName")
//    public abstract List<Word> findWordsByTrackName(String trackName);

    @Query("select * from Word where word = :word")
    public abstract Word findWordByWord(String word);

    @Transaction
    @Query("select * from Word where word = :word")
    public abstract WordObj findWordObjByWord(String word);

    @Query("select * from Word where word = :word")
    public abstract Word findWordByWordStr(String word);

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

    @Update
    public abstract void updateWords(List<Word> words);

    @Transaction
    public void updateTranslationWithExample(long wordId) {

    }

    @Query("delete from Word where id = :id")
    public abstract void deleteWordById(long id);

    @Query("delete from Word where word = :word")
    public abstract void deleteWordByWord(String word);

    @Delete
    public abstract void deleteWord(Word word);

    @Delete
    public abstract void deleteTranslation(Translation translation);

    @Delete
    public abstract void deleteExamples(List<Example> examples);

  //  @Transaction
    public void deleteTranslationAndExample(TranslationAndExample tae) {
        deleteExamples(tae.examples);
        deleteTranslation(tae.translation);
    }

    @Query("delete from Translation where wordId = :wordId")
    public abstract void deleteTranslationByWordId(long wordId);

    @Query("delete from Example where translId = :translId")
    public abstract void deleteExampleByTranslationId(long translId);

    @Query("delete from Example where translId in (:translIds)")
    public abstract void deleteAllExampleByTranslationId(long[] translIds);

    @Query("delete from Json where wordId = :wordId")
    public abstract void deleteJsonByWordId(long wordId);


    @Transaction
    public void deleteWordObj(WordObj wordObj) {
        for (TranslationAndExample tae : wordObj.translations) {
            deleteExamples(tae.examples);
            deleteTranslation(tae.translation);
        }
        deleteJsonByWordId(wordObj.word.id);
        deleteWord(wordObj.word);
    }

    @Transaction
    public void updateTranslationAndExample(WordObj wordObj, long[] oldTranslId) {
      //  long[] translIds = new long[wordObj.translations.size()];

        long wordId = wordObj.word.id;
//        for (TranslationAndExample tae : wordObj.translations) {
//            deleteExamples(tae.examples);
//        }
        deleteAllExampleByTranslationId(oldTranslId);
        deleteTranslationByWordId(wordId);
        for (TranslationAndExample tae : wordObj.translations) {
            tae.translation.wordId = wordId;
            addTranslationWithExamples(tae.translation, tae.examples);
        }
    }
}
