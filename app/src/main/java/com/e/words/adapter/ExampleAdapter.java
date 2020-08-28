package com.e.words.adapter;

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

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>{
    private FullWordFragment fragment;
    private List<WordObj> wordObjList;
    private LayoutInflater inflater;

    public ExampleAdapter(FullWordFragment fragment, List<WordObj> wordObjList) {
        this.fragment = fragment;
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wordObjList = wordObjList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.example_item_view, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return wordObjList.;
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView tvExample;
        CheckBox cbExample;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExample = itemView.findViewById(R.id.tv_example);
            cbExample = itemView.findViewById(R.id.cb_example);
        }
    }
}
