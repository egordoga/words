package com.e.words.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.adapter.ArticleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.List;

public class WordFragment extends Fragment {

    private WordObj wordObj;
    private WordObj smallWord;
    private String json;
    private List<byte[]> sounds;
    private boolean isPresent;

    public WordFragment() {
    }

    public static WordFragment newInstance(WordObj wordObj, String json, List<byte[]> sounds, boolean isPresent) {
        WordFragment fragment = new WordFragment();
        Bundle args = new Bundle();
        args.putSerializable("wordObj", wordObj);
        args.putSerializable("sounds", (Serializable) sounds);
        args.putString("json", json);
        args.putBoolean("isPresent", isPresent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            wordObj = (WordObj) args.getSerializable("wordObj");
            sounds = (List<byte[]>) args.getSerializable("sounds");
            json = args.getString("json");
            isPresent = args.getBoolean("isPresent");
        }
        smallWord = new WordObj();
        smallWord.word = wordObj.word;
        smallWord.word.transcript = wordObj.word.transcript;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        ArticleFragmentPagerAdapter mFragmentAdapter = new ArticleFragmentPagerAdapter(this, wordObj, smallWord, json, sounds, isPresent);
        ViewPager2 mViewPager = view.findViewById(R.id.vp_word);
        mViewPager.setAdapter(mFragmentAdapter);
        TabLayout mTabLayout = view.findViewById(R.id.tl_word);
        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Словарная статья");
                    break;
                case 1:
                    tab.setText("Полный перевод");
                    break;
                case 2:
                    tab.setText("Избранный перевод");
            }
        }).attach();
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 2 || tab.getPosition() == 1) {
//                    //  redrawFragment(FullWordFragment.newInstance(wordObj, /*smallWord,*/ tab.getPosition()));
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 2 || tab.getPosition() == 1) {
//                    // redrawFragment(FullWordFragment.newInstance(wordObj, /*smallWord,*/ tab.getPosition()));
//                }
//            }
//        });
        return view;
    }
}