package com.e.words.menu;

import android.app.AlertDialog;

import androidx.fragment.app.FragmentActivity;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.TrackWithWords;
import com.e.words.entity.entityNew.Track;
import com.e.words.fragment.AddWordFragment;
import com.e.words.fragment.MainFragment;
import com.e.words.fragment.PlayFragmentNew;
import com.e.words.fragment.SettingFragment;
import com.e.words.fragment.TrackFragment;
import com.e.words.fragment.TrackListFragment;
import com.e.words.fragment.VocabularyFragment;
import com.e.words.repository.TrackRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuMain {

    private final FragmentActivity activity;


    public MenuMain(FragmentActivity activity) {
        this.activity = activity;
    }

    public boolean getMain(int id) {
        switch (id) {
            case R.id.act_vocabulary:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new VocabularyFragment())
                        .commit();
                return true;
            case R.id.act_play:
                try {
                    TrackRepo repo = new TrackRepo(activity);
                    List<Track> tracks = repo.findAllTrack();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, PlayFragmentNew.newInstance(tracks))
                            .commit();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.act_add:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new AddWordFragment())
                        .commit();
                return true;
            case R.id.act_track:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new TrackFragment())
                        .commit();
                return true;
            case R.id.act_track_list:
                try {
                    TrackRepo repo = new TrackRepo(activity);
                    List<TrackWithWords> tracks = repo.findAllTrackWithWords();
                    if (tracks.size() == 0) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                        dialog
                                .setMessage(R.string.mess_not_find_track)
                                .create().show();
                    } else {
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_act, TrackListFragment.newInstance(tracks))
                                .commit();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.act_setting:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new SettingFragment())
                        .commit();
                return true;
            case R.id.act_main_menu:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new MainFragment())
                        .commit();
                return true;
        }
        return true;
    }
}
