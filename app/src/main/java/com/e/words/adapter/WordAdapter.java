package com.e.words.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
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
   // private SparseBooleanArray itemStateArray= new SparseBooleanArray();

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
        holder.ctvTranslWe.setText(taeList.get(position).transl);
       // holder.ctvTranslWe.setChecked(itemStateArray.get(position));
        holder.ctvTranslWe.setChecked(taeList.get(position).isChecked);
        holder.ctvTranslWe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if (!itemStateArray.get(position, false)) {
                if (!taeList.get(position).isChecked) {
                    holder.ctvTranslWe.setChecked(true);
                 //   itemStateArray.put(position, true);
                    taeList.get(position).isChecked = true;
                }
                else  {
                    holder.ctvTranslWe.setChecked(false);
                //    itemStateArray.put(position, false);
                    taeList.get(position).isChecked = false;
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

    void loadItems(List<TranslAndEx> items) {
        this.taeList = items;
        notifyDataSetChanged();
    }

      class WordViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{
        CheckedTextView ctvTranslWe;
        TextView tvTranslWe;
        CheckBox cbTranslWe;
        RecyclerView rvExample;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTranslWe = itemView.findViewById(R.id.tv_transl_we);
            cbTranslWe = itemView.findViewById(R.id.cb_transl_we);
            rvExample = itemView.findViewById(R.id.rv_example);
            ctvTranslWe = itemView.findViewById(R.id.ctv_transl);
        }

//         @Override
//         public void onClick(View v) {
//             int adapterPosition = getAdapterPosition();
//             if (!itemStateArray.get(adapterPosition, false)) {
//                 ctvTransl.setChecked(true);
//                 itemStateArray.put(adapterPosition, true);
//             }
//             else  {
//                 ctvTransl.setChecked(false);
//                 itemStateArray.put(adapterPosition, false);
//             }
//         }
     }
}
