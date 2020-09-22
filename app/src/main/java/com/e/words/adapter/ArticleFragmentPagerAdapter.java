package com.e.words.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.fragment.ArticleFragment;
import com.e.words.fragment.FullWordFragment;

public class ArticleFragmentPagerAdapter extends FragmentPagerAdapter {
    private WordObj wordObj;
    private WordObj smallWord;


    public ArticleFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior,
                                       WordObj wordObj, WordObj smallWord) {
        super(fm, behavior);

        this.wordObj = wordObj;
        this.smallWord = smallWord;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ArticleFragment.newInstance(wordObj);
            case 1:
            case 2:
                return FullWordFragment.newInstance(wordObj, smallWord, position);
            default:
                return new ArticleFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Словарная статья";

            case 1:
                return "Полный перевод";

            case 2:
                return "Избранный перевод";

            default:
                return null;
        }
    }
}
