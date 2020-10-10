package com.e.words.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.e.words.abby.abbyEntity.dto.dto_new.TrackSmall;
import com.e.words.repository.TrackRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrackListFragment  extends DialogFragment {
    private String[] trackNames;
    private TrackRepo repo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите кота")
                .setItems(getTrackNames(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "Выбранный кот: " + getTrackNames()[which],
                                Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String[] getTrackNames() {
        if (trackNames == null) {
            try {
                List<String> list = repo.findTrackNames();
                list.add(0, "Создать новый трек");
                trackNames = (String[]) list.toArray();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return trackNames;
    }
}
