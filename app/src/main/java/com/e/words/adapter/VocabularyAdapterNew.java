package com.e.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;

import java.util.ArrayList;
import java.util.List;

public class VocabularyAdapterNew extends RecyclerView.Adapter<VocabularyAdapterNew.VocabularyViewHolderNew> {

    private final List<WordObj> wordList;
    private final LayoutInflater inflater;
    private final ItemClickListenerNew mListener;

    public VocabularyAdapterNew(Context context, ItemClickListenerNew listener) {
        inflater = LayoutInflater.from(context);
        mListener = listener;
        wordList = new ArrayList<>();
    }

    @NonNull
    @Override
    public VocabularyViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.vocab_item_view, parent, false);
        return new VocabularyViewHolderNew(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyViewHolderNew holder, int position) {
        holder.tvWord.setText(wordList.get(position).word.word);
        holder.tvTranscr.setText(wordList.get(position).word.transcript);
        holder.tvTransl.setText(wordList.get(position).translations.get(0).translation.translation);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }


    public void setItem(List<WordObj> items) {
        wordList.addAll(items);
     //   notifyDataSetChanged();
        // notifyDataSetChanged();
      //   notifyAll();
    }

    public void deleteItem(int position) {
        wordList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, wordList.size());
      //  notifyAll();
    }

    static class VocabularyViewHolderNew extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvWord;
        TextView tvTranscr;
        TextView tvTransl;

        private final ItemClickListenerNew mListener;

        public VocabularyViewHolderNew(@NonNull View itemView, ItemClickListenerNew listener) {
            super(itemView);

            mListener = listener;
            itemView.setOnClickListener(this);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvTranscr = itemView.findViewById(R.id.tv_transcr);
            tvTransl = itemView.findViewById(R.id.tv_transl);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getBindingAdapterPosition());
        }
    }

    public interface ItemClickListenerNew {
        void onClick(View view, int position);
    }
}
