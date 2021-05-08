package com.e.words.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.entity.entityNew.Word;

import java.util.List;

public class TrackAdapterNew extends RecyclerView.Adapter<TrackAdapterNew.TrackAdapterViewHolderNew> {

    @NonNull
    private OnItemCheckListener onItemClick;
    private List<Word> words;


    public TrackAdapterNew(List<Word> words, @NonNull OnItemCheckListener onItemClick) {
        this.onItemClick = onItemClick;
        this.words = words;
    }

    @NonNull
    @Override
    public TrackAdapterViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapterViewHolderNew holder, int position) {
        Word currentWord = words.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TrackAdapterViewHolderNew extends RecyclerView.ViewHolder {
        public TrackAdapterViewHolderNew(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface OnItemCheckListener {
        void onItemCheck(Word word);
        void onItemUncheck(Word word);
    }
}
