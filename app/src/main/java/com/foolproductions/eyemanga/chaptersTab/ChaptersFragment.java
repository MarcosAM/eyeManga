package com.foolproductions.eyemanga.chaptersTab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.foolproductions.eyemanga.MangaRVAdapter;
import com.foolproductions.eyemanga.MangaViewModel;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChaptersFragment extends Fragment {

    private RecyclerView recyclerView;
    private MangaViewModel mangaViewModel;

    public ChaptersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chapters, container, false);
        recyclerView = view.findViewById(R.id.rvChapters);

        mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
                recyclerView.setLayoutManager(layoutManager);
                ChapterRVAdapter adapter = new ChapterRVAdapter(manga.getChapters());
                recyclerView.setAdapter(adapter);

                recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(), LinearLayout.VERTICAL));

                recyclerView.setHasFixedSize(true);
            }
        });
        return view;
    }

}
