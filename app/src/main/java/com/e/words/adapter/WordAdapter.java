package com.e.words.adapter;

import android.annotation.SuppressLint;
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
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.fragment.FullWordFragment;

import java.util.List;
import java.util.Objects;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{

    private final FullWordFragment fragment;
    private WordObj wordObj;
    private List<TranslAndEx> taeList;
    private LayoutInflater inflater;
    private RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();

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
        holder.ctvTransl.setText(taeList.get(position).transl);
        holder.ctvTransl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                boolean value = holder.ctvTransl.isChecked();
                if (value) {
                    holder.ctvTransl.setCheckMarkDrawable(R.drawable.checkbox_full);
                    holder.ctvTransl.setVisibility(1);
                }
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

     static class WordViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvTransl;
        TextView tvTranslWe;
        CheckBox cbTranslWe;
        RecyclerView rvExample;
     //   @SuppressLint("ResourceType")
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTranslWe = itemView.findViewById(R.id.tv_transl_we);
            cbTranslWe = itemView.findViewById(R.id.cb_transl_we);
            rvExample = itemView.findViewById(R.id.rv_example);
            ctvTransl = itemView.findViewById(R.id.ctv_transl);
          //  rvExample.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        }
    }
}
