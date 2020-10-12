package com.e.words.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.temp.TestTTS;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment implements TextToSpeech.OnInitListener{

    private Spinner spinner;
    private List<Track> tracks;
    private TrackRepo trackRepo;
    private MainFragment mainFrgm;
    private ImageButton btnPlay;
    private ImageButton btnStop;
    private int lastTrackPosition;
    private boolean isChangeTrack;
    private Track selectedTrack;
    private List<WordObj> wordObjList;
    private WordObjRepo wordRepo;
    private TextToSpeech tts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
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
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        tts = new TextToSpeech(getActivity(), this);
        btnPlay = view.findViewById(R.id.btn_play);
        btnStop = view.findViewById(R.id.btn_stop);
        trackRepo = new TrackRepo(getContext());
        wordRepo = new WordObjRepo(getContext());
        mainFrgm = new MainFragment();
        spinner = view.findViewById(R.id.spinner_play);
        ArrayAdapter<Track> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, Objects.requireNonNull(getAllTrack()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(lastTrackPosition);
        selectedTrack = tracks.get(lastTrackPosition);
        getWordObjList(selectedTrack);
        TestTTS testTTS = new TestTTS();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tracks.get(lastTrackPosition).isLast = false;
                lastTrackPosition = position;
                selectedTrack = tracks.get(lastTrackPosition);
                selectedTrack.isLast = true;
                isChangeTrack = true;
                getWordObjList(selectedTrack);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnPlay.setOnClickListener(v -> {
            btnPlay.setImageResource(R.drawable.btn_blue_pause);
            for (WordObj wordObj : wordObjList) {
                testTTS.playWordObj(wordObj, tts);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_return, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.act_return_main:
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, mainFrgm)
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Track> getAllTrack() {
        try {
            tracks = trackRepo.findAllTrack();
            for (int i = 0; i < tracks.size(); i++) {
                if (tracks.get(i).isLast) {
                    lastTrackPosition = i;
                    break;
                }
            }
            return tracks;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWordObjList(Track track) {
        String[] ids = track.wordIds.split(";;");
        try {
            wordObjList = wordRepo.findAllWordByIds(ids);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int status) {

    }
}