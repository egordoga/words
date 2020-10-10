package com.e.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.DataTest;
import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.VocabularyDto;
import com.e.words.entity.ForeignWordWithTranslate;
import com.e.words.entity.Translate;

import java.util.ArrayList;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>
        implements ItemTouchHelperAdapter{

    private List<VocabularyDto> wordList;
    private LayoutInflater inflater;
    private ItemClickListener mListener;

    public VocabularyAdapter(Context context, ItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        mListener = listener;
        wordList = new ArrayList<>();
    }

    @NonNull
    @Override
    public VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.vocab_item_view, parent, false);
        return new VocabularyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyViewHolder holder, int position) {
        holder.tvWord.setText(wordList.get(position).word);
        holder.tvTranscr.setText(wordList.get(position).transcript);
        holder.tvTransl.setText(wordList.get(position).translate);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    @Override
    public void onItemDelete(int position) {

    }

    public void setItem(List<VocabularyDto> items) {
        wordList.addAll(items);
        notifyDataSetChanged();
    }

    static class VocabularyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvWord;
        TextView tvTranscr;
        TextView tvTransl;

        private ItemClickListener mListener;

        public VocabularyViewHolder(@NonNull View itemView, ItemClickListener listener) {
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

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

//    private String makeTranslateString(List<Translate> translate) {
//        StringBuilder sb = new StringBuilder();
//        for (Translate s : translate) {
//            sb.append(s.word).append(", ");
//        }
//        sb.delete(sb.length() - 2, sb.length());
//       // sb.substring(0, sb.length() - 3);
//        return sb.toString();
//    }
}
