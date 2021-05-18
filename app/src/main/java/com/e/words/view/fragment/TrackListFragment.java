package com.e.words.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.entity.dto.TrackWithWords;
import com.e.words.view.adapter.TrackListAdapter;
import com.e.words.entity.Track;
import com.e.words.entity.Word;
import com.e.words.view.menu.MenuMain;
import com.e.words.db.repository.TrackRepo;
import com.e.words.db.repository.WordObjRepo;
import com.e.words.util.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TrackListFragment extends Fragment implements TrackListAdapter.ItemClickListener {

    private MainFragment mainFrgm;
    private PlayFragment playFrgm;
    private Context ctx;
    private static final String TRACKS = "tracks";
    private List<TrackWithWords> tracks;
    private TrackListAdapter adapter;

    public TrackListFragment() {
    }

    public static TrackListFragment newInstance(List<TrackWithWords> tracks) {
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
            tracks = (List<TrackWithWords>) getArguments().getSerializable(TRACKS);
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
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new MenuMain(getActivity()).getMain(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        PopupMenu pMenu = new PopupMenu(ctx, view);
        pMenu.inflate(R.menu.menu_track_list);
        pMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.act_play_track:
                    playFrgm = PlayFragment.newInstance(getTrackList(tracks), tracks.get(position).track);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, playFrgm)
                            .commit();
                    return true;
                case R.id.act_del_track:
                    //  Track track = tracks.get(position).track;
                    TrackWithWords trackWw = tracks.get(position);
                    WordObjRepo repo = new WordObjRepo(ctx);
                    //  List<Word> words = repo.findWordsByTrackName(trackWw.name);
                    FileWorker worker = new FileWorker();
                    for (Word word : trackWw.wordList) {
//                        String[] fileNames = word.fileNames.split(";;");
//                        worker.deleteTranslateFiles(fileNames, ctx);
                        worker.deleteFiles(word.word, true, ctx);
                        word.trackId = null;
                    }
                    repo.updateWords(trackWw.wordList);
                    new TrackRepo(ctx).deleteTrack(trackWw);
                    adapter.deleteItem(position);
                    return true;
                case R.id.act_rename_track:
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Выберите трек")
//                            .setItems(getTrackNames(), (dialog, which) -> {
//                                if (which == 0) {
                    AlertDialog.Builder adName = new AlertDialog.Builder(ctx);
                //    adName.setTitle("Выберите трек");
                    LayoutInflater li = LayoutInflater.from(ctx);
                    View nameView = li.inflate(R.layout.track_name_alert, null);
                    adName.setView(nameView);
                    final EditText etName = nameView.findViewById(R.id.et_track_name);
                    adName
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog1, id) -> {
                                //                        tts = new TextToSpeech(ctx, status -> {
                                String trackName = etName.getText().toString().trim();
                                TrackRepo trackRepo = new TrackRepo(ctx);
                                try {
                                    Track track = trackRepo.findTrackByName(trackName);
                                    if (track != null) {
                                        AlertDialog.Builder trackPresent = new AlertDialog.Builder(ctx);
                                        trackPresent
                                                .setTitle("Предупреждение")
                                                .setMessage("Такое имя уже есть в списке треков")
                                                .create()
                                                .show();
                                    } else {
                                        TrackWithWords trackUpd = tracks.get(position);
                                        trackUpd.track.name = trackName;
                                        adapter.setItem(tracks);
                                        trackRepo.updateTrack(trackUpd.track);
                                    }
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                    adName.create();
                    adName.show();

                    return true;
            }
            return super.onOptionsItemSelected(item);
        });
        pMenu.show();
    }

    private List<Track> getTrackList(List<TrackWithWords> trackWwList) {
        List<Track> tracks = new ArrayList<>();
        for (TrackWithWords trackWw : trackWwList) {
            tracks.add(trackWw.track);
        }
        return tracks;
    }
}