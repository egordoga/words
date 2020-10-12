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
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.Translation;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.List;

public class WordFragment extends Fragment {

    private WordObj wordObj;
    private WordObj smallWord;
    private String json;
    private List<byte[]> sounds;

    public WordFragment() {
    }

    public static WordFragment newInstance(WordObj wordObj, String json, List<byte[]> sounds) {
        WordFragment fragment = new WordFragment();
        Bundle args = new Bundle();
        args.putSerializable("wordObj", wordObj);
        args.putSerializable("sounds", (Serializable) sounds);
        args.putString("json", json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wordObj = (WordObj) getArguments().getSerializable("wordObj");
            sounds = (List<byte[]>) getArguments().getSerializable("sounds");
            json = getArguments().getString("json");
        }

//        JsonConvertNew jc = new JsonConvertNew();
//        jc.jsonToObj(JsonData.LOOK);
//        wordObj = jc.wordObj;
        smallWord = new WordObj();
        smallWord.word = wordObj.word;
        smallWord.word.transcript = wordObj.word.transcript;


        System.out.println("WordFragment onCreate".toUpperCase() + "  " + wordObj.word.word.toUpperCase());


//        try {
//            smallWord = new FindWordAsyncTask().execute("look").get();
//            JsonConvertNew jc = new JsonConvertNew();
//            wordObj = jc.jsonToObj(smallWord.word.json);
//          //  wordObj = jc.wordObj;
//            setCheckedFields(smallWord);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
        //        smallWord = new WordObj();
//        smallWord.word = wordObj.word;
//        smallWord.word.transcript = wordObj.word.transcript;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
//        ArticleFragmentPagerAdapter mFragmentAdapter = new ArticleFragmentPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
//                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, wordObj, smallWord, json, sounds);
        ArticleFragmentPagerAdapter mFragmentAdapter = new ArticleFragmentPagerAdapter(this, wordObj, smallWord, json, sounds);
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
        // mTabLayout.setupWithViewPager(mViewPager);

        System.out.println("WordFragment onCreateView     ".

                toUpperCase() + wordObj.word.word);

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

    private void setCheckedFields(WordObj wordDb) {
        for (TranslationAndExample tae : wordDb.translations) {
            Translation tr = wordObj.translations.get(tae.translation.index).translation;
            tr.isChecked = true;
            for (Example ex : tae.examples) {
                Example objEx = wordObj.translations.get(tae.translation.index).examples.get(ex.index);
                objEx.isChecked = true;
            }
        }
    }

//    class FindWordAsyncTask extends AsyncTask<String, Void, WordObj> {
//        @Override
//        protected WordObj doInBackground(String... strings) {
//            return null /*new WordObjRepo(getContext()).findWordByWord(strings[0])*/;
//        }
//    }
}