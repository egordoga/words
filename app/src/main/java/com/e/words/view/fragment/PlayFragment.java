package com.e.words.view.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.entity.Track;
import com.e.words.view.menu.MenuMain;
import com.e.words.service.PlayerService;
import com.e.words.service.TrackHelper;
import com.e.words.util.Util;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class PlayFragment extends Fragment implements Util.CallSpin {
    private List<Track> tracks;
    private ImageButton btnPlay;
    private ImageButton btnStop;
    private ImageButton btnPause;
    private int lastTrackPosition;
    private Track selectedTrack;
    private Context ctx;
    private TextView tvPlayTrack;

    private PlayerService.PlayerServiceBinder playerServiceBinder;
    private MediaControllerCompat mediaController;
    private MediaControllerCompat.Callback callback;
    private ServiceConnection serviceConnection;

    private static final String TRACKS_PARAM = "tracks";
    private static final String SEL_TRACK = "selected";
    private static boolean isSelectedPresent = false;

    public PlayFragment() {
    }

    public static PlayFragment newInstance(List<Track> tracks, Track selected) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRACKS_PARAM, (Serializable) tracks);
        if (selected != null) {
            args.putSerializable(SEL_TRACK, selected);
            isSelectedPresent = true;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tracks = (List<Track>) getArguments().getSerializable(TRACKS_PARAM);
            if (isSelectedPresent) {
                selectedTrack = (Track) getArguments().getSerializable(SEL_TRACK);
            }
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ctx = getContext();
        TrackHelper.allTrack = tracks;
        if (isSelectedPresent) {
            for (int i = 0; i < tracks.size(); i++) {
                if (tracks.get(i).equals(selectedTrack)) {
                    lastTrackPosition = i;
                    TrackHelper.position = i;
                    TrackHelper.currentTrack = selectedTrack;
                    break;
                }
            }
        }

        tvPlayTrack = view.findViewById(R.id.tv_play_track);
        ImageButton btnChoiceTrack = view.findViewById(R.id.btn_choice_track);
        selectedTrack = TrackHelper.getCurrentTrack();
        lastTrackPosition = TrackHelper.position;
        tvPlayTrack.setText(selectedTrack.name);
        TrackHelper.fragPlay = this;

        btnPlay = view.findViewById(R.id.btn_play);
        btnPause = view.findViewById(R.id.btn_pause);
        btnStop = view.findViewById(R.id.btn_stop);
        ImageButton btnSkipToNext = view.findViewById(R.id.btn_next);
        ImageButton btnSkipToPrevious = view.findViewById(R.id.btn_previous);

        initService();

        btnPlay.setOnClickListener(v -> {
            if (mediaController != null) {
                if (isSelectedPresent) {
                    mediaController.getTransportControls().rewind();
                } else {
                    mediaController.getTransportControls().play();
                }
            }
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

        btnChoiceTrack.setOnClickListener(v -> {
            String[] names = tracks.stream().map(track -> track.name).toArray(String[]::new);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Выберите трек")
                    .setItems(names, (dialog, which) -> {
                        selectedTrack = tracks.get(which);
                        lastTrackPosition = which;
                        TrackHelper.currentTrack = selectedTrack;
                        TrackHelper.position = lastTrackPosition;
                        tvPlayTrack.setText(selectedTrack.name);
                        if (mediaController != null) {
                            mediaController.getTransportControls().rewind();
                        }
                    });
            builder.create();
            builder.show();
        });
        return view;
    }

    private void initService() {
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
                mediaController = new MediaControllerCompat(ctx, playerServiceBinder.getMediaSessionToken());
                mediaController.registerCallback(callback);
                callback.onPlaybackStateChanged(mediaController.getPlaybackState());
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

    @Override
    public void refreshSpin(int position) {
        tvPlayTrack.setText(tracks.get(position).name);
    }
}
