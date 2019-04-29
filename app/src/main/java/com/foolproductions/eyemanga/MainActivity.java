package com.foolproductions.eyemanga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa o Manga Manager!
        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                List<MangaListItem> mangaListItens = MangaManager.getMangaListItens();
                for (int i = 0; i < mangaListItens.size(); i++) {
                    initializeRecyclerView();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }

    void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvMangaList);
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
}
