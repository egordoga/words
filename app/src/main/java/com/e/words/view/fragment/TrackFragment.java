package com.e.words.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.view.adapter.TrackAdapter;
import com.e.words.entity.Track;
import com.e.words.entity.Word;
import com.e.words.view.menu.MenuMain;
import com.e.words.db.repository.TrackRepo;
import com.e.words.db.repository.WordObjRepo;
import com.e.words.service.TrackHelper;
import com.e.words.util.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.e.words.R.*;

public class TrackFragment extends Fragment implements TrackAdapter.ItemClickListener{

    private TrackRepo trackRepo;
    private RecyclerView rvTrackWords;
    private TrackAdapter trackAdapter;
    private List<Track> trackList;
    private MainFragment mainFrgm;
    private Button btnDelFromTrack;
    private ImageButton btnPlay;
    private Context ctx;
    private List<Word> checkedWords;
    private List<Word> wordList;
    private Track currentTrack;
    private int currentPosition;

    public TrackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_track, container, false);
        Spinner trackSpin = view.findViewById(id.spinner_track);
        ctx = getContext();
        rvTrackWords = view.findViewById(id.rv_track_words);
        rvTrackWords.setLayoutManager(new LinearLayoutManager(ctx));
        trackRepo = new TrackRepo(ctx);
        checkedWords = new ArrayList<>();
        trackAdapter = new TrackAdapter(checkedWords, ctx, this);
        trackList = getAllTrack();
        btnDelFromTrack = view.findViewById(R.id.btn_del_from_track);
        btnPlay = view.findViewById(id.btn_play_track);
        btnDelFromTrack.setEnabled(false);
        currentTrack = trackList.get(0);
        currentPosition = 0;
        try {
            wordList = trackRepo.findWordsFromTrack(currentTrack.id);
            trackAdapter.loadItems(wordList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        rvTrackWords.setAdapter(trackAdapter);
        ArrayAdapter<Track> spinAdapter = new ArrayAdapter<>(Objects.requireNonNull(ctx),
                android.R.layout.simple_spinner_item, trackList);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.notifyDataSetChanged();
        trackSpin.setAdapter(spinAdapter);
        trackSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkedWords.clear();
                currentTrack = trackList.get(position);
                currentPosition = position;
                try {
                    wordList = trackRepo.findWordsFromTrack(currentTrack.id);
                    trackAdapter.loadItems(wordList);
                    rvTrackWords.setAdapter(trackAdapter);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDelFromTrack.setOnClickListener(v -> {
            for (Word checkedWord : checkedWords) {
                checkedWord.trackId = null;
                checkedWord.fileNames = null;
                new FileWorker().deleteFiles(checkedWord.word, true, ctx);
            }
            List<Word> temp = new ArrayList<>(checkedWords);
            new WordObjRepo(ctx).updateWords(temp);
            if (wordList.size() == checkedWords.size()) {
                trackList.remove(currentTrack);
                spinAdapter.remove(currentTrack);
                TrackHelper.allTrack.remove(currentTrack);


                trackRepo.deleteTrack(currentTrack);
            }
            trackAdapter.deleteChecked();
        });
        btnPlay.setOnClickListener(v -> {
            PlayFragment playFrgm = PlayFragment.newInstance(trackList, currentTrack);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_act, playFrgm)
                    .commit();
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Track> getAllTrack() {
        try {
            return trackRepo.findAllTrack();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void onClick(View view, int position) {
        btnDelFromTrack.setEnabled(checkedWords.size() != 0);
    }

    private int getNextPosition(int position) {
        if (++position == trackList.size() - 1) {
            return 0;
        } else {
            return ++position;
        }
    }
}