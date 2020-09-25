package com.e.words.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.adapter.WordAdapter;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.repository.WordObjRepo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FullWordFragment extends Fragment {

    private WordAdapter adapter;
    private WordObj wordObj;
    private WordObj smallWord;
    private int positionTab;

    public FullWordFragment() {
    }


    public static FullWordFragment newInstance(WordObj wordObj, WordObj smallWord, int positionTab) {
        FullWordFragment fragment = new FullWordFragment();
        Bundle args = new Bundle();
        args.putSerializable("wordObj", wordObj);
        args.putSerializable("smallWord", smallWord);
        args.putInt("positionTab", positionTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wordObj = (WordObj) getArguments().getSerializable("wordObj");
            smallWord = (WordObj) getArguments().getSerializable("smallWord");
            positionTab = getArguments().getInt("positionTab");
        }
        if (positionTab == 2) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_word, container, false);
        TextView tvWordFw = view.findViewById(R.id.tv_word_fw);
        TextView tvTranscrFw = view.findViewById(R.id.tv_transcr_fw);
        tvWordFw.setText(wordObj.word.word);
    //    String transcrUS = wordObj.word.transcrUS != null ? "   " + wordObj.word.word : "";
        tvTranscrFw.setText(wordObj.word.transcript);
        RecyclerView rvFullWord = view.findViewById(R.id.rv_fw);
        rvFullWord.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordAdapter(this);
        rvFullWord.setAdapter(adapter);
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
                smallWord.translations.add(new TranslationAndExample(tae.translation.translation, tae.translation.index, tae.getCheckedExamples(), false));
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_save) {
//            Toast.makeText(getContext(), wordObj.word.word, Toast.LENGTH_LONG).show();
//            byte[] arr = {1, 5, 6};
//            List<byte[]> list = new ArrayList<>();
//            list.add(arr);
//         //   new WordObjRepo(getContext()).addWord(smallWord, list);
//        //    new WordObjRepo(getContext()).findWordByWord("look");
//         //   new WordObjRepo(getContext()).deleteWordByWord("look");
//            new WordObjRepo(getContext()).printCount();
//            return true;
//        }

        WordObjRepo repo = new WordObjRepo(getContext());
        switch (id) {
//            case R.id.action_save:
//                repo.addWord(smallWord, null);
//                return true;
            case R.id.action_get:
                repo.findWordByWord("look");
                return true;
            case R.id.action_update:
                repo.updateWord(smallWord);
                return true;
            case R.id.action_delete:
                repo.deleteWordByWord("look");
                return true;
            case R.id.action_count:
                repo.printCount();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}