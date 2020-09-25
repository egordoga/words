package com.e.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.e.words.fragment.FullWordFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private FullWordFragment fragment;
    private List<TranslationAndExample> taeList = new LinkedList<>();
    private LayoutInflater inflater;
    private RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();

    public WordAdapter(FullWordFragment fragment) {
        this.fragment = fragment;
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_example_item_view, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
//        holder.tvTranslWe.setText(taeList.get(position).transl);
        holder.ctvTranslWe.setText(taeList.get(position).translation.translation /*+ "   " + taeList.get(position).index*/);
        holder.ctvTranslWe.setChecked(taeList.get(position).translation.isChecked);
        holder.ctvTranslWe.setOnClickListener(v -> {
            if (!taeList.get(position).translation.isChecked) {
                holder.ctvTranslWe.setChecked(true);
                taeList.get(position).translation.isChecked = true;
            } else {
                holder.ctvTranslWe.setChecked(false);
                taeList.get(position).translation.isChecked = false;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvExample.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(taeList.get(position).examples.size());
        ExampleAdapter adapter = new ExampleAdapter(fragment, taeList.get(position).examples);
        holder.rvExample.setLayoutManager(layoutManager);
        holder.rvExample.setAdapter(adapter);
        holder.rvExample.setRecycledViewPool(pool);
    }

    @Override
    public int getItemCount() {
        return taeList.size();
    }

    public void loadItems(List<TranslationAndExample> items) {
        this.taeList = items;
        notifyDataSetChanged();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvTranslWe;
        TextView tvTranslWe;
        CheckBox cbTranslWe;
        RecyclerView rvExample;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
           // tvTranslWe = itemView.findViewById(R.id.tv_transl_we);
           // cbTranslWe = itemView.findViewById(R.id.cb_transl_we);
            rvExample = itemView.findViewById(R.id.rv_example);
            ctvTranslWe = itemView.findViewById(R.id.ctv_transl);
        }
    }
}
