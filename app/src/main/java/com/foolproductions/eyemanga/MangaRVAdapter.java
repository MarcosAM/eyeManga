package com.foolproductions.eyemanga;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaRVAdapter extends RecyclerView.Adapter<MangaRVAdapter.MangaViewHolder> {

    private List<MangaListItem> mangas;

    public MangaRVAdapter(List<MangaListItem> mangas) {
        this.mangas = mangas;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_manga, viewGroup, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder mangaViewHolder, int i) {
        mangaViewHolder.title.setText(mangas.get(i).getT());
        Picasso.get().load(MangaEdenURLs.IMAGE_URL + mangas.get(i).getIm()).into(mangaViewHolder.cover);
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView cover;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            cover = itemView.findViewById(R.id.ivCover);
        }
    }
}