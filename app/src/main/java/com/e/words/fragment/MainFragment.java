package com.e.words.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.temp.TestTTS;
import com.e.words.temp.TestTTS1;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements TextToSpeech.OnInitListener {

    private Button buttonTest1;
    private Button buttonTest2;
    private Button btnVocab;
    private Button btnAddWord;
    private Button btnPlay;
    private Button btnTrack;
    private Button btnTrackList;
    private Button btnSetting;
    private ArticleFragment af;
    private WordFragment wf;
    private AddWordFragment awf;
    private PlayFragment pf;
    private VocabularyFragment vocabFrgm;
    private TrackFragment trackFrgm;
    private SettingFragment setFrgm;
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
        setFrgm = new SettingFragment();
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
        buttonTest1 = view.findViewById(R.id.btn_test_frgm);
        buttonTest2 = view.findViewById(R.id.btn_test2);
        btnVocab = view.findViewById(R.id.btn_vocab);
        btnAddWord = view.findViewById(R.id.btn_add);
        btnPlay = view.findViewById(R.id.btn_play);
        btnTrack = view.findViewById(R.id.btn_track);
        btnTrackList = view.findViewById(R.id.btn_track_list);
        btnSetting = view.findViewById(R.id.btn_setting);
        Context ctx = getContext();
        tts = new TextToSpeech(ctx, this);
        TrackRepo trackRepo = new TrackRepo(ctx);
        List<Track> tracks = null;
        try {
            tracks = trackRepo.findAllTrack();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        buttonTest1.setOnClickListener(v -> {
//            TestTTS testTTS = new TestTTS(ctx);
//            WordObjRepo repo = new WordObjRepo(ctx);
//            try {
//                System.out.println("SSSSS " + repo.findWordsByTrackName("first").get(0).fileNames);
//                File dir = ctx.getFilesDir();
//                File file = new File(dir, "look2.wav");
//                System.out.println("RR " + file.exists());
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }

            //        testTTS.ttsToFiles(null, tts, getContext());
       //     testTTS.addFiles(getContext());
            new TestTTS1(ctx).testTts();

        });
        buttonTest2.setOnClickListener(v -> {
         //   TestTTS1 testTTS = new TestTTS1(ctx);
            WordObjRepo repo = new WordObjRepo(ctx);
            try {
                WordObj w = repo.findWordObjByWord("resume");
                System.out.println("XX " + w.word.fileNames);
                System.out.println("XX2 " + w.word.trackName);
                WordObj w1 = repo.findWordObjByWord("lock");
                System.out.println("XX " + w1.word.fileNames);
                System.out.println("XX2 " + w1.word.trackName);
                WordObj w2 = repo.findWordObjByWord("simple");
                System.out.println("XX " + w2.word.fileNames);
                System.out.println("XX2 " + w2.word.trackName);
                WordObj w3 = repo.findWordObjByWord("look");
                System.out.println("XX " + w3.word.fileNames);
                System.out.println("XX2 " + w3.word.trackName);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

      //      testTTS.testExoPlayer();
        });

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

        btnSetting.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, setFrgm)
                    .commit();
        });

        List<Track> finalTracks = tracks;
        btnTrack.setOnClickListener(v -> {
            if (finalTracks.size() == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog
                        .setMessage("Не найдено ни одного трека")
                        .create().show();
            } else {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, trackFrgm)
                        .commit();
            }
        });

        btnTrackList.setOnClickListener(v -> {
                    if (finalTracks.size() == 0) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                        dialog
                                .setMessage("Не найдено ни одного трека")
                                .create().show();
                    } else {
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_act, TrackListFragment.newInstance(finalTracks))
                                .commit();
                    }
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
            if (finalTracks.size() == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog
                        .setMessage("Не найдено ни одного трека")
                        .create().show();
            } else {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, PlayFragment.newInstance(finalTracks))
                        .commit();
            }

        });


        return view;
    }



    @Override
    public void onInit(int status) {

    }
}