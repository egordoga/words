package com.e.words.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordWithId;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<WordWithId> wordList;
    private LayoutInflater inflater;
  //  private TrackAdapter.ItemClickListener mListener;
    public List<Long> checkedIds;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public TrackAdapter(Context context/*, TrackAdapter.ItemClickListener listener*/) {
        inflater = LayoutInflater.from(context);
   //     mListener = listener;
        wordList = new ArrayList<>();
        checkedIds = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.track_word_item_view, parent, false);
        return new TrackViewHolder(view/*, mListener*/);
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
                checkedIds.add(wordList.get(position).id);
            } else {
                holder.ctvTrackWord.setChecked(false);
                itemStateArray.put(position, false);
                checkedIds.remove(wordList.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void loadItems(List<WordWithId> items) {
        this.wordList = items;
       // notifyDataSetChanged();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        CheckedTextView ctvTrackWord;

        public TrackViewHolder(@NonNull View itemView/*, TrackAdapter.ItemClickListener listener*/) {
            super(itemView);
     //       mListener = listener;
            ctvTrackWord = itemView.findViewById(R.id.ctv_track_word);
        }

        void bind(int position) {
            if (!itemStateArray.get(position, false)) {
                ctvTrackWord.setChecked(false);
            } else {
                ctvTrackWord.setChecked(true);
            }
        }

//        @Override
//        public void onClick(View v) {
//            mListener.onClick(v, getBindingAdapterPosition());
//        }
    }

//    public interface ItemClickListener {
//        void onClick(View view, int position);
//    }
}
