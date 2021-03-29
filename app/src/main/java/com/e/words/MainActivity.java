package com.e.words;

import android.os.Bundle;

import com.e.words.adapter.VocabularyAdapter;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Track;
import com.e.words.fragment.ArticleFragment;
import com.e.words.fragment.MainFragment;
import com.e.words.fragment.PlayFragment;
import com.e.words.fragment.TestFragment;
import com.e.words.fragment.TrackListFragment;
import com.e.words.repository.TrackRepo;
import com.e.words.temp.TestTTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    private VocabularyAdapter adapter;
//    private TestTTS testTTS;
//    private ArticleFragment artFrgm;
//    private MainFragment mainFragment;
//    private PlayFragment playFragment;
//    private FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment target = null;
        TrackRepo repo = new TrackRepo(this);
        try {
            List<Track> tracks = repo.findAllTrack();
            if (tracks.size() == 0) {
                target = new MainFragment();
            } else target = PlayFragment.newInstance(tracks);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_act, target)
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppProperty.getInstance(this).saveProps();
    }
}
