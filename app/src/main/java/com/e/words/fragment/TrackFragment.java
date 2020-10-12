package com.e.words.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordWithId;
import com.e.words.adapter.TrackAdapter;
import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackFragment extends Fragment {

    private Spinner trackSpin;
    private TrackRepo trackRepo;
    private RecyclerView rvTrackWords;
    private TrackAdapter trackAdapter;
    private List<Track> trackList;
    private List<WordWithId> wordList;
    private MainFragment mainFrgm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackFragment newInstance(String param1, String param2) {
        TrackFragment fragment = new TrackFragment();
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
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        trackSpin = view.findViewById(R.id.spinner_track);
        rvTrackWords = view.findViewById(R.id.rv_track_words);
        rvTrackWords.setLayoutManager(new LinearLayoutManager(getContext()));
        trackRepo = new TrackRepo(getContext());
        trackAdapter = new TrackAdapter(getContext());
        trackList = getAllTrack();
        Track track = trackList.get(0);
        try {
            wordList = trackRepo.findWordWithIdFromTrack(track.id);
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
        trackSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Track track = trackList.get(position);
                try {
                    trackAdapter.loadItems(trackRepo.findWordWithIdFromTrack(track.id));
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