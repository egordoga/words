package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.abby.abbyEntity.dto.dto_new.ExampleDto;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Sound;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.entity.entityNew.Word;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Dao
public abstract class WordDao {

    @Insert
    public abstract void addAllExample(List<Example> examples);

    @Insert
    public abstract long addTranslation(Translation translation);

    @Insert
    public abstract long addWord(Word word);

    @Insert
    public abstract void addSound(Sound sound);

    private void addTranslationWithExamples(Translation translation, List<Example> examples) {
        long id = addTranslation(translation);
        for (Example example : examples) {
            example.translId = id;
        }
        addAllExample(examples);
    }

    @Transaction
    public void addWord(WordObj wordObj, List<byte[]> sounds) {
        String transcrUS = wordObj.transcriptions.size() > 1 ? wordObj.transcriptions.get(1) : null;
        Word word = new Word(wordObj.word, wordObj.json, wordObj.transcriptions.get(0), transcrUS);
        long wordId = addWord(word);
        byte[] soundUS = sounds.size() > 1 ? sounds.get(1) : null;
        Sound sound = new Sound(sounds.get(0), soundUS, wordId);
        addSound(sound);
        for (TranslAndEx tae : wordObj.translations) {
            List<Example> examples = new LinkedList<>();
            for (ExampleDto exampleDto : tae.exampleDtos) {
                examples.add(new Example(exampleDto.example, exampleDto.index));
            }
            Translation transl = new Translation(tae.transl,tae.index, wordId);
            addTranslationWithExamples(transl, examples);
        }
    }

    @Query("select * from Example where translId = :translId")
    public abstract List<Example> findExampleByTranslId(long translId);

    @Query("select * from Translation where wordId = :wordId")
    public abstract List<Translation> findTranslationsByWordId(long wordId);

    @Query("select * from Word where word = :word")
    public abstract Word findWordByWord(String word);

    @Transaction
    @Query("select id, translation, idx from Translation where wordId = :wordId")
    public abstract List<TranslationAndExample> findTweByWordId(long wordId);

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

    @Transaction
    public WordObj getWordObjFromDB(String word) {
        WordObj wordObj = new WordObj();
        Word wordDb = findWordByWord(word);
        List<TranslationAndExample> tweList = findTweByWordId(wordDb.id);
        wordObj.word = wordDb.word;
        wordObj.json = wordDb.json;
        for (TranslationAndExample twe : tweList) {
            wordObj.translations.add(new TranslAndEx(twe.translation, convertExampleToDto(twe.examples), twe.index));
        }
        List<String> transcriptionList = new ArrayList<>();
        transcriptionList.add(wordDb.transcrGB);
        if (wordDb.transcrUS != null) {
            transcriptionList.add(wordDb.transcrUS);
        }
        wordObj.transcriptions = transcriptionList;
        return wordObj;
    }

    private List<ExampleDto> convertExampleToDto(List<Example> examples) {
        List<ExampleDto> exDtos = new ArrayList<>();
        for (Example example : examples) {
            exDtos.add(new ExampleDto(example.example, example.index));
        }
        return exDtos;
    }

}
