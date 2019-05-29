package com.foolproductions.eyemanga.fragments;


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

import com.foolproductions.eyemanga.MangaActivity;
import com.foolproductions.eyemanga.MangaViewModel;
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
    private MangaViewModel mangaViewModel;
    //private ReadingHistoric historic;

    public AboutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        findViews(view);
        setIsLoading(true);

        mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                initializeViews(manga);
                setIsLoading(false);
            }
        });
        return view;
    }

    void findViews(View view) {
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

    void initializeViews(final Manga manga) {
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

        //TODO organizar isso aqui
        /*HistoricDAO dao = new HistoricDAO(getContext());
        String mangaId = getActivity().getIntent().getStringExtra(MangaActivity.EXTRA_NAME);
        if (dao.checkIfExists(mangaId)) {
            btnContinue.setText(getString(R.string.continue_reading));
            historic = dao.getReadingHistoric(mangaId);
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    continueReadingFromLastPage();
                }
            });
        } else {
            if (manga.getChapters().size() > 0) {
                btnContinue.setText(getString(R.string.start_reading));
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO trasformar esse 3 para acessar a id do capítulo em uma variável talvez
                        startReadingFromFirstChapter(manga.getChapters().get(manga.getChapters().size() - 1).get(3));
                    }
                });
            } else {
                //TODO pensar num feedback ao usuário de porque esse manga não tem capítulo algum
                btnContinue.setVisibility(View.GONE);
            }
        }*/

        this.manga = manga;
    }

    public void startContinueReading() {
        Intent intent = new Intent(getContext(), ReadActivity.class);
        intent.putExtra(MangaActivity.EXTRA_NAME, getActivity().getIntent().getStringExtra(MangaActivity.EXTRA_NAME));
        intent.putExtra("serializedManga", manga);
        startActivity(intent);
    }

    /*public void startReadingFromFirstChapter(String chapterId) {
        Intent intent = new Intent(getContext(), ReadActivity.class);
        //TODO refatorar isso aqui essa função e a continueReading... são muito parecidas
        intent.putExtra(ReadActivity.EXTRA_NAME, chapterId);
        intent.putExtra(MangaActivity.EXTRA_NAME, getActivity().getIntent().getStringExtra(MangaActivity.EXTRA_NAME));
        intent.putExtra("serializedManga", manga);
        startActivity(intent);
    }

    public void continueReadingFromLastPage() {
        //TODO reaproveitar o código acima
        if (historic != null) {
            Intent intent = new Intent(getContext(), ReadActivity.class);
            intent.putExtra(ReadActivity.EXTRA_NAME, historic.getChapterId());
            intent.putExtra(MangaActivity.EXTRA_NAME, historic.getId());
            intent.putExtra(ReadActivity.EXTRA_PAGE, historic.getPage());
            intent.putExtra("serializedManga", manga);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Histórico nulo, fuck!", Toast.LENGTH_SHORT).show();
        }
    }*/

    void setIsLoading(Boolean isLoading) {
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