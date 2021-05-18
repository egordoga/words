package com.e.words.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.e.words.entity.dto.WordObj;
import com.e.words.view.fragment.ArticleFragment;
import com.e.words.view.fragment.FullWordFragment;

import java.util.List;

public class ArticleFragmentPagerAdapter extends FragmentStateAdapter {
    private final WordObj wordObj;
    private final WordObj smallWord;
    private final String json;
    private final List<byte[]> sounds;
    private final boolean isPresent;


    public ArticleFragmentPagerAdapter(Fragment fm, WordObj wordObj,
                                       WordObj smallWord, String json, List<byte[]> sounds, boolean isPresent) {
        super(fm);

        this.wordObj = wordObj;
        this.smallWord = smallWord;
        this.json = json;
        this.sounds = sounds;
        this.isPresent = isPresent;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ArticleFragment.newInstance(wordObj);
            case 1:
            case 2:
                return FullWordFragment.newInstance(wordObj, smallWord, position, json, sounds, isPresent);
            default:
                return new ArticleFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
