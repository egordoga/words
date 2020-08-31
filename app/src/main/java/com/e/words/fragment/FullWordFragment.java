package com.e.words.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.e.words.R;
import com.e.words.abby.JsonConvertNew;
import com.e.words.abby.JsonData;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.adapter.WordAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullWordFragment extends Fragment {

    private TextView tvWordFw;
    private TextView tvTranscrFw;
    private RecyclerView rvFullWord;
    private RecyclerView rvExample;
    private WordAdapter adapter;
    private WordObj wordObj;
    JsonConvertNew jc;
    private CheckedTextView ctvTransl;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FullWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FullWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullWordFragment newInstance(String param1, String param2) {
        FullWordFragment fragment = new FullWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        jc = new JsonConvertNew();
        jc.jsonToObj(JsonData.LOOK);
        wordObj = jc.wordObj;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_word, container, false);
        ctvTransl = view.findViewById(R.id.ctv_transl);
        tvWordFw = view.findViewById(R.id.tv_word_fw);
        tvTranscrFw = view.findViewById(R.id.tv_transcr_fw);
        tvWordFw.setText(wordObj.word);
        tvTranscrFw.setText(wordObj.transcriptions.get(0).transcript);
        rvFullWord = view.findViewById(R.id.rv_fw);
        rvFullWord.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordAdapter(this, wordObj);
        rvFullWord.setAdapter(adapter);
     //   rvExample = view.findViewById(R.id.rv_example);
     //   rvExample.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}