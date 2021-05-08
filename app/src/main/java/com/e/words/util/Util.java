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

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Util {

    private final WordObjRepo repo;

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

    public static String arrToString(String[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(", ").append(arr[i]);
        }
        return sb.toString();
    }

    public static String ListToStringForDB(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(";;").append(s);
        }
        return sb.toString();
    }

    public interface CallSpin {
        void refreshSpin(int position);
    }
}
