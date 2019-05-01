package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipGroup = findViewById(R.id.cgMangaFilter);
        recyclerView = findViewById(R.id.rvMangaList);
        progressBar = findViewById(R.id.pbMainActivity);

        setIsLoading(true);

        //Inicializa o Manga Manager!
        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                initializeRecyclerView();
                createFilterChips();
                setIsLoading(false);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }

    void createFilterChips() {
        List<String> categories = MangaManager.getCategories();

        for (String category : categories) {
            final Chip chip = new Chip(MainActivity.this);
            chip.setCheckable(true);
            chip.setText(category);
            chip.setId(View.generateViewId());

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Toast.makeText(MainActivity.this, chip.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            chipGroup.addView(chip);
        }
    }

    void initializeRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MangaRVAdapter(MangaManager.getMangaListItens()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maintoolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String userInput = s.toLowerCase();
                List<MangaListItem> mangaList = MangaManager.getMangaListItens();
                List<MangaListItem> newMangaList = new ArrayList<>();

                for (MangaListItem manga : mangaList) {
                    if (manga.getT().toLowerCase().contains(userInput)) {
                        newMangaList.add(manga);
                    }
                }

                RecyclerView recyclerView = findViewById(R.id.rvMangaList);
                ((MangaRVAdapter) recyclerView.getAdapter()).updateList(newMangaList);

                return true;
            }
        });
        return true;
    }

    void setIsLoading(Boolean isLoading) {
        if (isLoading) {
            recyclerView.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            chipGroup.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
