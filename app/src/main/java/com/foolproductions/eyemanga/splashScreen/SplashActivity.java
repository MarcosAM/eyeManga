package com.foolproductions.eyemanga.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.foolproductions.eyemanga.MainActivity;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

public class SplashActivity extends AppCompatActivity {

    LinearLayout llSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        llSplashScreen = findViewById(R.id.llSplashScreen);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        llSplashScreen.startAnimation(fadeIn);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);

        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                llSplashScreen.startAnimation(fadeOut);
                fadeOut.setDuration(600);
                fadeOut.setFillAfter(true);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        goToMainActivity();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onFailure() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}