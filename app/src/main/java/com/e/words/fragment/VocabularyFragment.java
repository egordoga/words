package com.e.words.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.adapter.VocabularyAdapter;
import com.e.words.adapter.WordAdapter;
import com.e.words.repository.WordObjRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class VocabularyFragment extends Fragment {

    private RecyclerView rvVocab;
    private VocabularyAdapter adapter;
    private static WordObjRepo repo;

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

        repo = new WordObjRepo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        rvVocab = view.findViewById(R.id.rv_vocab);
        rvVocab.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VocabularyAdapter(getContext(), null);
        try {
            adapter.setItem(new FindWordsAsyncTask().execute().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        rvVocab.setAdapter(adapter);
        return view;
    }

    static class FindWordsAsyncTask extends AsyncTask<Void, Void, List<VocabularyDto>> {
        @Override
        protected List<VocabularyDto> doInBackground(Void... voids) {
            return repo.findAllVocabularyDto();
        }
    }
}