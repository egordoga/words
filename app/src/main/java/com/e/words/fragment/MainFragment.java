package com.e.words.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.repository.WordObjRepo;
import com.e.words.temp.TestTTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements TextToSpeech.OnInitListener {

    private Button buttonTest;
    private Button btnVocab;
    private Button btnAddWord;
    private Button btnPlay;
    private Button btnTrack;
    private ArticleFragment af;
    private WordFragment wf;
    private AddWordFragment awf;
    private PlayFragment pf;
    private VocabularyFragment vocabFrgm;
    private TrackFragment trackFrgm;
    private TextToSpeech tts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
       // af = new ArticleFragment();
        wf = new WordFragment();
        awf = new AddWordFragment();
        pf = new PlayFragment();
        vocabFrgm = new VocabularyFragment();
        trackFrgm = new TrackFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        buttonTest = view.findViewById(R.id.btn_test_frgm);
        btnVocab = view.findViewById(R.id.btn_vocab);
        btnAddWord = view.findViewById(R.id.btn_add);
        btnPlay = view.findViewById(R.id.btn_play);
        btnTrack = view.findViewById(R.id.btn_track);
        tts = new TextToSpeech(getActivity(), this);

//        button.setOnClickListener(v -> {
//            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_act, wf)
//                    .commit();
//
//        });

        btnAddWord.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, awf)
                    .commit();

  //          new RestTest().printResult();

        });

        btnVocab.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, vocabFrgm)
                    .commit();
        });

        btnTrack.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, trackFrgm)
                    .commit();
        });


        btnPlay.setOnClickListener(v -> {
//            List<WordObj> list = new ArrayList<>();
//            WordObjRepo repo = new WordObjRepo(getContext());
//            try {
//                list.add(repo.findWordByWord("look"));
//                list.add(repo.findWordByWord("issue"));
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            TestTTS testTTS = new TestTTS();
//            for (WordObj wordObj : list) {
//                testTTS.playWordObj(wordObj, tts);
//            }
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, pf)
                    .commit();

        });


        return view;
    }



    @Override
    public void onInit(int status) {

    }
}