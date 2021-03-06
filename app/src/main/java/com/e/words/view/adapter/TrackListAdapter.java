package com.e.words.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.entity.Word;
import com.e.words.entity.dto.TrackWithWords;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder> {

    private final List<TrackWithWords> trackList;
    private final List<String> wordList;
    private final LayoutInflater inflater;
    private final ItemClickListener mListener;

    public TrackListAdapter(Context context, TrackListAdapter.ItemClickListener listener) {
        this.trackList = new ArrayList<>();
        this.wordList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TrackListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.track_list_item, parent, false);
        return new TrackListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackListViewHolder holder, int position) {
        holder.tvTrackName.setText(trackList.get(position).track.name);
        holder.tvTrackWords.setText(wordList.get(position));
    }

    public void setItem(List<TrackWithWords> items) {
        trackList.clear();
        trackList.addAll(items);
        makeWordList(items);
        notifyDataSetChanged();
        // notifyAll();
    }

    public void deleteItem(int position) {
        trackList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    private void makeWordList(List<TrackWithWords> items) {
        StringBuilder sb;
        for (TrackWithWords item : items) {
            List<Word> objList = item.wordList;
            sb = new StringBuilder();
            sb.append(objList.get(0).word);
            for (int i = 1; i < objList.size(); i++) {
                sb.append(", ").append(objList.get(i).word);
            }
            wordList.add(sb.toString());
        }
    }

    static class TrackListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTrackName;
        TextView tvTrackWords;
        private final ItemClickListener listener;

        public TrackListViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            tvTrackName = itemView.findViewById(R.id.tv_track_name);
            tvTrackWords = itemView.findViewById(R.id.tv_track_words);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getBindingAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
