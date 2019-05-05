package com.foolproductions.eyemanga.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foolproductions.eyemanga.MangaViewModel;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.StringEscapeUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaDetailsFragment extends Fragment {

    private ImageView ivCover;
    private TextView tvTitle, tvCategories, tvRelease, tvAuthor, tvArtist, tvDescription;
    private MangaViewModel mangaViewModel;

    public MangaDetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_details, container, false);
        ivCover = view.findViewById(R.id.ivAboutCover);
        tvTitle = view.findViewById(R.id.tvAboutTitle);
        tvCategories = view.findViewById(R.id.tvAboutCategories);
        tvRelease = view.findViewById(R.id.tvAboutRelease);
        tvAuthor = view.findViewById(R.id.tvAboutAuthor);
        tvArtist = view.findViewById(R.id.tvAboutArtist);
        tvDescription = view.findViewById(R.id.tvAboutDescription);

        mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                Picasso.get().load(MangaEdenURLs.IMAGE_URL + manga.getImage()).placeholder(R.drawable.placeholder_cover).into(ivCover);
                tvTitle.setText(manga.getTitle());

                if (manga.getCategories().size() > 0) {
                    String categories = "";

                    for (String category : manga.getCategories()) {
                        categories += category + " ";
                    }
                    tvCategories.setText(categories);
                }

                tvRelease.setText(manga.getReleased());
                tvAuthor.setText(StringEscapeUtils.unescapeHtml4(manga.getAuthor()));
                if (!manga.getAuthor().equals(manga.getArtist())) {
                    tvArtist.setText(StringEscapeUtils.unescapeHtml4(manga.getArtist()));
                } else {
                    tvArtist.setVisibility(View.GONE);
                }
                tvDescription.setText(StringEscapeUtils.unescapeHtml4(manga.getDescription()));
            }
        });
        return view;
    }

}
