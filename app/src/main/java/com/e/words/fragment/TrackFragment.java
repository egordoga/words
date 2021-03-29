package com.e.words.fragment;

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
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.adapter.TrackAdapter;
import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TrackFragment extends Fragment {

    private TrackRepo trackRepo;
    private RecyclerView rvTrackWords;
    private TrackAdapter trackAdapter;
    private List<Track> trackList;
    private MainFragment mainFrgm;

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
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        Spinner trackSpin = view.findViewById(R.id.spinner_track);
        rvTrackWords = view.findViewById(R.id.rv_track_words);
        rvTrackWords.setLayoutManager(new LinearLayoutManager(getContext()));
        trackRepo = new TrackRepo(getContext());
        trackAdapter = new TrackAdapter(getContext());
        trackList = getAllTrack();
        Track track = trackList.get(0);
        try {
            List<String> wordList = trackRepo.findWordsFromTrack(track.id);
            trackAdapter.loadItems(wordList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        rvTrackWords.setAdapter(trackAdapter);
        ArrayAdapter<Track> spinAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, trackList);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.notifyDataSetChanged();
        trackSpin.setAdapter(spinAdapter);
        trackSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Track track = trackList.get(position);
                try {
                    trackAdapter.loadItems(trackRepo.findWordsFromTrack(track.id));
                    rvTrackWords.setAdapter(trackAdapter);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
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
}