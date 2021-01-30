package com.e.words.fragment;

//import androidx.appcompat.app.AlertDialog;

import android.content.Context;
//import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.temp.TestTTS;
import com.e.words.worker.PlayWorker;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayFragment extends Fragment /*implements TextToSpeech.OnInitListener*/ {

    private Spinner spinner;
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
    private List<WordObj> wordObjList;
    private WordObjRepo wordRepo;
    private TextToSpeech tts;
    private Context ctx;
    private SimpleExoPlayer player;
    private StyledPlayerView playerView;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TRACK_PARAM = "tracks";
    //   private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance(List<Track> tracks) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRACK_PARAM, (Serializable) tracks);
        //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tracks = (List<Track>) getArguments().getSerializable(TRACK_PARAM);
            //     mParam2 = getArguments().getString(ARG_PARAM2);
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


//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder
//                .setMessage("kkkkkkk")
//                .setPositiveButton("ok", (v, w) ->
//                        Toast.makeText(getContext(), "Вы нажали OK", Toast.LENGTH_SHORT).show())
//         //       .create()
//                .show();
//
//
//        System.out.println("KKKKKKKKKKKKKKKKK");
//        System.out.println("KKKKKKKKKKKKKKKKK");
//        System.out.println("KKKKKKKKKKKKKKKKK");


        //     tts = new TextToSpeech(getActivity(), this);


       /* btnPlay = view.findViewById(R.id.btn_play);
        btnStop = view.findViewById(R.id.btn_stop);
        btnPause = view.findViewById(R.id.btn_pause);
//        trackRepo = new TrackRepo(getContext());
        wordRepo = new WordObjRepo(getContext());
        mainFrgm = new MainFragment();
        playerView = view.findViewById(R.id.player_view);
        getAllTrack();
        System.out.println("TRACK SIZE " + tracks.size());
        if (tracks.size() == 0) {
            System.out.println("JJJJJJJJJJJJJJJJJJJJ");*/


//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            System.out.println("DDDDDDDDDDDDDDD");
//            builder
//                    .setMessage("Нет ни одного трека")
//                    .setPositiveButton("OK", (dialog, which) -> {
//                System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
//                dialog.cancel();
//                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.main_act, mainFrgm)
//                        .commit();
//            })
        //                  .create()
        //          .show();

//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//
//            // Указываем Title
//            alertDialog.setTitle("Информационое сообщение");
//
//            // Указываем текст сообщение
//            alertDialog.setMessage("Welcome to devcolibri.com");
//
//            // задаем иконку
//         //   alertDialog.setIcon(R.drawable.tick);
//
//            // Обработчик на нажатие OK
//            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // Код который выполнится после закрытия окна
//                    Toast.makeText(getContext(), "Вы нажали OK", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            // показываем Alert
//            alertDialog.show();


//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder
//                    .setMessage("jjjjjjjjjjjjjjj")
////                    .setPositiveButton("OK", (dialog, which) ->
////                            Toast.makeText(getContext(), "Вы нажали OK", Toast.LENGTH_SHORT).show())
//                    .show();


//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder
//                    .setTitle("ERROR 404")
//                    .setMessage("Такого слова не найдено в словаре ABBY")
//                    .setCancelable(true)
//                    .create()
//                    .show();


//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Выберите трек");
////                    .setItems(new String[] {"ddd", "kkk"}, (dialog, which) -> {
////                        if (which == 0) {
////                            AlertDialog.Builder adName = new AlertDialog.Builder(getContext());
////                            LayoutInflater li = LayoutInflater.from(getContext());
////                            View nameView = li.inflate(R.layout.track_name_alert, null);
////                            adName.setView(nameView);
////                            final EditText etName = nameView.findViewById(R.id.et_track_name);
////                            adName
////                                    .setCancelable(false)
////                                    .setPositiveButton("OK", (dialog1, id) ->{
////                                        repoTrack.insertTrack(etName.getText().toString(),
////                                                vocabList.get(position).word, new TextToSpeech(getContext(), this));
////                                        //getTrackNamesFromDb();
////                                        isTrackAdd = true;
////                                    })
////                                    .setNegativeButton("CANCEL", (dialog1, which1) -> dialog1.cancel())
////                                    .create()
////                                    .show();
////                        } else {
////                            try {
////                                Track track = repoTrack.findTrackByName(trackNames[which]);
////                                String[] words = track.words.split(";;");
////                                boolean isPresent = Arrays.asList(words).contains(String.valueOf(vocabList.get(position).word));
////                                if (isPresent) {
////                                    AlertDialog.Builder wordPresent = new AlertDialog.Builder(getContext());
////                                    wordPresent
////                                            .setTitle("Предупреждение")
////                                            .setMessage("Такое слово уже есть в этом треке")
////                                            .create()
////                                            .show();
////                                } else {
////                                    repoTrack.addToTrack(track, vocabList.get(position).word);
////                                }
////                            } catch (ExecutionException | InterruptedException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    });
//
//            builder.create();
//            builder.show();


       // }
        spinner = view.findViewById(R.id.spinner_play);
        ArrayAdapter<Track> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, Objects.requireNonNull(tracks));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(lastTrackPosition);
   //     selectedTrack = tracks.get(lastTrackPosition);

        getWordObjList(selectedTrack);

//        TestTTS testTTS = new TestTTS(getContext());


//        ctx = getContext();
//        player = new SimpleExoPlayer.Builder(ctx).build();
//        File dir = ctx.getFilesDir();
//        File file1 = new File(dir, "issue.wav");
//        Uri uri1 = Uri.fromFile(file1);
//        MediaItem itm1 = MediaItem.fromUri(uri1);
//        player.addMediaItem(itm1);
//        for (int i = 1; i < 10; i++) {
//            player.addMediaItem(MediaItem.fromUri(Uri.fromFile(new File(dir, "issue" + i + ".wav"))));
//        }
//        player.prepare();


 //       AtomicBoolean isStop = new AtomicBoolean(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  tracks.get(lastTrackPosition).isLast = false;
                lastTrackPosition = position;
                selectedTrack = tracks.get(lastTrackPosition);
            //    selectedTrack.isLast = true;
             //   isChangeTrack = true;
                releasePlayer();
                getWordObjList(selectedTrack);
                initializePlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btnPlay.setOnClickListener(v -> {
//            player.play();
//        });
//        btnStop.setOnClickListener(v -> {
//           player.stop(false);
//        });
//        btnPause.setOnClickListener(v -> player.pause());

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllTrack() {
        try {
            tracks = trackRepo.findAllTrack();
            for (int i = 0; i < tracks.size(); i++) {
                if (tracks.get(i).isLast) {
                    lastTrackPosition = i;
                    break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getWordObjList(Track track) {
        String[] words = track.words.split(";;");
        try {
            wordObjList = wordRepo.findAllWordByWords(words);
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
                System.out.println("FF " + word.word);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addWordFiles(Word word, AppProperty props) {
        File dir = ctx.getFilesDir();
        String[] names = word.fileNames.split(";;");
    //    MediaItem item;
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