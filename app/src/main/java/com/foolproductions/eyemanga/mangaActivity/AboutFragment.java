package com.foolproductions.eyemanga.mangaActivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.readActivity.ReadActivity;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.StringEscapeUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private ImageView ivCover;
    private TextView tvTitle, tvCategories, tvRelease, tvAuthor, tvArtist, tvDescription;
    private CardView cvDetails, cvDescription;
    private ProgressBar progressBar;
    private Button btnContinue;
    private Manga manga;

    public AboutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        findViews(view);
        setIsLoading(true);

        MangaViewModel mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                initializeViews(manga);
                setIsLoading(false);
            }
        });
        return view;
    }

    private void findViews(View view) {
        ivCover = view.findViewById(R.id.ivAboutCover);
        tvTitle = view.findViewById(R.id.tvAboutTitle);
        tvCategories = view.findViewById(R.id.tvAboutCategories);
        tvRelease = view.findViewById(R.id.tvAboutRelease);
        tvAuthor = view.findViewById(R.id.tvAboutAuthor);
        tvArtist = view.findViewById(R.id.tvAboutArtist);
        tvDescription = view.findViewById(R.id.tvAboutDescription);
        cvDetails = view.findViewById(R.id.cvAboutDetails);
        cvDescription = view.findViewById(R.id.cvAboutDescription);
        progressBar = view.findViewById(R.id.pbAbout);
        btnContinue = view.findViewById(R.id.btnContinue);
    }

    private void initializeViews(final Manga manga) {
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

        if (manga.getChapters().size() <= 0) {
            btnContinue.setVisibility(View.GONE);
        } else {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startContinueReading();
                }
            });
        }
        this.manga = manga;
    }

    private void startContinueReading() {
        Intent intent = new Intent(getContext(), ReadActivity.class);
        intent.putExtra("serializedManga", manga);
        startActivity(intent);
    }

    private void setIsLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            cvDetails.setVisibility(View.GONE);
            cvDescription.setVisibility(View.GONE);
            btnContinue.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            cvDetails.setVisibility(View.VISIBLE);
            cvDescription.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.VISIBLE);
        }
    }
}