package com.e.words.fragment;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.adapter.VocabularyAdapter;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class VocabularyFragment extends Fragment implements VocabularyAdapter.ItemClickListener {

    private RecyclerView rvVocab;
    private VocabularyAdapter adapter;
    private static WordObjRepo repoWord;
    private TrackRepo repoTrack;
    private MainFragment mainFrgm;
    private List<VocabularyDto> vocabList;
    private String[] trackNames;
    private boolean isTrackAdd = false;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public VocabularyFragment() {
        // Required empty public constructor
    }

//    public static VocabularyFragment newInstance(String param1, String param2) {
//        VocabularyFragment fragment = new VocabularyFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        repoWord = new WordObjRepo(getContext());
        repoTrack = new TrackRepo(getContext());
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        rvVocab = view.findViewById(R.id.rv_vocab);
        rvVocab.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VocabularyAdapter(getContext(), this);
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
                case R.id.act_add_to_track :
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Выберите трек")
                            .setItems(getTrackNames(), (dialog, which) -> {
                                if (which == 0) {
                                    AlertDialog.Builder adName = new AlertDialog.Builder(getContext());
                                    LayoutInflater li = LayoutInflater.from(getContext());
                                    View nameView = li.inflate(R.layout.track_name_alert, null);
                                    adName.setView(nameView);
                                    final EditText etName = nameView.findViewById(R.id.et_track_name);
                                    adName
                                            .setCancelable(false)
                                            .setPositiveButton("OK", (dialog1, id) ->{
                                                repoTrack.insertTrack(etName.getText().toString(), vocabList.get(position).wordId);
                                                //getTrackNamesFromDb();
                                                isTrackAdd = true;
                                            })
                                            .setNegativeButton("CANCEL", (dialog1, which1) -> dialog1.cancel())
                                            .create()
                                            .show();
                                } else repoTrack.addToTrack(trackNames[which], vocabList.get(position).wordId);
                            });

                    builder.create();
                    builder.show();
                    Toast.makeText(getContext(), vocabList.get(position).word, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.act_del_word:
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
}