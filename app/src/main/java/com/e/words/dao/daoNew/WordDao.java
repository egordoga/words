package com.e.words.dao.daoNew;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Json;
import com.e.words.entity.entityNew.Sound;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.entity.entityNew.Word;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addAllExample(List<Example> examples);

    @Insert/*(onConflict = OnConflictStrategy.REPLACE)*/
    public abstract long addTranslation(Translation translation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addWord(Word word);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public abstract long addWordObj(WordObj wordObj);

    @Insert
    public abstract void addSound(Sound sound);

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
    public void addWord(WordObj wordObj, String jsonStr, @Nullable List<byte[]> sounds) {
        //      String transcrUS = wordObj.transcriptions.size() > 1 ? wordObj.transcriptions.get(1) : null;
        //      Word word = new Word(wordObj.word, wordObj.json, wordObj.transcriptions.get(0), transcrUS);

        long wordId = addWord(wordObj.word);
        Json json = new Json(jsonStr, wordId);
        addJson(json);
        if (sounds != null) {
            byte[] soundUS = sounds.size() > 1 ? sounds.get(1) : null;
            Sound sound = new Sound(sounds.get(0), soundUS, wordId);
            addSound(sound);
        }
        for (TranslationAndExample tae : wordObj.translations) {
            tae.translation.wordId = wordId;
            addTranslationWithExamples(tae.translation, tae.examples);
        }
    }

    @Query("select * from Example where translId = :translId")
    public abstract List<Example> findExampleByTranslId(long translId);

    @Query("select * from Translation where wordId = :wordId")
    public abstract List<Translation> findTranslationsByWordId(long wordId);

    @Query("select * from Word where word = :word")
    public abstract Word findWordByWord(String word);

    @Transaction
    @Query("select * from Word where word = :word")
    public abstract WordObj findWordObjByWord(String word);

    @Transaction
    @Query("select id, translation, idx from Translation where wordId = :wordId")
    public abstract List<TranslationAndExample> findTweByWordId(long wordId);


    @Query("SELECT COUNT(id) FROM Word")
    public abstract int getCountWord();

    @Query("SELECT COUNT(id) FROM Translation")
    public abstract int getCountTr();

    @Query("SELECT COUNT(id) FROM Example")
    public abstract int getCountEx();

    @Update
    public abstract void updateWord(Word word);


//    @Transaction
//    public WordObj getWordObjFromDB(String word) {
//        WordObj wordObj = new WordObj();
//        List<TranslAndEx> taeList = new ArrayList<>();
//        List<ExampleDto> exampleDtos = new LinkedList<>();
//        Word wordDb = findWordByWord(word);
//        List<Translation> translations = findTranslationsByWordId(wordDb.id);
//        for (Translation translation : translations) {
//            List<Example> examples = findExampleByTranslId(translation.id);
//            for (Example example : examples) {
//                exampleDtos.add(new ExampleDto(example.example, example.index));
//            }
//            taeList.add(new TranslAndEx(translation.translation, exampleDtos, translation.index));
//        }
//        wordObj.word = wordDb.word;
//        wordObj.json = wordDb.json;
//        wordObj.translations = taeList;
//        List<String> transcriptionList = new ArrayList<>();
//        transcriptionList.add(wordDb.transcrGB);
//        if (wordDb.transcrUS != null) {
//            transcriptionList.add(wordDb.transcrUS);
//        }
//        wordObj.transcriptions = transcriptionList;
//        return wordObj;
//    }

//    @Transaction
//    public WordObj getWordObjFromDB(String word) {
//        WordObj wordObj = new WordObj();
//        Word wordDb = findWordByWord(word);
//        List<TranslationAndExample> taeList = findTweByWordId(wordDb.id);
//        wordObj.word = wordDb.word;
//        wordObj.json = wordDb.json;
//        wordObj.wordId = wordDb.id;
//        wordObj.translations = taeList;
////        for (TranslationAndExample twe : tweList) {
////            wordObj.translations.add(new TranslationAndExample(twe.translation, convertExampleToDto(twe.examples), twe.index));
////        }
//        List<String> transcriptionList = new ArrayList<>();
//        transcriptionList.add(wordDb.transcript);
//        if (wordDb.transcrUS != null) {
//            transcriptionList.add(wordDb.transcrUS);
//        }
//        wordObj.transcriptions = transcriptionList;
//        return wordObj;
//    }

    @Update
    public abstract void updateTranslation(Translation translation);

    @Update
    public abstract void updateAllExamples(List<Example> examples);

    @Transaction
    public void updateTranslationWithExample(long wordId) {

    }

    @Delete
    public abstract void deleteAllExamples(List<Example> examples);

    @Delete
    public abstract void deleteAllTranslations(List<Translation> translations);

    //  @Transaction
    @Query("delete from Word where id = :id")
    public abstract void deleteWordById(long id);

    @Query("delete from Word where word = :word")
    public abstract void deleteWordByWord(String word);

    @Query("delete from Translation where wordId = :wordId")
    public abstract void deleteTranslationByWordId(long wordId);

    @Query("delete from Translation where wordId = :translId")
    public abstract void deleteExamplesByTranslId(long translId);

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

//    @Transaction
//    public void deleteWordObj(WordObj wordObj) {
//        deleteAllExamples(wordObj.translations.);
//        deleteWordById();
//    }

//    private List<Example> convertExampleToDto(List<Example> examples) {
//        List<Example> exDtos = new ArrayList<>();
//        for (Example example : examples) {
//            exDtos.add(new Example(example.example, example.index));
//        }
//        return exDtos;
//    }

}
