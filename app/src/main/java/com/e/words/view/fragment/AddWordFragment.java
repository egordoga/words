package com.e.words.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.abby.JsonConvertNew;
import com.e.words.entity.dto.FullWordObj;
import com.e.words.entity.dto.WordObj;
import com.e.words.abby.model.Lang;
import com.e.words.abby.rest.RestRequest;
import com.e.words.view.menu.MenuMain;
import com.e.words.db.repository.WordObjRepo;
import com.e.words.util.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AddWordFragment extends Fragment {

    EditText etNewWord;
    Button btnAddNewWord;
    private Context ctx;
    private WordObj wordObj;
    private WordFragment wf;

    public AddWordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getContext();
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_word, container, false);
        etNewWord = view.findViewById(R.id.et_new_word);
        btnAddNewWord = view.findViewById(R.id.btn_add_new_word);
        btnAddNewWord.setOnClickListener(v -> {
            String word = etNewWord.getText().toString().toLowerCase();
            try {
                wordObj = new WordObjRepo(ctx).findWordObjByWord(word);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (wordObj == null) {
                String json = RestRequest.getWordJson(word, Lang.EN.number, Lang.RU.number);
                if ("404".equals(json)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder
                            .setTitle("ERROR 404")
                            .setMessage("Такого слова не найдено в словаре ABBY")
                            .setCancelable(true)
                            .show();
                } else {
                    JsonConvertNew jc = new JsonConvertNew();
                    wordObj = jc.jsonToObj(json);
                    wf = WordFragment.newInstance(wordObj, json, getSounds(jc.sounds), false); //TODO method
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, wf)
                            .commit();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder
                        .setTitle("Внимание!")
                        .setMessage("Такое слово уже есть в Вашем словаре. Открыть его?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            FullWordObj fullWordObj = new Util(ctx).makeFullObj(wordObj);
                            wf = WordFragment.newInstance(fullWordObj.wordObj, fullWordObj.json, null, true);
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_act, wf)
                                    .commit();
                        })
                        .setNegativeButton("CANCEL", (dialog, which) -> Toast.makeText(ctx, "Введите другое слово", Toast.LENGTH_LONG).show())
                        .show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new MenuMain(getActivity()).getMain(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<byte[]> getSounds(List<String> fileNames) {
        List<byte[]> list = new ArrayList<>();
        for (String fileName : fileNames) {
            String snd64 = RestRequest.getSoundString(fileName);
            if ("404".equals(snd64)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder
                        .setTitle("ERROR 404")
                        .setMessage("При загрузке звука произошел сбой\nПопробуйте загрузить слово позже");
            } else {
                assert snd64 != null;
                if (snd64.startsWith("\"")) {
                    snd64 = snd64.substring(1);
                }
                if (snd64.endsWith("\"")) {
                    snd64 = snd64.substring(0, snd64.length() - 1);
                }
                byte[] arr = Base64.getDecoder().decode(snd64);
                list.add(arr);
            }
        }
        return list;
    }
}