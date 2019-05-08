package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.foolproductions.eyemanga.historicDatabase.ReadingHistoric;
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

        setIsLoading(false);
        initializeRecyclerView();
        createFilterChips();

        //TODO deletar isso aqui
        HistoricDAO dao = new HistoricDAO(MainActivity.this);
        List<ReadingHistoric> historics = dao.getList();
        for (ReadingHistoric historic : historics) {
            Log.i("Debbuging", " ID: " + historic.getId());
            Log.i("Debbuging", " Chapter ID " + historic.getChapterId());
            Log.i("Debbuging", "Última página " + String.valueOf(historic.getPage()));
        }
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

    void initializeRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MangaRVAdapter(MangaManager.getMangaListItens());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maintoolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        /*searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Toast.makeText(MainActivity.this, "Focado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Desfocado!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
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
