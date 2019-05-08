package com.foolproductions.eyemanga.chaptersTab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foolproductions.eyemanga.MangaActivity;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.readActivity.ReadActivity;

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
        //holder.chapterId = chapters.get(position).get(3);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //String chapterId;

        public ChapterViewHolder(@NonNull final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvChapter);
            //TODO deletar isso aqui
            /*
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ReadActivity.class);
                    intent.putExtra(ReadActivity.EXTRA_NAME, chapterId);
                    itemView.getContext().startActivity(intent);
                }
            });*/
        }
    }
}