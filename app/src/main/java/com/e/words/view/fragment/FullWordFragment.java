package com.e.words.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.db.repository.WordObjRepo;
import com.e.words.entity.Example;
import com.e.words.entity.TranslationAndExample;
import com.e.words.entity.dto.WordObj;
import com.e.words.util.worker.FileWorker;
import com.e.words.view.adapter.WordAdapter;
import com.e.words.view.menu.MenuMain;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FullWordFragment extends Fragment {

    private WordAdapter adapter;
    private WordObj wordObj;
    private WordObj smallWord;
    private int positionTab;
    private String json;
    private List<byte[]> sounds;
    private Context ctx;
    private TextToSpeech tts;

    public FullWordFragment() {
    }


    public static FullWordFragment newInstance(WordObj wordObj, WordObj smallWord, int positionTab,
                                               String json, List<byte[]> sounds, boolean isPresent) {
        FullWordFragment fragment = new FullWordFragment();
        Bundle args = new Bundle();
        args.putSerializable("wordObj", wordObj);
        args.putSerializable("smallWord", smallWord);
        args.putInt("positionTab", positionTab);
        args.putSerializable("sounds", (Serializable) sounds);
        args.putString("json", json);
        args.putBoolean("isPresent", isPresent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            wordObj = (WordObj) args.getSerializable("wordObj");
            smallWord = (WordObj) args.getSerializable("smallWord");
            positionTab = args.getInt("positionTab");
            json = args.getString("json");
            ctx = getContext();
            sounds = (List<byte[]>) args.getSerializable("sounds");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_word, container, false);
        Button btnSave = view.findViewById(R.id.btn_save_word);
        btnSave.setVisibility(View.GONE);
        TextView tvWordFw = view.findViewById(R.id.tv_word_fw);
        TextView tvTranscrFw = view.findViewById(R.id.tv_transcr_fw);
        tvWordFw.setText(wordObj.word.word);
        tvTranscrFw.setText(wordObj.word.transcript);
        RecyclerView rvFullWord = view.findViewById(R.id.rv_fw);
        rvFullWord.setLayoutManager(new LinearLayoutManager(ctx));
        adapter = new WordAdapter(this);
        rvFullWord.setAdapter(adapter);
        WordObjRepo repo = new WordObjRepo(ctx);
        if (positionTab == 2) {
            btnSave.setVisibility(View.VISIBLE);
            btnSave.setOnClickListener(v -> {
                try {
                    WordObj wordObj = repo.findWordObjByWord(smallWord.word.word);
                    if (wordObj == null) {
                        saveWord(repo);
                    } else if (!wordObj.translations.equals(smallWord.translations)) {
                        long[] translIds = wordObj.translations.stream().map(tae -> tae.translation.id).mapToLong(Long::longValue).toArray();
                        boolean isNeedFiles = wordObj.word.trackId != null;
                        smallWord.word.id = wordObj.word.id;
                        smallWord.word.trackId = wordObj.word.trackId;
                        tts = new TextToSpeech(ctx, status -> repo.updateTranslationAndExample(smallWord, translIds, isNeedFiles, tts));
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.loadItems(getList());
    }

    private List<TranslationAndExample> getList() {
        if (positionTab == 1) {
            editFullList();
            return wordObj.translations;
        } else if (positionTab == 2) {
            editSmallWord();
            return smallWord.translations;
        }
        return null;
    }

    public void editSmallWord() {
        smallWord.translations.clear();
        for (TranslationAndExample tae : wordObj.translations) {
            if (tae.translation.isChecked) {
                smallWord.translations.add(new TranslationAndExample(tae.translation.translation,
                        tae.translation.index, tae.getCheckedExamples(), false));
            }
        }
    }

    private void editFullList() {
        for (TranslationAndExample translation : smallWord.translations) {
            if (translation.translation.isChecked) {
                wordObj.translations.get(translation.translation.index).translation.isChecked = false;
                for (Example exampleDto : translation.examples) {
                    wordObj.translations.get(translation.translation.index).examples.get(exampleDto.index).isChecked = false;
                }
                continue;
            }
            for (Example exampleDto : translation.examples) {
                if (exampleDto.isChecked) {
                    wordObj.translations.get(translation.translation.index).examples.get(exampleDto.index).isChecked = false;
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new MenuMain(getActivity()).getMain(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void saveWord(WordObjRepo repo) {
        repo.addWord(smallWord, json);
        FileWorker worker = new FileWorker();
        worker.saveSoundFile(Objects.requireNonNull(ctx), sounds.get(0), smallWord.word.word);
        if (sounds.size() > 1) {
            worker.saveSoundFile(Objects.requireNonNull(ctx), sounds.get(1), smallWord.word.word + "US");
        }
    }
}