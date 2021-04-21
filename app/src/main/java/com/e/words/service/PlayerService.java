package com.e.words.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.support.v4.media.MediaMetadataCompat;
import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.app.NotificationCompat;


import com.e.words.MainActivity;
import com.e.words.R;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.fragment.PlayFragmentNew;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.worker.PlayWorker;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;


import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;


final public class PlayerService extends Service {

    private final int NOTIFICATION_ID = 404;
    private final String NOTIFICATION_DEFAULT_CHANNEL_ID = "default_channel";

    private final MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder();

    private final PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder().setActions(
            PlaybackStateCompat.ACTION_PLAY
                    | PlaybackStateCompat.ACTION_STOP
                    | PlaybackStateCompat.ACTION_PAUSE
                    | PlaybackStateCompat.ACTION_PLAY_PAUSE
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
    );

    private MediaSessionCompat mediaSession;

    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private boolean audioFocusRequested = false;

    private SimpleExoPlayer exoPlayer;
    private ExtractorsFactory extractorsFactory;
    private DataSource.Factory dataSourceFactory;
    private Context ctx;

    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public static List<Word> words;
    private TrackRepo trackRepo;
    private WordObjRepo wordRepo;
    private List<Track> allTrack;


    private boolean isStop = false;

  //  public PlayerService(List<Word> words) {
//        this.words = words;
//    }


    //   private final MusicRepository musicRepository = new MusicRepository();

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_DEFAULT_CHANNEL_ID, getString(R.string.notification_channel_name), NotificationManagerCompat.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .setAcceptsDelayedFocusGain(false)
                    .setWillPauseWhenDucked(true)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mediaSession = new MediaSessionCompat(this, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(mediaSessionCallback);

        ctx = getApplicationContext();

     //   Intent activityIntent = new Intent(ctx, PlayFragmentNew.class);
        Intent activityIntent = new Intent(ctx, MainActivity.class);
        mediaSession.setSessionActivity(PendingIntent.getActivity(ctx, 0, activityIntent, 0));

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null, ctx, MediaButtonReceiver.class);
        mediaSession.setMediaButtonReceiver(PendingIntent.getBroadcast(ctx, 0, mediaButtonIntent, 0));

        exoPlayer = new SimpleExoPlayer.Builder(ctx).build();
        exoPlayer.addListener(exoPlayerListener);

        trackRepo = new TrackRepo(ctx);
        wordRepo = new WordObjRepo(ctx);



//        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
//
//        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(RADIO_URL))


//        DataSource.Factory httpDataSourceFactory = new OkHttpDataSourceFactory(new OkHttpClient(), Util.getUserAgent(this, getString(R.string.app_name)));
//        Cache cache = new SimpleCache(new File(this.getCacheDir().getAbsolutePath() + "/exoplayer"), new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100)); // 100 Mb max
//        this.dataSourceFactory = new CacheDataSourceFactory(cache, httpDataSourceFactory, CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
//        this.extractorsFactory = new DefaultExtractorsFactory();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
        releasePlayer();
    }

    public MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        private Track currentTrack;
        int currentState = PlaybackStateCompat.STATE_STOPPED;

        @Override
        public void onPlay() {
            if (!exoPlayer.getPlayWhenReady()) {
                startService(new Intent(ctx, PlayerService.class));

           //     MusicRepository.Track track = musicRepository.getCurrent();
                Track track = TrackHelper.getCurrentTrack();
                updateMetadataFromTrack(track);

           //     prepareToPlay(track.getUri());
                initializePlayer(track);

                if (!audioFocusRequested) {
                    audioFocusRequested = true;

                    int audioFocusResult;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        audioFocusResult = audioManager.requestAudioFocus(audioFocusRequest);
                    } else {
                        audioFocusResult = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    }
                    if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                        return;
                }

                mediaSession.setActive(true); // Сразу после получения фокуса

                registerReceiver(becomingNoisyReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));

                exoPlayer.setPlayWhenReady(true);
            }

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            currentState = PlaybackStateCompat.STATE_PLAYING;

            refreshNotificationAndForegroundStatus(currentState);
            isStop = false;
        }

        @Override
        public void onPause() {
            if (exoPlayer.getPlayWhenReady()) {
                exoPlayer.setPlayWhenReady(false);
                unregisterReceiver(becomingNoisyReceiver);
            }

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            currentState = PlaybackStateCompat.STATE_PAUSED;

            refreshNotificationAndForegroundStatus(currentState);
        }

        @Override
        public void onStop() {
            isStop = true;
            if (exoPlayer.getPlayWhenReady()) {
                exoPlayer.setPlayWhenReady(false);
                unregisterReceiver(becomingNoisyReceiver);
            }

            if (audioFocusRequested) {
                audioFocusRequested = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioManager.abandonAudioFocusRequest(audioFocusRequest);
                } else {
                    audioManager.abandonAudioFocus(audioFocusChangeListener);
                }
            }

            mediaSession.setActive(false);

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_STOPPED,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            currentState = PlaybackStateCompat.STATE_STOPPED;
            refreshNotificationAndForegroundStatus(currentState);

            stopSelf();
        }

        @Override
        public void onSkipToNext() {
            Track track = TrackHelper.getNext();
            changeTrack(track);
        }

        @Override
        public void onSkipToPrevious() {
            Track track = TrackHelper.getPrevious();
            changeTrack(track);
        }

        @Override
        public void onRewind() {
            Track track = TrackHelper.getCurrentTrack();
            changeTrack(track);
        }

        private void changeTrack(Track track) {
            exoPlayer.setPlayWhenReady(false);
            updateMetadataFromTrack(track);
            //       refreshNotificationAndForegroundStatus(currentState);
            //    releasePlayerWithoutNull();
            exoPlayer.removeMediaItems(0, exoPlayer.getMediaItemCount());
            onPlay();
            currentTrack = track;
        }

//        private void prepareToPlay(Uri uri) {
//            if (!uri.equals(currentUri)) {
//                currentUri = uri;
//                ExtractorMediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
//             //   ProgressiveMediaSource
//                exoPlayer.prepare(mediaSource);
//            }
//        }

        private void initializePlayer(Track track) {
//        player = new SimpleExoPlayer.Builder(ctx).build();
//        playerView.setPlayer(player);
            if (!track.equals(currentTrack) || isStop) {
                currentTrack = track;
                exoPlayer.setPlayWhenReady(playWhenReady);
                exoPlayer.seekTo(currentWindow, playbackPosition);
                exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
                addFilesToPlayer(track);
                exoPlayer.prepare();
                currentTrack = track;
            }
        }

        private void updateMetadataFromTrack(Track track) {
            //  metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ART, BitmapFactory.decodeResource(getResources(), track.getBitmapResId()));
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Title");
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Artist");
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Artist");
            //  metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 1000);
            mediaSession.setMetadata(metadataBuilder.build());
        }
    };

    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaSessionCallback.onPlay(); // Не очень красиво
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaSessionCallback.onPause();
                    break;
                default:
                    mediaSessionCallback.onPause();
                    break;
            }
        }
    };

    private final BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Disconnecting headphones - stop playback
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                mediaSessionCallback.onPause();
            }
        }
    };

    private ExoPlayer.EventListener exoPlayerListener = new ExoPlayer.EventListener() {

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

//        @Override
//        public void onLoadingChanged(boolean isLoading) {
//        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playWhenReady && playbackState == ExoPlayer.STATE_ENDED) {
                mediaSessionCallback.onSkipToNext();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerServiceBinder();
    }

    public class PlayerServiceBinder extends Binder {
        public MediaSessionCompat.Token getMediaSessionToken() {
            return mediaSession.getSessionToken();
        }
    }

    private void refreshNotificationAndForegroundStatus(int playbackState) {
        switch (playbackState) {
            case PlaybackStateCompat.STATE_PLAYING: {
                startForeground(NOTIFICATION_ID, getNotification(playbackState));
                break;
            }
            case PlaybackStateCompat.STATE_PAUSED: {
                NotificationManagerCompat.from(PlayerService.this)
                        .notify(NOTIFICATION_ID, getNotification(playbackState));
                stopForeground(false);
                break;
            }

//            case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT: {
//                startForeground(NOTIFICATION_ID, getNotification(PlaybackStateCompat.STATE_PLAYING));
//            }

            default: {
                stopForeground(true);
                break;
            }
        }
    }

    private Notification getNotification(int playbackState) {
        NotificationCompat.Builder builder = MediaStyleHelper.from(ctx, mediaSession);
        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_previous, getString(R.string.previous), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)));

        if (playbackState == PlaybackStateCompat.STATE_PLAYING)
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, getString(R.string.pause), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
        else
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, getString(R.string.play), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));

        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_next, getString(R.string.next), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)));
        builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1)
                .setShowCancelButton(true)
                .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_STOP))
                .setMediaSession(mediaSession.getSessionToken())); // setMediaSession требуется для Android Wear
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // The whole background (in MediaStyle), not just icon background
        builder.setShowWhen(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOnlyAlertOnce(true);
        builder.setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID);

        return builder.build();
    }

//    private void initializePlayer(Track track) {
////        player = new SimpleExoPlayer.Builder(ctx).build();
////        playerView.setPlayer(player);
//        exoPlayer.setPlayWhenReady(playWhenReady);
//        exoPlayer.seekTo(currentWindow, playbackPosition);
//        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
//        addFilesToPlayer(track);
//        exoPlayer.prepare();
//    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void addFilesToPlayer(Track track) {
        AppProperty props = AppProperty.getInstance(ctx);
//        TrackHelper trackHelper = new TrackHelper(ctx);
        try {
            List<Word> words = wordRepo.findWordsByTrackName(track.name);
            for (Word word : words) {
                addWordFiles(word, props);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addWordFiles(Word word, AppProperty props) {
        File dir = ctx.getFilesDir();
        String[] names = word.fileNames.split(";;");
        for (int i = 0; i < props.countRepeat; i++) {
            exoPlayer.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[0]))));
            Uri uri = PlayWorker.getSilentUri(props.wordPause);
            exoPlayer.addMediaItem(MediaItem.fromUri(uri));
        }
        for (int i = 1; i < names.length; i++) {
            if ("Silent".equalsIgnoreCase(names[i])) {
                Uri uri = PlayWorker.getSilentUri(props.translPause);
                exoPlayer.addMediaItem(MediaItem.fromUri(uri));
            } else {
                if (names[i].equals("Silent")) {
                    continue;
                }
                exoPlayer.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, names[i]))));
            }
        }
    }
}