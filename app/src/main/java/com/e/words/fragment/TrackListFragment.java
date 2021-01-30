package com.e.words.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.e.words.R;
import com.e.words.adapter.TrackListAdapter;
import com.e.words.entity.entityNew.Track;
import com.e.words.entity.entityNew.Word;
import com.e.words.fragment.MainFragment;
import com.e.words.fragment.PlayFragment;
import com.e.words.repository.TrackRepo;
import com.e.words.repository.WordObjRepo;
import com.e.words.worker.FileWorker;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TrackListFragment extends Fragment implements TrackListAdapter.ItemClickListener{

    private RecyclerView rvTrackList;
    private MainFragment mainFrgm;
    private PlayFragment playFrgm;
    private Context ctx;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TRACKS = "tracks";
 //   private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<Track> tracks;
    private String mParam2;

    public TrackListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tracks Parameter 1.
     * @return A new instance of fragment TrackListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackListFragment newInstance(List<Track> tracks) {
        TrackListFragment fragment = new TrackListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRACKS, (Serializable) tracks);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tracks = (List<Track>) getArguments().getSerializable(TRACKS);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mainFrgm = new MainFragment();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);
        ctx = getContext();
        rvTrackList = view.findViewById(R.id.rv_track_list);
        rvTrackList.setLayoutManager(new LinearLayoutManager(ctx));
        TrackListAdapter adapter = new TrackListAdapter(ctx, this);
      //  List<Track> tracks = new ArrayList<>();
     //   try {
        //    tracks = new TrackRepo(getContext()).findAllTrack();
        //    tracks = (List<Track>) savedInstanceState.get("tracks");
          //  System.out.println("TRACKS " + tracks.size());
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
        adapter.setItem(tracks);
        rvTrackList.setAdapter(adapter);
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

    @Override
    public void onClick(View view, int position) {
        PopupMenu pMenu = new PopupMenu(ctx, view);
        pMenu.inflate(R.menu.menu_track_list);
        pMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.act_play_track:
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_act, playFrgm)
                            .commit();
                    return true;
                case R.id.act_del_track:
                    Track track = tracks.get(position);
                    WordObjRepo repo = new WordObjRepo(ctx);
                    try {
                        List<Word> words = repo.findWordsByTrackName(track.name);
                        FileWorker worker = new FileWorker(ctx);
                        for (Word word : words) {
                            String[] names = word.fileNames.split(";;");
                            worker.deleteTranslateFiles(names, ctx);
                        }
                        new TrackRepo(ctx).deleteTrack(track);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
            }
            return super.onOptionsItemSelected(item);
        });
        pMenu.show();
    }
}