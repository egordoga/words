package com.e.words.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.worker.PlayWorker;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PlayFragment extends Fragment {

    private List<Track> tracks;
    private TrackRepo trackRepo;
    private MainFragment mainFrgm;
    private ImageButton btnPlay;
    private ImageButton btnStop;
    private ImageButton btnPause;
    private int lastTrackPosition;
    private int oldTrackPosition;
    private boolean isChangeTrack;
    private Track selectedTrack;
    private WordObjRepo wordRepo;
    private TextToSpeech tts;
    private Context ctx;
    private SimpleExoPlayer player;
    private StyledPlayerView playerView;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private static final String TRACK_PARAM = "tracks";
    private final String TAG = "DEBUG " + getClass().getSimpleName();

    public PlayFragment() {
    }

    public static PlayFragment newInstance(List<Track> tracks) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRACK_PARAM, (Serializable) tracks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tracks = (List<Track>) getArguments().getSerializable(TRACK_PARAM);
        }
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        mainFrgm = new MainFragment();

        playerView = view.findViewById(R.id.player_view);
        ctx = getContext();
        wordRepo = new WordObjRepo(ctx);
        trackRepo = new TrackRepo(getContext());
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).isLast) {
                selectedTrack = tracks.get(i);
                lastTrackPosition = i;
                oldTrackPosition = i;
                break;
            }
        }

        if (selectedTrack == null) {
            selectedTrack = tracks.get(0);
        }
        Spinner spinner = view.findViewById(R.id.spinner_play);
        ArrayAdapter<Track> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, Objects.requireNonNull(tracks));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(lastTrackPosition);
        getWordObjList(selectedTrack);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastTrackPosition = position;
                selectedTrack = tracks.get(lastTrackPosition);
                releasePlayer();
                getWordObjList(selectedTrack);
                initializePlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
        if (oldTrackPosition != lastTrackPosition) {
            Track old = tracks.get(oldTrackPosition);
            Track last = tracks.get(lastTrackPosition);
            old.isLast = false;
            last.isLast = true;
            trackRepo.updateTrack(old);
            trackRepo.updateTrack(last);
        }
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

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void getAllTrack() {
//        try {
//            tracks = trackRepo.findAllTrack();
//            for (int i = 0; i < tracks.size(); i++) {
//                if (tracks.get(i).isLast) {
//                    lastTrackPosition = i;
//                    break;
//                }
//            }
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWordObjList(Track track) {
        String[] words = track.words.split(";;");
        try {
            List<WordObj> wordObjList = wordRepo.findAllWordByWords(words);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(ctx).build();
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        addFilesToPlayer();
        player.prepare();
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    private void addFilesToPlayer() {
        AppProperty props = AppProperty.getInstance(ctx);
        try {
            List<Word> words = wordRepo.findWordsByTrackName(selectedTrack.name);
            for (Word word : words) {
                addWordFiles(word, props);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addWordFiles(Word word, AppProperty props) {
        Log.d(TAG, word.fileNames);
        File dir = ctx.getFilesDir();
        String[] names = word.fileNames.split(";;");
        for (int i = 0; i < props.countRepeat; i++) {
            player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[0]))));
            Uri uri = PlayWorker.getSilentUri(props.wordPause);
            player.addMediaItem(MediaItem.fromUri(uri));
        }
        for (int i = 1; i < names.length; i++) {
            if ("Silent".equalsIgnoreCase(names[i])) {
                Uri uri = PlayWorker.getSilentUri(props.translPause);
                player.addMediaItem(MediaItem.fromUri(uri));
            } else {
                if (names[i].equals("Silent")) {
                    continue;
                }
                player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[i]))));
            }
        }
    }
}