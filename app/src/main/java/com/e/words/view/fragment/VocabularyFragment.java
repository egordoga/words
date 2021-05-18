package com.e.words.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.entity.dto.TrackWithWords;
import com.e.words.entity.dto.FullWordObj;
import com.e.words.entity.dto.WordObj;
import com.e.words.view.adapter.VocabularyAdapter;
import com.e.words.entity.Track;
import com.e.words.entity.Word;
import com.e.words.view.menu.MenuMain;
import com.e.words.db.repository.TrackRepo;
import com.e.words.db.repository.WordObjRepo;
import com.e.words.util.Util;
import com.e.words.util.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

//public class VocabularyFragment extends Fragment implements VocabularyAdapter.ItemClickListener {
public class VocabularyFragment extends Fragment implements VocabularyAdapter.ItemClickListenerNew {

 //   private static WordObjRepo repoWord;
    private TrackRepo trackRepo;
    private WordObjRepo wordObjRepo;
    private MainFragment mainFrgm;
//    private List<VocabularyDto> vocabList;
    private List<WordObj> wordObjList;
    private String[] trackNames;
    private boolean isTrackAdd = false;
    private Context ctx;
    private TextToSpeech tts;
  //  private List<String> wordsStr;
    private VocabularyAdapter adapter;

    public VocabularyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     repoWord = new WordObjRepo(getContext());
        trackRepo = new TrackRepo(getContext());
        wordObjRepo = new WordObjRepo(getContext());
        mainFrgm = new MainFragment();
        ctx = getContext();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        RecyclerView rvVocab = view.findViewById(R.id.rv_vocab);
        rvVocab.setLayoutManager(new LinearLayoutManager(ctx));
     //   VocabularyAdapter adapter = new VocabularyAdapter(ctx, this);
        adapter = new VocabularyAdapter(ctx, this);
        try {
        //    vocabList = new FindWordsAsyncTask().execute().get();
            wordObjList = new WordObjRepo(getContext()).findAllWordObj();
         //   wordsStr = getWordList();
            adapter.setItem(wordObjList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        rvVocab.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        new MenuMain(getActivity()).getMain(id);
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view, int position) {
        PopupMenu pMenu = new PopupMenu(getContext(), view);
        pMenu.inflate(R.menu.menu_add_to_track);
        pMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.act_add_to_track:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Выберите трек")
                            .setItems(getTrackNames(), (dialog, which) -> {
                                if (which == 0) {
                                    AlertDialog.Builder adName = new AlertDialog.Builder(ctx);
                                    LayoutInflater li = LayoutInflater.from(ctx);
                                    View nameView = li.inflate(R.layout.track_name_alert, null);
                                    adName.setView(nameView);
                                    final EditText etName = nameView.findViewById(R.id.et_track_name);
                                    adName
                                            .setCancelable(false)
                                            .setPositiveButton("OK", (dialog1, id) -> {
                                                tts = new TextToSpeech(ctx, status -> {
                                                    String trackName = etName.getText().toString();
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
                                                            trackRepo.insertTrackWithWords(trackName, wordObjList.get(position).word, tts);
                                                        }
                                                    } catch (ExecutionException | InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                });
                                                isTrackAdd = true;
                                            })
                                            .setNegativeButton("CANCEL", (dialog1, which1) -> dialog1.cancel())
                                            .create()
                                            .show();
                                } else {
                                    try {
                                     //   Track track = trackRepo.findTrackByName(trackNames[which]);
                                        TrackWithWords trackWw = trackRepo.findTrackWithWordsByName(trackNames[which]);
                                     //   String[] words = track.words.split(";;");
                                     //   boolean isPresent = wordsStr.contains(String.valueOf(vocabList.get(position).word));
                                        boolean isPresent = getWordStrList(trackWw).contains(wordObjList.get(position).word.word);
                                        if (isPresent) {
                                            AlertDialog.Builder wordPresent = new AlertDialog.Builder(ctx);
                                            wordPresent
                                                    .setTitle("Предупреждение")
                                                    .setMessage("Такое слово уже есть в этом треке")
                                                    .create()
                                                    .show();
                                        } else {
                                            tts = new TextToSpeech(ctx, status ->
                                                    trackRepo.addToTrack(trackWw.track, wordObjList.get(position).word, tts));
                                        }
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    builder.create();
                    builder.show();
                    Toast.makeText(getContext(), wordObjList.get(position).word.word, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.act_del_word:
                    WordObj wordObj = wordObjList.get(position);
                    FileWorker worker = new FileWorker();
//                    worker.deleteFile(word.word + ".wav", ctx);
//                    worker.deleteFile(word.word + "US.wav", ctx);
                    worker.deleteFiles(wordObj.word.word, false, ctx);
                    Track track = isWordLastInTrack(wordObj.word);
                    if (track != null) {
                        trackRepo.deleteTrackWithLastWord(track, wordObj);
                    } else {
                        wordObjRepo.deleteWordObj(wordObj);
                    }
                    wordObjList.remove(position);
                    adapter.deleteItem(position);
                    return true;
                case R.id.act_article:
                    FullWordObj fullWordObj = new Util(ctx).makeFullObj(wordObjList.get(position));
                    WordFragment wf = WordFragment.newInstance(fullWordObj.wordObj, fullWordObj.json, null, true);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, wf)
                            .commit();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        });
        pMenu.show();
    }

    private Track isWordLastInTrack(Word word) {
        if (word.trackId != null) {
            try {
                TrackWithWords trackWw = trackRepo.findTrackWithWordsById(word.trackId);
                if (trackWw.wordList.size() == 1 && trackWw.wordList.get(0).id == word.id) {
                    return trackWw.track;
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String[] getTrackNames() {
        if (trackNames == null || isTrackAdd) {
            try {
                List<String> list = trackRepo.findTrackNames();
                list.add(0, "Создать новый трек");
                trackNames = list.toArray(new String[0]);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            isTrackAdd = false;
        }
        return trackNames;
    }

//    static class FindWordsAsyncTask extends AsyncTask<Void, Void, List<VocabularyDto>> {
//        @Override
//        protected List<VocabularyDto> doInBackground(Void... voids) {
//            return repoWord.findAllVocabularyDto();
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    private List<String> getWordStrList(TrackWithWords trackWw) {
        List<String> list = new ArrayList<>();
        for (Word word : trackWw.wordList) {
            list.add(word.word);
        }
        return list;
    }
}