package com.foolproductions.eyemanga.mangaActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foolproductions.eyemanga.R;

import java.util.List;

public class ChapterRVAdapter extends RecyclerView.Adapter<ChapterRVAdapter.ChapterViewHolder> {

    private List<List<String>> chapters;

    ChapterRVAdapter(List<List<String>> chapters) {
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
        if (chapters.get(position).get(2) != null)
            holder.textView.setText(chapters.get(position).get(0) + " " + chapters.get(position).get(2));
        else
            holder.textView.setText(chapters.get(position).get(0));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ChapterViewHolder(@NonNull final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvChapter);
        }
    }
}