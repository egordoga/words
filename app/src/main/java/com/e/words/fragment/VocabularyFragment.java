package com.e.words.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.adapter.VocabularyAdapter;
import com.e.words.entity.entityNew.Track;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class VocabularyFragment extends Fragment implements VocabularyAdapter.ItemClickListener {

    private static WordObjRepo repoWord;
    private TrackRepo repoTrack;
    private MainFragment mainFrgm;
    private List<VocabularyDto> vocabList;
    private String[] trackNames;
    private boolean isTrackAdd = false;
    private Context ctx;
    private TextToSpeech tts;

    public VocabularyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repoWord = new WordObjRepo(getContext());
        repoTrack = new TrackRepo(getContext());
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
        VocabularyAdapter adapter = new VocabularyAdapter(ctx, this);
        try {
            vocabList = new FindWordsAsyncTask().execute().get();
            adapter.setItem(vocabList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        rvVocab.setAdapter(adapter);
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
                                                        Track track = repoTrack.findTrackByName(trackName);
                                                        if (track != null) {
                                                            AlertDialog.Builder trackPresent = new AlertDialog.Builder(ctx);
                                                            trackPresent
                                                                    .setTitle("Предупреждение")
                                                                    .setMessage("Такое имя уже есть в списке треков")
                                                                    .create()
                                                                    .show();
                                                        } else {
                                                            repoTrack.insertTrack(trackName, vocabList.get(position).word, tts);
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
                                        Track track = repoTrack.findTrackByName(trackNames[which]);
                                        String[] words = track.words.split(";;");
                                        boolean isPresent = Arrays.asList(words).contains(String.valueOf(vocabList.get(position).word));
                                        if (isPresent) {
                                            AlertDialog.Builder wordPresent = new AlertDialog.Builder(ctx);
                                            wordPresent
                                                    .setTitle("Предупреждение")
                                                    .setMessage("Такое слово уже есть в этом треке")
                                                    .create()
                                                    .show();
                                        } else {
                                            tts = new TextToSpeech(ctx, status ->
                                                    repoTrack.addToTrack(track, vocabList.get(position).word, tts));
                                        }
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    builder.create();
                    builder.show();
                    Toast.makeText(getContext(), vocabList.get(position).word, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.act_del_word:
                    FileWorker worker = new FileWorker();
                    worker.deleteFile(vocabList.get(position).word, ctx);
                    worker.deleteFile(vocabList.get(position).word + "US", ctx);
                    new WordObjRepo(getContext()).deleteWordById(vocabList.get(position).wordId);
                    return true;
                case R.id.act_article:

                    return true;
            }
            return super.onOptionsItemSelected(item);
        });
        pMenu.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String[] getTrackNames() {
        if (trackNames == null || isTrackAdd) {
            try {
                List<String> list = repoTrack.findTrackNames();
                list.add(0, "Создать новый трек");
                trackNames = list.toArray(new String[0]);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            isTrackAdd = false;
        }
        return trackNames;
    }

    static class FindWordsAsyncTask extends AsyncTask<Void, Void, List<VocabularyDto>> {
        @Override
        protected List<VocabularyDto> doInBackground(Void... voids) {
            return repoWord.findAllVocabularyDto();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}