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
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.fragment.FullWordFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{

    private final FullWordFragment fragment;
    private List<WordObj> wordObjList;
    private LayoutInflater inflater;

    public WordAdapter(FullWordFragment fragment) {
        this.fragment = fragment;
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wordObjList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_example_item_view, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return wordObjList.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvWordWe;
        RecyclerView rvExample;
        CheckBox cbWordWe;
        @SuppressLint("ResourceType")
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWordWe = itemView.findViewById(R.id.tv_word_we);
            cbWordWe = itemView.findViewById(R.id.cb_word_we);
            rvExample = itemView.findViewById(R.layout.example_item_view);
        }
    }
}
