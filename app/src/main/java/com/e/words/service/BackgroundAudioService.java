package com.e.words.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;

import com.e.words.R;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Word;
import com.e.words.worker.PlayWorker;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BackgroundAudioService /*extends MediaBrowserServiceCompat*/ {

 //   private MediaPlayer mMediaPlayer;
//    private MediaSessionCompat mMediaSessionCompat;
//    private SimpleExoPlayer player;
//    private StyledPlayerView playerView;
//    private boolean playWhenReady = false;
//    private int currentWindow = 0;
//    private long playbackPosition = 0;
//    private Context ctx;
//
//
//    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if( player != null && player.isPlaying() ) {
//                player.pause();
//            }
//        }
//    };
//
//    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {
//
//        @Override
//        public void onPlay() {
//            super.onPlay();
//        }
//
//        @Override
//        public void onPause() {
//            super.onPause();
//        }
//
//        @Override
//        public void onPlayFromMediaId(String mediaId, Bundle extras) {
//            super.onPlayFromMediaId(mediaId, extras);
//        }
//    };
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        MediaButtonReceiver.handleIntent(mMediaSessionCompat, intent);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        ctx = getApplicationContext();
//        initMediaPlayer();
//        initMediaSession();
//        initNoisyReceiver();
//    }
//
//    private void initMediaPlayer() {
//        player = new SimpleExoPlayer.Builder(ctx).build();
//        playerView.setPlayer(player);
//        player.setWakeMode(PowerManager.PARTIAL_WAKE_LOCK);
//        player.setPlayWhenReady(playWhenReady);
//        player.seekTo(currentWindow, playbackPosition);
//        player.setRepeatMode(Player.REPEAT_MODE_ALL);
//        addFilesToPlayer();
//        player.prepare();
//    }
//
//
//    private void initMediaSession() {
//        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
//        mMediaSessionCompat = new MediaSessionCompat(getApplicationContext(), "SessionCompat", mediaButtonReceiver, null);
//
//        mMediaSessionCompat.setCallback(mMediaSessionCallback);
//        mMediaSessionCompat.setFlags( MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
//                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );
//
//        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
//        mMediaSessionCompat.setMediaButtonReceiver(pendingIntent);
//
//        setSessionToken(mMediaSessionCompat.getSessionToken());
//    }
//
//
//    private void initNoisyReceiver() {
//        //Handles headphones coming unplugged.
//        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
//        registerReceiver(mNoisyReceiver, filter);
//    }
//
//
//    @Override
//    public void onAudioFocusChange(int focusChange) {
//        switch( focusChange ) {
//            case AudioManager.AUDIOFOCUS_LOSS: {
//                if( mMediaPlayer.isPlaying() ) {
//                    mMediaPlayer.stop();
//                }
//                break;
//            }
//            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: {
//                mMediaPlayer.pause();
//                break;
//            }
//            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
//                if( mMediaPlayer != null ) {
//                    mMediaPlayer.setVolume(0.3f, 0.3f);
//                }
//                break;
//            }
//            case AudioManager.AUDIOFOCUS_GAIN: {
//                if( mMediaPlayer != null ) {
//                    if( !mMediaPlayer.isPlaying() ) {
//                        mMediaPlayer.start();
//                    }
//                    mMediaPlayer.setVolume(1.0f, 1.0f);
//                }
//                break;
//            }
//        }
//    }
//
//    private void releasePlayer() {
//        if (player != null) {
//            playWhenReady = player.getPlayWhenReady();
//            playbackPosition = player.getCurrentPosition();
//            currentWindow = player.getCurrentWindowIndex();
//            player.release();
//            player = null;
//        }
//    }
//
//    private void addFilesToPlayer(List<Word> words) {
//        AppProperty props = AppProperty.getInstance(ctx);
//      //  try {
//     //       List<Word> words = wordRepo.findWordsByTrackName(selectedTrack.name);
//            for (Word word : words) {
//                addWordFiles(word, props);
//            }
////        } catch (ExecutionException | InterruptedException e) {
////            e.printStackTrace();
////        }
//    }
//
//    private void addWordFiles(Word word, AppProperty props) {
//        File dir = ctx.getFilesDir();
//        String[] names = word.fileNames.split(";;");
//        for (int i = 0; i < props.countRepeat; i++) {
//            player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[0]))));
//            Uri uri = PlayWorker.getSilentUri(props.wordPause);
//            player.addMediaItem(MediaItem.fromUri(uri));
//        }
//        for (int i = 1; i < names.length; i++) {
//            if ("Silent".equalsIgnoreCase(names[i])) {
//                Uri uri = PlayWorker.getSilentUri(props.translPause);
//                player.addMediaItem(MediaItem.fromUri(uri));
//            } else {
//                if (names[i].equals("Silent")) {
//                    continue;
//                }
//                player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[i]))));
//            }
//        }
//    }
//
//
//    @Nullable
//    @Override
//    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
//        if(TextUtils.equals(clientPackageName, getPackageName())) {
//            return new BrowserRoot(getString(R.string.app_name), null);
//        }
//
//        return null;
//    }
//
//    //Not important for general audio service, required for class
//    @Override
//    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
//        result.sendResult(null);
//    }
}
