package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.foolproductions.eyemanga.historicDatabase.HistoricDAO;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    MangaRVAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipGroup = findViewById(R.id.cgMangaFilter);
        recyclerView = findViewById(R.id.rvMangaList);
        progressBar = findViewById(R.id.pbMainActivity);

        initializeRecyclerView();
        createFilterChips();
        setIsSearching(false);

        //TODO deletar isso aqui
        HistoricDAO dao = new HistoricDAO(MainActivity.this);
        List<MangaListItem> mangas = dao.getList();
        for (MangaListItem manga : mangas) {
            Log.i("Debbuging", " ID: " + manga.getI());
            Log.i("Debbuging", " Title: " + manga.getT());
            Log.i("Debbuging", " Image: " + manga.getIm());
            Log.i("Debbuging", " Chapter ID " + manga.getChapterId());
            Log.i("Debbuging", "Última página " + String.valueOf(manga.getPage()));
        }
    }

    void initializeRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MangaRVAdapter(MangaManager.getMangaListItens(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
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
                        adapter.addCategoryFilter(chip.getText().toString(), searchView.getQuery());
                    } else {
                        adapter.removeCategoryFilter(chip.getText().toString(), searchView.getQuery());
                    }
                }
            });

            chipGroup.addView(chip);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maintoolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setIsSearching(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setIsSearching(false);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    void setIsSearching(boolean isSearching) {
        if (isSearching) {
            chipGroup.setVisibility(View.VISIBLE);
        } else {
            chipGroup.setVisibility(View.GONE);
        }
        adapter.setIsSearching(isSearching);
    }

    /*void setIsLoading(Boolean isLoading) {
        if (isLoading) {
            recyclerView.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            chipGroup.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }*/
}
