package com.e.words;

import android.os.Bundle;

import com.e.words.config.AppProperty;
import com.e.words.entity.Track;
import com.e.words.view.fragment.MainFragment;
import com.e.words.view.fragment.PlayFragment;
import com.e.words.db.repository.TrackRepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

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
            } else target = PlayFragment.newInstance(tracks, null);
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
