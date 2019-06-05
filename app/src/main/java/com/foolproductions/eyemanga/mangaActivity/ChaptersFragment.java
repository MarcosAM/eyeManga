package com.foolproductions.eyemanga.mangaActivity;


import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.readActivity.ReadActivity;
import com.foolproductions.eyemanga.util.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChaptersFragment extends Fragment {

    private RecyclerView recyclerView;
    private MangaViewModel mangaViewModel;

    public ChaptersFragment() {

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chapters, container, false);
        recyclerView = view.findViewById(R.id.rvChapters);

        mangaViewModel = ViewModelProviders.of(getActivity()).get(MangaViewModel.class);
        mangaViewModel.getManga().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(final Manga manga) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
                recyclerView.setLayoutManager(layoutManager);
                ChapterRVAdapter adapter = new ChapterRVAdapter(manga.getChapters());
                recyclerView.setAdapter(adapter);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), ReadActivity.class);
                        intent.putExtra(ReadActivity.EXTRA_NAME, manga.getChapters().get(position).get(3));
                        intent.putExtra("serializedManga", manga);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));
                recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(), LinearLayout.VERTICAL));

                recyclerView.setHasFixedSize(true);
            }
        });
        return view;
    }
}
