package com.e.words.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.e.words.R;
import com.e.words.config.AppProperty;
import com.e.words.menu.MenuMain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SettingFragment extends Fragment {

    private TextView tvSpeedTts;
    private TextView tvCountRepeat;
    private TextView tvPitch;
    private TextView tvWordPause;
    private TextView tvTranslPause;
    private MainFragment mainFrgm;

    public SettingFragment() {
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppProperty props = AppProperty.getInstance(Objects.requireNonNull(getActivity()));
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        SeekBar sbCountRepeat = view.findViewById(R.id.sb_count_repeat);
        SeekBar sbSpeedTts = view.findViewById(R.id.sb_speed_tts);
        SeekBar sbPitch = view.findViewById(R.id.sb_pitch);
        SeekBar sbWordPause = view.findViewById(R.id.sb_pause_words);
        SeekBar sbTranslPause = view.findViewById(R.id.sb_pause_transl);
        tvCountRepeat = view.findViewById(R.id.tv_count_repeat);
        tvSpeedTts = view.findViewById(R.id.tv_speed_tts);
        tvPitch = view.findViewById(R.id.tv_pitch);
        tvWordPause = view.findViewById(R.id.tv_pause_words);
        tvTranslPause = view.findViewById(R.id.tv_pause_transl);
        sbCountRepeat.setMax(10);
        sbCountRepeat.setMin(2);
        sbCountRepeat.setProgress(props.countRepeat);
        tvCountRepeat.setText(String.valueOf(props.countRepeat));
        sbCountRepeat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                tvCountRepeat.setText(String.valueOf(progress));
                props.isPropsChange = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvCountRepeat.setText(String.valueOf(progressValue));
                props.countRepeat = progressValue;
            }
        });
        sbSpeedTts.setMax(150);
        sbSpeedTts.setMin(50);
        int speedProgress = (int) (props.speedTts * 100);
        sbSpeedTts.setProgress(speedProgress);
        tvSpeedTts.setText(String.valueOf(speedProgress));
        sbSpeedTts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                tvSpeedTts.setText(progress + "%");
                props.isPropsChange = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvSpeedTts.setText(progressValue + "%");
                props.speedTts = (float) progressValue / 100;
            }
        });
        sbPitch.setMax(150);
        sbPitch.setMin(50);
        int pitchProgress = (int) (props.pitch * 100);
        sbPitch.setProgress(pitchProgress);
        tvPitch.setText(String.valueOf(pitchProgress) + "%");
        sbPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
                tvPitch.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvPitch.setText(progressValue + "%");
                props.pitch = (float) progressValue / 100;
                props.isPropsChange = true;
            }
        });
        sbWordPause.setMax(2000);
        sbWordPause.setMin(100);
        sbWordPause.incrementProgressBy(100);
        int wordPauseProgress = props.wordPause;
        sbWordPause.setProgress(wordPauseProgress);
        tvWordPause.setText(String.valueOf(wordPauseProgress));
        sbWordPause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 100;
                progress = progress * 100;
                System.out.println(progress);
                progressValue = progress;
                tvWordPause.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvWordPause.setText(String.valueOf(progressValue));
                props.isPropsChange = true;
                props.wordPause = progressValue;
            }
        });
        sbTranslPause.setMax(2000);
        sbTranslPause.setMin(100);
        sbTranslPause.incrementProgressBy(100);
        int translPauseProgress = props.translPause;
        sbTranslPause.setProgress(translPauseProgress);
        tvTranslPause.setText(String.valueOf(translPauseProgress));
        sbTranslPause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 100;
                progress = progress * 100;
                progressValue = progress;
                tvTranslPause.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvTranslPause.setText(String.valueOf(progressValue));
                props.isPropsChange = true;
                props.translPause = progressValue;
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new MenuMain(getActivity()).getMain(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}