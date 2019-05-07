package com.foolproductions.eyemanga.readActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadRVAdapter extends RecyclerView.Adapter<ReadRVAdapter.ReadViewHolder> {

    List<List<String>> images;

    public ReadRVAdapter(List<List<String>> images) {
        this.images = new ArrayList<>();
        this.images.addAll(images);
        Collections.reverse(this.images);
    }

    @NonNull
    @Override
    public ReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_page, parent, false);
        return new ReadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReadViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.photoView.setVisibility(View.GONE);
        Picasso.get()
                .load(MangaEdenURLs.IMAGE_URL + images.get(position).get(1))
                .transform(new CheckForRotationTransformation()).error(R.drawable.placeholder_cover).
                into(holder.photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.photoView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        //TODO mudar baseado nas possíveis exceções
                    }
                });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ReadViewHolder extends RecyclerView.ViewHolder {
        private PhotoView photoView;
        private ProgressBar progressBar;

        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);

            photoView = itemView.findViewById(R.id.pvMangaPage);
            progressBar = itemView.findViewById(R.id.pbPage);
        }
    }
}
