package com.e.words.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.fragment.ArticleFragment;
import com.e.words.fragment.FullWordFragment;

import java.util.List;

public class ArticleFragmentPagerAdapter extends FragmentStateAdapter {
    private WordObj wordObj;
    private WordObj smallWord;
    private String json;
    private List<byte[]> sounds;


    public ArticleFragmentPagerAdapter(/*@NonNull FragmentManager fm, int behavior,*/Fragment fm, WordObj wordObj,
                                       WordObj smallWord, String json, List<byte[]> sounds) {
        super(fm);

        this.wordObj = wordObj;
        this.smallWord = smallWord;
        this.json = json;
        this.sounds = sounds;
    }

//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//
//        System.out.println("ArticleFragmentPagerAdapter  getItem   ".toUpperCase() + wordObj.word.word);
//
//
//        switch (position) {
//            case 0:
//                return ArticleFragment.newInstance(wordObj);
//            case 1:
//            case 2:
//                return FullWordFragment.newInstance(wordObj, smallWord, position, json, sounds);
//            default:
//                return new ArticleFragment();
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 3;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Словарная статья";
//
//            case 1:
//                return "Полный перевод";
//
//            case 2:
//                return "Избранный перевод";
//
//            default:
//                return null;
//        }
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ArticleFragment.newInstance(wordObj);
            case 1:
            case 2:
                return FullWordFragment.newInstance(wordObj, smallWord, position, json, sounds);
            default:
                return new ArticleFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
