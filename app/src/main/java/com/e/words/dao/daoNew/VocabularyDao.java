package com.e.words.dao.daoNew;

import androidx.room.Dao;
import androidx.room.Query;

import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.entity.entityNew.Word;

import java.util.LinkedList;
import java.util.List;

@Dao
public abstract class VocabularyDao {

    @Query("select * from Word")
    public abstract List<Word> findAllWords();

//    public List<VocabularyDto> getAllVocabularyDto() {
//        List<VocabularyDto> list = new LinkedList<>();
//        List<Word> words = findAllWords();
//        for (Word word : words) {
//            list.add(new VocabularyDto(word.word, word.transcript, word.))
//        }
//    }
}
