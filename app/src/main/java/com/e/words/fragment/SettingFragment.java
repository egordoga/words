package com.e.words.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.e.words.R;
import com.e.words.config.AppProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    private SeekBar sbSpeedTts;
    private SeekBar sbCountRepeat;
    private SeekBar sbPitch;
    private SeekBar sbWordPause;
    private SeekBar sbTranslPause;
    private TextView tvSpeedTts;
    private TextView tvCountRepeat;
    private TextView tvPitch;
    private TextView tvWordPause;
    private TextView tvTranslPause;
    private MainFragment mainFrgm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppProperty props = AppProperty.getInstance(Objects.requireNonNull(getActivity()));
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sbCountRepeat = view.findViewById(R.id.sb_count_repeat);
        sbSpeedTts = view.findViewById(R.id.sb_speed_tts);
        sbPitch = view.findViewById(R.id.sb_pitch);
        sbWordPause = view.findViewById(R.id.sb_pause_words);
        sbTranslPause = view.findViewById(R.id.sb_pause_transl);
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
        inflater.inflate(R.menu.menu_return, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.act_return_main:
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_act, mainFrgm)
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}