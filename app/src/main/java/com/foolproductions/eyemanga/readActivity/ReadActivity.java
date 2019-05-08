package com.foolproductions.eyemanga.readActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.foolproductions.eyemanga.MangaActivity;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.historicDatabase.HistoricDAO;
import com.foolproductions.eyemanga.historicDatabase.ReadingHistoric;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "chapterId";

    private RecyclerView recyclerView;
    private ReadViewModel readViewModel;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView = findViewById(R.id.rvRead);
        recyclerView.setLayoutManager(layoutManager);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        readViewModel.setChapterId(getIntent().getStringExtra(EXTRA_NAME));
        readViewModel.getChapter().observe(this, new Observer<Chapter>() {
            @Override
            public void onChanged(Chapter chapter) {
                ReadRVAdapter adapter = new ReadRVAdapter(chapter.getImages());
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        int page = -1;
        try {
            page = layoutManager.findFirstVisibleItemPosition();
        } catch (Exception e) {

        } finally {
            if (page > 0) {
                HistoricDAO historicDAO = new HistoricDAO(getApplicationContext());
                ReadingHistoric historic = new ReadingHistoric();
                historic.setId(getIntent().getStringExtra(MangaActivity.EXTRA_NAME));
                historic.setChapterId(getIntent().getStringExtra(EXTRA_NAME));
                historic.setPage(page);
                //TODO essa verificação deveria ser o próprio DAO que deveria fazer
                if (historicDAO.checkIfExists(historic.getId())) {
                    historicDAO.update(historic);
                    Toast.makeText(this, "Uplodei pq vc já leu esse manga", Toast.LENGTH_SHORT).show();
                } else {
                    historicDAO.save(historic);
                    Toast.makeText(this, "Salvei pq vc não tinha lido esse manga", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onDestroy();
    }
}
