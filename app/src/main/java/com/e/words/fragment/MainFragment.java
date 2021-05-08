package com.e.words.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.TrackWithWords;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.temp.TestTTS1;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainFragment extends Fragment {

    private ArticleFragment af;
    private WordFragment wf;
    private AddWordFragment awf;
    private PlayFragment pf;
    private PlayFragmentNew pfn;
    private VocabularyFragment vocabFrgm;
    private TrackFragment trackFrgm;
    private SettingFragment setFrgm;
    private TextToSpeech tts;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wf = new WordFragment();
        awf = new AddWordFragment();
        pf = new PlayFragment();
        pfn = new PlayFragmentNew();
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
        Button buttonTest1 = view.findViewById(R.id.btn_test_frgm);
        Button buttonTest2 = view.findViewById(R.id.btn_test2);
        Button btnVocab = view.findViewById(R.id.btn_vocab);
        Button btnAddWord = view.findViewById(R.id.btn_add);
        Button btnPlay = view.findViewById(R.id.btn_play);
        Button btnTrack = view.findViewById(R.id.btn_track);
        Button btnTrackList = view.findViewById(R.id.btn_track_list);
        Button btnSetting = view.findViewById(R.id.btn_setting);
        Context ctx = getContext();
        TrackRepo trackRepo = new TrackRepo(ctx);
        List<TrackWithWords> tracks = null;
        try {
            tracks = trackRepo.findAllTrackWithWords();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        List<TrackWithWords> finalTracks = tracks;
//        buttonTest1.setOnClickListener(v -> {
//            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_act, PlayFragmentNew.newInstance(finalTracks))
//                    .commit();
//
//        });
        buttonTest2.setOnClickListener(v -> {
            //   TestTTS1 testTTS = new TestTTS1(ctx);
            WordObjRepo repo = new WordObjRepo(ctx);
            try {
                List<WordObj> trackList = repo.findAllWordObj();
                trackList.forEach(System.out::println);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
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


//        btnPlay.setOnClickListener(v -> {
//            if (finalTracks.size() == 0) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
//                dialog
//                        .setMessage("Не найдено ни одного трека")
//                        .create().show();
//            } else {
//                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.main_act, PlayFragment.newInstance(finalTracks))
//                        .commit();
//            }
//
//        });


        return view;
    }
}