package com.e.words.view.menu;

import android.app.AlertDialog;

import androidx.fragment.app.FragmentActivity;

import com.e.words.R;
import com.e.words.db.repository.TrackRepo;
import com.e.words.entity.Track;
import com.e.words.entity.dto.TrackWithWords;
import com.e.words.view.fragment.AddWordFragment;
import com.e.words.view.fragment.MainFragment;
import com.e.words.view.fragment.PlayFragment;
import com.e.words.view.fragment.SettingFragment;
import com.e.words.view.fragment.TrackFragment;
import com.e.words.view.fragment.TrackListFragment;
import com.e.words.view.fragment.VocabularyFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuMain {

    private final FragmentActivity activity;


    public MenuMain(FragmentActivity activity) {
        this.activity = activity;
    }

    public void getMain(int id) {
        switch (id) {
            case R.id.act_vocabulary:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new VocabularyFragment())
                        .commit();
                return;
            case R.id.act_play:
                try {
                    TrackRepo repo = new TrackRepo(activity);
                    List<Track> tracks = repo.findAllTrack();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, PlayFragment.newInstance(tracks, null))
                            .commit();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            case R.id.act_add:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new AddWordFragment())
                        .commit();
                return;
            case R.id.act_track:
                try {
                    TrackRepo repo = new TrackRepo(activity);
                    List<Track> tracks = repo.findAllTrack();
                    if (tracks.size() == 0) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                        dialog
                                .setMessage(R.string.mess_not_find_track)
                                .create().show();
                    } else {
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_act, TrackFragment.newInstance(tracks))
                                .commit();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return;
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
                return;
            case R.id.act_setting:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new SettingFragment())
                        .commit();
                return;
            case R.id.act_main_menu:
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, new MainFragment())
                        .commit();
        }
    }
}
