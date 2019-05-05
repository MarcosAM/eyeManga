package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.foolproductions.eyemanga.fragments.AboutFragment;
import com.foolproductions.eyemanga.chaptersTab.ChaptersFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MangaActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "id";

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;

    private MangaViewModel mangaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        viewPager = findViewById(R.id.viewpager);
        smartTabLayout = findViewById(R.id.viewpagertab);

        mangaViewModel = ViewModelProviders.of(this).get(MangaViewModel.class);
        mangaViewModel.setMangaId(getIntent().getStringExtra(EXTRA_NAME));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        //TODO pegar essas strings dos values
                        .add("About", AboutFragment.class)
                        .add("Chapters", ChaptersFragment.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}