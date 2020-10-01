package com.e.words.fragment;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.words.R;
import com.e.words.abby.JsonConvertNew;
import com.e.words.abby.abbyEntity.dto.dto_new.FullWordObj;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.abby.model.Lang;
import com.e.words.abby.rest.RestRequest;
import com.e.words.repository.WordObjRepo;
import com.e.words.util.Util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWordFragment extends Fragment {

   // @BindView(R.id.et_new_word)
    EditText etNewWord;
   // @BindView(R.id.btn_add_new_word)
    Button btnAddNewWord;

    private static WordObjRepo repo;
    private WordObj wordObj;
    private WordFragment wf;
//    private String from = "EN";
//    private String to = "RU";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddWordFragment newInstance(String param1, String param2) {
        AddWordFragment fragment = new AddWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_word, container, false);
  //      ButterKnife.bind(Objects.requireNonNull(getActivity()));
        etNewWord = view.findViewById(R.id.et_new_word);
        btnAddNewWord = view.findViewById(R.id.btn_add_new_word);
        repo = new WordObjRepo(getContext());
        btnAddNewWord.setOnClickListener(v -> {
            String word = etNewWord.getText().toString();
            try {
                wordObj = new FindWordAsyncTask().execute(word).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (wordObj == null) {
                String json = RestRequest.getWordJson(word, Lang.EN.number, Lang.RU.number);
                if ("404".equals(json)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder
                            .setTitle("ERROR 404")
                            .setMessage("Такого слова не найдено в словаре")
                            .setCancelable(true)
                            .show();
                } else {
                    JsonConvertNew jc = new JsonConvertNew();
                    wordObj = jc.jsonToObj(json);
                  //  repo.addWord(wordObj, json, getSounds(jc.sounds));
                    wf = WordFragment.newInstance(wordObj, json, getSounds(jc.sounds)); //TODO method
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, wf)
                            .commit();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
               // WordObj finalWordObj = wordObj;
                builder
                        .setTitle("Внимание!")
                        .setMessage("Такое слово уже есть в словаре. Открыть его?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            FullWordObj fullWordObj = new Util(getContext()).makeFullObj(wordObj);
                            wf = WordFragment.newInstance(fullWordObj.wordObj, fullWordObj.json, null);
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_act, wf)
                                    .commit();
                        })
                        .setNegativeButton("CANCEL", (dialog, which) -> {
                            Toast.makeText(getContext(), "Введите другое слово", Toast.LENGTH_LONG).show();
                        })
                .show();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<byte[]> getSounds(List<String> fileNames) {
        List<byte[]> list = new ArrayList<>();
        for (String fileName : fileNames) {
            String snd64 = RestRequest.getSoundString(fileName);
            if ("404".equals(snd64)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setTitle("ERROR 404")
                        .setMessage("При загрузке звука произошел сбой\nПопробуйте загрузить слово позже");
            } else {
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

    static class FindWordAsyncTask extends AsyncTask<String, Void, WordObj> {
        @Override
        protected WordObj doInBackground(String... strings) {
            return repo.findWordByWord(strings[0]);
        }
    }
}