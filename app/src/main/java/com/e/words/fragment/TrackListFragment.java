package com.e.words.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.adapter.TrackListAdapter;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TrackListFragment extends Fragment implements TrackListAdapter.ItemClickListener {

    private MainFragment mainFrgm;
    private PlayFragment playFrgm;
    private Context ctx;
    private static final String TRACKS = "tracks";
    private List<Track> tracks;
    private TrackListAdapter adapter;

    public TrackListFragment() {
    }

    public static TrackListFragment newInstance(List<Track> tracks) {
        TrackListFragment fragment = new TrackListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRACKS, (Serializable) tracks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tracks = (List<Track>) getArguments().getSerializable(TRACKS);
        }
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);
        ctx = getContext();
        RecyclerView rvTrackList = view.findViewById(R.id.rv_track_list);
        rvTrackList.setLayoutManager(new LinearLayoutManager(ctx));
        adapter = new TrackListAdapter(ctx, this);
        adapter.setItem(tracks);
        rvTrackList.setAdapter(adapter);
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

    @Override
    public void onClick(View view, int position) {
        PopupMenu pMenu = new PopupMenu(ctx, view);
        pMenu.inflate(R.menu.menu_track_list);
        pMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.act_play_track:
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, playFrgm)
                            .commit();
                    return true;
                case R.id.act_del_track:
                    Track track = tracks.get(position);
                    WordObjRepo repo = new WordObjRepo(ctx);
                    try {
                        List<Word> words = repo.findWordsByTrackName(track.name);
                        FileWorker worker = new FileWorker();
                        for (Word word : words) {
                            String[] names = word.fileNames.split(";;");
                            worker.deleteTranslateFiles(names, ctx);
                        }
                        new TrackRepo(ctx).deleteTrack(track);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    adapter.deleteItem(position);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        });
        pMenu.show();
    }
}