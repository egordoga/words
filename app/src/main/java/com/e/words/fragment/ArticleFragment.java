package com.e.words.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
//import android.support.v4.app

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.words.R;
import com.e.words.abby.JsonConvertNew;
import com.e.words.abby.JsonData;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {
    private String article;
    TextView articleTw;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleFragment() {
        // Required empty public constructor
    }


    public static ArticleFragment newInstance(WordObj wordObj) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable("wordObj", wordObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            WordObj wordObj = (WordObj) getArguments().getSerializable("wordObj");
            article = wordObj.word.article;
        }
//        JsonConvertNew jc = new JsonConvertNew();
//        jc.jsonToObj(JsonData.LOOK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        articleTw = view.findViewById(R.id.tw_article);
        articleTw.setMovementMethod(new ScrollingMovementMethod());
        articleTw.setText(article);
        return view;
    }
}