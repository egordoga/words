package com.e.words.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.e.words.db.VocabRepo;
import com.e.words.entity.ForeignWordWithTranslate;

import java.util.List;

public class VocabularyViewModel extends AndroidViewModel {

    private VocabRepo repo;
    private MutableLiveData<List<ForeignWordWithTranslate>> foreignWWTs = new MutableLiveData<>();

    public VocabularyViewModel(@NonNull Application application) {
        super(application);

        repo = new VocabRepo();
        foreignWWTs.setValue(repo.getForeignWWT());
    }
}
