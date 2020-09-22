package com.e.words.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.e.words.R;
import com.e.words.abby.JsonConvertNew;
import com.e.words.abby.JsonData;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.adapter.ArticleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class WordFragment extends Fragment {

    private WordObj wordObj;
    private WordObj smallWord;

    public WordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JsonConvertNew jc = new JsonConvertNew();
        jc.jsonToObj(JsonData.LOOK);
        wordObj = jc.wordObj;
        smallWord = new WordObj();
        smallWord.word = wordObj.word;
        smallWord.transcriptions = wordObj.transcriptions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        ArticleFragmentPagerAdapter mFragmentAdapter = new ArticleFragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, wordObj, smallWord);
        ViewPager mViewPager = view.findViewById(R.id.vp_word);
        mViewPager.setAdapter(mFragmentAdapter);
        TabLayout mTabLayout = view.findViewById(R.id.tl_word);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
             //   Toast.makeText(getContext(), "Size " + getSmallWord().translations.size(), Toast.LENGTH_LONG).show();
//                int position = tab.getPosition();
//                switch (position) {
//                    case 0:
//                        replaceFragment(ArticleFragment.newInstance(wordObj));
//                        break;
//                    case 1:
//                    case 2:
//                        replaceFragment(FullWordFragment.newInstance(wordObj, /*smallWord,*/ position));
//                }

                if (tab.getPosition() == 2 || tab.getPosition() == 1) {
                  //  redrawFragment(FullWordFragment.newInstance(wordObj, /*smallWord,*/ tab.getPosition()));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2 || tab.getPosition() == 1) {
                   // redrawFragment(FullWordFragment.newInstance(wordObj, /*smallWord,*/ tab.getPosition()));
                }
            }
        });
        return view;
    }
}