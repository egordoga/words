package com.e.words.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.fragment.FullWordFragment;

import java.util.List;
import java.util.Objects;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{

    private final FullWordFragment fragment;
    private WordObj wordObj;
    private List<TranslAndEx> taeList;
    private LayoutInflater inflater;

    public WordAdapter(FullWordFragment fragment, WordObj wordObj) {
        this.fragment = fragment;
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.taeList = wordObj.translations;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_example_item_view, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.tvTranslWe.setText(taeList.get(position).transl);
        ExampleAdapter adapter = new ExampleAdapter(fragment, taeList.get(position).examples);
        holder.rvExample.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return taeList.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvTranslWe;
        CheckBox cbTranslWe;
        RecyclerView rvExample;
        @SuppressLint("ResourceType")
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTranslWe = itemView.findViewById(R.id.tv_transl_we);
            cbTranslWe = itemView.findViewById(R.id.cb_transl_we);
            rvExample = itemView.findViewById(R.layout.example_item_view);
        }
    }
}
