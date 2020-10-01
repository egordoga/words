package com.e.words.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.e.words.abby.JsonConvertNew;
import com.e.words.abby.abbyEntity.dto.dto_new.FullWordObj;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.repository.WordObjRepo;

import java.util.concurrent.ExecutionException;

public class Util {

    private WordObjRepo repo;

    public Util(Context context) {
        repo = new WordObjRepo(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public FullWordObj makeFullObj(WordObj wordDb) {
        String json = null;
        try {
            json = repo.findJsonByWordId(wordDb.word.id).json;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        JsonConvertNew jc = new JsonConvertNew();
        WordObj wordObj = null;
        if (json != null) {
            wordObj = jc.jsonToObj(json);
            for (TranslationAndExample taeDb : wordDb.translations) {
                Translation tr = wordObj.translations.get(taeDb.translation.index).translation;
                tr.isChecked = true;
                for (Example exDb : taeDb.examples) {
                    Example objEx = wordObj.translations.get(taeDb.translation.index).examples.get(exDb.index);
                    objEx.isChecked = true;
                }
            }
        }


        return new FullWordObj(wordObj, json);
    }
}
