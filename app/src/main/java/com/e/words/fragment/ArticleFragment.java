package com.e.words.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;

public class ArticleFragment extends Fragment {
    private String article;

    public ArticleFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        TextView articleTw = view.findViewById(R.id.tw_article);
        articleTw.setMovementMethod(new ScrollingMovementMethod());
        articleTw.setText(article);
        return view;
    }
}