package com.e.words.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.e.words.R;
import com.e.words.entity.Example;

import java.util.List;
import java.util.Objects;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private final List<Example> examples;
    private final LayoutInflater inflater;

    public ExampleAdapter(Fragment fragment, List<Example> examples) {
        this.inflater = (LayoutInflater) Objects.requireNonNull(fragment.getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.examples = examples;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.example_item_view, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        holder.ctvExample.setText(examples.get(position).example);
        holder.ctvExample.setChecked(examples.get(position).isChecked);
        holder.ctvExample.setOnClickListener(v -> {
            if (!examples.get(position).isChecked) {
                holder.ctvExample.setChecked(true);
                examples.get(position).isChecked = true;
            } else {
                holder.ctvExample.setChecked(false);
                examples.get(position).isChecked = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvExample;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            ctvExample = itemView.findViewById(R.id.ctv_example);
        }
    }
}
