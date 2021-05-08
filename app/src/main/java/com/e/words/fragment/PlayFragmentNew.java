package com.e.words.fragment;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.menu.MenuMain;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.service.PlayerService;
import com.e.words.service.TrackHelper;
import com.e.words.util.Util;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.content.Context.BIND_AUTO_CREATE;

public class PlayFragmentNew extends Fragment implements Util.CallSpin {
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
//    private TrackHelper trackHelper;

//    @SuppressLint("StaticFieldLeak")
//    public static PlayFragmentNew instance;
    public Spinner spinner;

    private TextView tvPlayTrack;
    private Button btnChoiceTrack;


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


        System.out.println("ON CREATE--------------------------------------------------");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);


        System.out.println("ON CREATE VIEW---------------------------------------");


        ctx = getContext();
        wordRepo = new WordObjRepo(ctx);
        trackRepo = new TrackRepo(ctx);
        mainFrgm = new MainFragment();
        playerService = new PlayerService();
 //       instance = this;
//        trackHelper = new TrackHelper();
//        getLastTrack();
//        try {
//            words = wordRepo.findWordsByTrackName(selectedTrack.name);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }


        TrackHelper.allTrack = tracks;
//        for (int i = 0; i < tracks.size(); i++) {
//            if (tracks.get(i).isLast) {
//                selectedTrack = tracks.get(i);
//
//                lastTrackPosition = i;
//                //               oldTrackPosition = i;
//                break;
//            }
//        }
//        if (selectedTrack == null) {
//            selectedTrack = tracks.get(0);
//        }
//        TrackHelper.currentTrack = selectedTrack;


        tvPlayTrack = view.findViewById(R.id.tv_play_track);
        btnChoiceTrack = view.findViewById(R.id.btn_choice_track);




        selectedTrack = TrackHelper.getCurrentTrack();
        lastTrackPosition = TrackHelper.position;
        oldTrack = selectedTrack;
        spinner = view.findViewById(R.id.spinner_play);
        ArrayAdapter<Track> adapter = new ArrayAdapter<>(Objects.requireNonNull(ctx),
                android.R.layout.simple_spinner_item, Objects.requireNonNull(tracks));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(lastTrackPosition, false);


      //  spinner.



        TrackHelper.fragPlay = this;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastTrackPosition = position;
                selectedTrack = tracks.get(lastTrackPosition);
                TrackHelper.currentTrack = selectedTrack;
                TrackHelper.position = position;
                playerService.changeTrack(selectedTrack);
//                releasePlayer();
//                getWordObjList(selectedTrack);
//                initializePlayer();





                if (mediaController != null) {

             //        mediaController.getTransportControls()..rewind();
           //         mediaController.getTransportControls().playFromMediaId("PlayerService", null);
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
//            lastTrackPosition = getNewLastTrackPosition(lastTrackPosition, false);
//            selectedTrack = tracks.get(lastTrackPosition);
//
//
//            System.out.println("YYY " + selectedTrack.name + "  " + lastTrackPosition);
//
//
//            spinner.setSelection(lastTrackPosition);
        });
        btnSkipToNext.setOnClickListener(v -> {
            if (mediaController != null)
                mediaController.getTransportControls().skipToNext();
//            lastTrackPosition = getNewLastTrackPosition(lastTrackPosition, true);
//            selectedTrack = tracks.get(lastTrackPosition);
//
//
//            System.out.println("MMM " + selectedTrack.name + "  " + lastTrackPosition);
//
//
//            spinner.setSelection(lastTrackPosition);
        });


        btnChoiceTrack.setOnClickListener(v -> {
         //   String[] names =
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Выберите трек")
                        .setItems((CharSequence[]) tracks.stream().map(track -> track.name).toArray(), (dialog, which) -> {
                    //        getTrackByName(which);
                            selectedTrack = tracks.get(which);
                            lastTrackPosition = which;
                            TrackHelper.currentTrack = selectedTrack;
                            TrackHelper.position = lastTrackPosition;
                            playerService.changeTrack(selectedTrack);
                            tvPlayTrack.setText(selectedTrack.name);
                        });
        });





        return view;
    }

    private void initService() {
      //  PlayerService.words = this.words;
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



        System.out.println("ON DESTROY");


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
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new MenuMain(getActivity()).getMain(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private int getNewLastTrackPosition(int position, boolean isNext) {
        if (isNext) {
            if (position == tracks.size() - 1) {
                return 0;
            } else {
                return ++position;
            }
        } else {
            if (position == 0) {
                return tracks.size() - 1;
            } else {
                return --position;
            }
        }
    }

    @Override
    public void refreshSpin(int position) {
     //   spinner.setSelection(position, false);
        tvPlayTrack.setText(tracks.get(position).name);
    }


    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ON PAUSE-------------------------------------------");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("ON STOP-----------------------------------------");
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        System.out.println("ON START---------------------------------------");
//        lastTrackPosition = TrackHelper.position;
//        selectedTrack = TrackHelper.getCurrentTrack();
//        spinner.setSelection(lastTrackPosition);
//    }

    private void getTrackByName(String name) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).name.equals(name)) {
                selectedTrack = tracks.get(i);
                lastTrackPosition = i;
                break;
            }
        }
    }
}
