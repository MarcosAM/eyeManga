package com.foolproductions.eyemanga;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MangaRVAdapter extends RecyclerView.Adapter<MangaRVAdapter.MangaViewHolder> implements Filterable {

    private List<MangaListItem> allMangas = new ArrayList<>();
    private List<MangaListItem> mangas = new ArrayList<>();
    private List<String> selectedCategories = new ArrayList<>();

    public MangaRVAdapter(List<MangaListItem> mangas) {
        this.mangas.clear();
        this.mangas.addAll(mangas);
        this.allMangas.clear();
        this.allMangas.addAll(MangaManager.getMangaListItens());
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
        Picasso.get().load(MangaEdenURLs.IMAGE_URL + mangas.get(i).getIm()).placeholder(R.drawable.placeholder_cover).into(mangaViewHolder.cover);
        mangaViewHolder.mangaId = mangas.get(i).getI();
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }

    @Override
    public Filter getFilter() {
        return titleFilter;
    }

    Filter titleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MangaListItem> filteredMangas = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                if (selectedCategories.size() > 0) {
                    for (MangaListItem manga : allMangas) {
                        if (manga.getC().containsAll(selectedCategories)) {
                            filteredMangas.add(manga);
                        }
                    }
                } else {
                    filteredMangas.addAll(allMangas);
                }
            } else {
                String filter = charSequence.toString().toLowerCase().trim();
                if (selectedCategories.size() > 0) {
                    for (MangaListItem manga : allMangas) {
                        if (manga.getT().toLowerCase().contains(filter) && manga.getC().containsAll(selectedCategories)) {
                            filteredMangas.add(manga);
                        }
                    }
                } else {
                    for (MangaListItem manga : allMangas) {
                        if (manga.getT().toLowerCase().contains(filter)) {
                            filteredMangas.add(manga);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredMangas;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mangas.clear();
            mangas.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void addCategoryFilter(String category, CharSequence searchInput) {
        if (MangaManager.getCategories().contains(category)) {
            selectedCategories.add(category);
            titleFilter.filter(searchInput);
        }
    }

    public void removeCategoryFilter(String category, CharSequence searchInput) {
        if (selectedCategories.contains(category)) {
            selectedCategories.remove(category);
            titleFilter.filter(searchInput);
        }
    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView cover;
        String mangaId;

        public MangaViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            cover = itemView.findViewById(R.id.ivCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), MangaActivity.class);
                    intent.putExtra(MangaActivity.EXTRA_NAME, mangaId);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}