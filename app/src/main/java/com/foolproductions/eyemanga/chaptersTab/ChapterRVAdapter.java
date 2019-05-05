package com.foolproductions.eyemanga.chaptersTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foolproductions.eyemanga.R;

import java.util.List;

public class ChapterRVAdapter extends RecyclerView.Adapter<ChapterRVAdapter.ChapterViewHolder> {

    List<List<String>> chapters;

    public ChapterRVAdapter(List<List<String>> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_details, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.textView.setText(chapters.get(position).get(0) + " " + chapters.get(position).get(2));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvChapter);

            //TODO criar evento de clique
        }
    }
}