package com.foolproductions.eyemanga.readActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.foolproductions.eyemanga.MangaActivity;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.historicDatabase.HistoricDAO;
import com.foolproductions.eyemanga.historicDatabase.ReadingHistoric;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "chapterId";
    public static final String EXTRA_PAGE = "page";

    private RecyclerView recyclerView;
    private Button btnNextChapter;
    private ReadViewModel readViewModel;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);

    private boolean isAtBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btnNextChapter = findViewById(R.id.btnNextChapter);
        btnNextChapter.setVisibility(View.GONE);
        btnNextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readViewModel.goToNextChapter()) {
                    btnNextChapter.setVisibility(View.GONE);
                } else {
                    //TODO relatar que deu erro!
                }
            }
        });
        recyclerView = findViewById(R.id.rvRead);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                setAtBottom(!recyclerView.canScrollVertically(1));
            }
        });

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        readViewModel.initIfNecessery(
                getApplicationContext(),
                (Manga) getIntent().getSerializableExtra("serializedManga"),
                getIntent().getStringExtra(EXTRA_NAME));
        readViewModel.getChapter().observe(this, new Observer<Chapter>() {
            @Override
            public void onChanged(Chapter chapter) {
                ReadRVAdapter adapter = new ReadRVAdapter(chapter.getImages());
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                //TODO só fazer isso quando o chapter id do intent for igual ao atual porque quando atualizar o novo capítulo pode reiniciar o recycler view
                //TODO no mesmo canto que o cappítulo salvo e não é isso que a gente quer!
                //TODO ver isso aqui
                recyclerView.scrollToPosition(readViewModel.getStartingPage());
                /*if (getIntent().getIntExtra(EXTRA_PAGE, 0) > 0) {
                    recyclerView.scrollToPosition(getIntent().getIntExtra(EXTRA_PAGE, 0));
                }*/
            }
        });
    }

    private void setAtBottom(boolean bottom) {
        if (isAtBottom == bottom) {
            return;
        }
        Log.i("Debbuging", "Alterei isArBottom porque é bottom é " + bottom + " e atBottom é " + isAtBottom);
        isAtBottom = bottom;
        if (bottom) {
            if (readViewModel.hasNextChapter()) {
                btnNextChapter.setVisibility(View.VISIBLE);
            }
        } else {
            btnNextChapter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        int page = -1;
        try {
            page = layoutManager.findFirstVisibleItemPosition();
        } catch (Exception e) {

        } finally {
            if (page >= 0) {
                ReadingHistoric historic = new ReadingHistoric();
                historic.setId(getIntent().getStringExtra(MangaActivity.EXTRA_NAME));
                historic.setChapterId(getIntent().getStringExtra(EXTRA_NAME));
                historic.setPage(page);
                readViewModel.getHistoricDAO().save(historic);
            }
        }
        super.onDestroy();
    }
}
