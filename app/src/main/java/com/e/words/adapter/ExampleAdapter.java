package com.e.words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.ExampleDto;
import com.e.words.fragment.FullWordFragment;

import java.util.List;
import java.util.Objects;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private List<ExampleDto> exampleDtos;
    private LayoutInflater inflater;

    public ExampleAdapter(FullWordFragment fragment, List<ExampleDto> exampleDtos) {
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.exampleDtos = exampleDtos;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.example_item_view, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        holder.ctvExample.setText(exampleDtos.get(position).example /*+ "   " + examples.get(position).index*/);
        holder.ctvExample.setChecked(exampleDtos.get(position).isChecked);
        holder.ctvExample.setOnClickListener(v -> {
            if (!exampleDtos.get(position).isChecked) {
                holder.ctvExample.setChecked(true);
                exampleDtos.get(position).isChecked = true;
            } else {
                holder.ctvExample.setChecked(false);
                exampleDtos.get(position).isChecked = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return exampleDtos.size();
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvExample;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            ctvExample = itemView.findViewById(R.id.ctv_example);
        }
    }
}
