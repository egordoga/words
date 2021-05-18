package com.e.words.view.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<Word> wordList;
    private final LayoutInflater inflater;
    public List<Word> checkedWords;
    private final SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private final ItemClickListener mListener;

    public TrackAdapter(List<Word> checkedWords, Context context, TrackAdapter.ItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        wordList = new ArrayList<>();
        this.checkedWords = checkedWords;
        mListener = listener;
    }

    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.track_word_item_view, parent, false);
        return new TrackViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.TrackViewHolder holder, int position) {
        holder.bind(position);
        holder.ctvTrackWord.setText(wordList.get(position).word);
        holder.ctvTrackWord.setOnClickListener(v -> {
            boolean value = holder.ctvTrackWord.isChecked();
            if (!value) {
                holder.ctvTrackWord.setChecked(true);
                itemStateArray.put(position, true);
                checkedWords.add(wordList.get(position));
            } else {
                holder.ctvTrackWord.setChecked(false);
                itemStateArray.put(position, false);
                checkedWords.remove(wordList.get(position));
            }
            mListener.onClick(v, position);
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void loadItems(List<Word> items) {
        this.wordList = items;
        // notifyDataSetChanged();
    }

    public void deleteChecked() {
        for (Word checkedWord : checkedWords) {
            int position = wordList.indexOf(checkedWord);
            wordList.remove(position);
            notifyItemRemoved(position);
        }
        checkedWords.clear();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckedTextView ctvTrackWord;
        private final ItemClickListener mListener;

        public TrackViewHolder(@NonNull View itemView, TrackAdapter.ItemClickListener listener) {
            super(itemView);
            mListener = listener;
            ctvTrackWord = itemView.findViewById(R.id.ctv_track_word);
        }

        void bind(int position) {
            ctvTrackWord.setChecked(itemStateArray.get(position, false));
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getBindingAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
