package com.foolproductions.eyemanga.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foolproductions.eyemanga.MangaViewModel;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChaptersFragment extends Fragment {

    private MangaViewModel mangaViewModel;
    private TextView tvTitle;

    public ChaptersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapters, container, false);
        tvTitle = view.findViewById(R.id.tvChapterTitle);

        mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                tvTitle.setText(manga.getArtist());
            }
        });
        return view;
    }

}
