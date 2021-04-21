package com.e.words.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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

import androidx.fragment.app.Fragment;

import com.e.words.MainActivity;
import com.e.words.R;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.service.PlayerService;
import com.e.words.service.TrackHelper;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.content.Context.BIND_AUTO_CREATE;

public class PlayFragmentNew extends Fragment {
    private List<Track> tracks;
    private List<Word> words;
    private TrackRepo trackRepo;
    private MainFragment mainFrgm;
    private ImageButton btnPlay;
    private ImageButton btnStop;
    private ImageButton btnPause;
    private ImageButton btnSkipToPrevious;
    private ImageButton btnSkipToNext;
    private int lastTrackPosition;
    private int oldTrackPosition;
    private boolean isChangeTrack;
    private Track selectedTrack;
    private Track oldTrack;
    private WordObjRepo wordRepo;
    private TextToSpeech tts;
    private Context ctx;
    private SimpleExoPlayer player;
    private StyledPlayerView playerView;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String currentTrackName;


    private PlayerService.PlayerServiceBinder playerServiceBinder;
    private MediaControllerCompat mediaController;
    private MediaControllerCompat.Callback callback;
    private ServiceConnection serviceConnection;
    private PlayerService playerService;

    private static final String TRACK_PARAM = "tracks";
    private final String TAG = "DEBUG " + getClass().getSimpleName();

    public PlayFragmentNew() {
    }

    public static PlayFragmentNew newInstance(List<Track> tracks) {
        PlayFragmentNew fragment = new PlayFragmentNew();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ctx = getContext();
        wordRepo = new WordObjRepo(ctx);
        trackRepo = new TrackRepo(ctx);
        mainFrgm = new MainFragment();
        playerService = new PlayerService();
//        getLastTrack();
//        try {
//            words = wordRepo.findWordsByTrackName(selectedTrack.name);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }


        TrackHelper.allTrack = tracks;
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).isLast) {
                selectedTrack = tracks.get(i);

                lastTrackPosition = i;
                //               oldTrackPosition = i;
                break;
            }
        }
        if (selectedTrack == null) {
            selectedTrack = tracks.get(0);
        }
        TrackHelper.currentTrack = selectedTrack;
        oldTrack = selectedTrack;
        Spinner spinner = view.findViewById(R.id.spinner_play);
        ArrayAdapter<Track> adapter = new ArrayAdapter<>(Objects.requireNonNull(ctx),
                android.R.layout.simple_spinner_item, Objects.requireNonNull(tracks));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(lastTrackPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastTrackPosition = position;
                selectedTrack = tracks.get(lastTrackPosition);
                TrackHelper.currentTrack = selectedTrack;
 //               playerService.mediaSessionCallback.onRewind();
//                releasePlayer();
//                getWordObjList(selectedTrack);
//                initializePlayer();


                if (mediaController != null) {
                    mediaController.getTransportControls().rewind();
         //           mediaController.getTransportControls().play();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPlay = view.findViewById(R.id.btn_play);
        btnPause = view.findViewById(R.id.btn_pause);
        btnStop = view.findViewById(R.id.btn_stop);
        btnSkipToNext = view.findViewById(R.id.btn_next);
        btnSkipToPrevious = view.findViewById(R.id.btn_previous);

        initService();

        btnPlay.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().play();
        });
        btnPause.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().pause();
        });
        btnStop.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().stop();
        });
        btnSkipToPrevious.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().skipToPrevious();
        });
        btnSkipToNext.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().skipToNext();
        });

        return view;
    }

    private void initService() {
        PlayerService.words = this.words;
        callback = new MediaControllerCompat.Callback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {
                if (state == null)
                    return;
                boolean playing = state.getState() == PlaybackStateCompat.STATE_PLAYING;
                btnPlay.setEnabled(!playing);
                btnPause.setEnabled(playing);
                btnStop.setEnabled(playing);
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerServiceBinder = (PlayerService.PlayerServiceBinder) service;
                try {
                    mediaController = new MediaControllerCompat(ctx, playerServiceBinder.getMediaSessionToken());
                    mediaController.registerCallback(callback);
                    callback.onPlaybackStateChanged(mediaController.getPlaybackState());
                } catch (RemoteException e) {
                    mediaController = null;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerServiceBinder = null;
                if (mediaController != null) {
                    mediaController.unregisterCallback(callback);
                    mediaController = null;
                }
            }
        };

        ctx.bindService(new Intent(ctx, PlayerService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeService();
        selectedTrack = TrackHelper.getCurrentTrack();
        if (!oldTrack.equals(selectedTrack)) {
//            Track old = tracks.get(oldTrackPosition);
//            Track last = tracks.get(lastTrackPosition);
            oldTrack.isLast = false;
            selectedTrack.isLast = true;
            trackRepo.updateTrack(oldTrack);
            trackRepo.updateTrack(selectedTrack);
        }
    }

    private void closeService() {
        playerServiceBinder = null;
        if (mediaController != null) {
            mediaController.unregisterCallback(callback);
            mediaController = null;
        }
        ctx.unbindService(serviceConnection);
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
