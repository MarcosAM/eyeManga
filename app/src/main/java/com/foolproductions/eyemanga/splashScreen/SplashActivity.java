package com.foolproductions.eyemanga.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.foolproductions.eyemanga.MainActivity;
import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

public class SplashActivity extends AppCompatActivity {

    LinearLayout llSplashScreen;
    LinearLayout llSplashScreenFailed;

    Button btTryAgain;
    ProgressBar pbTryingAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        llSplashScreen = findViewById(R.id.llSplashScreen);
        llSplashScreenFailed = findViewById(R.id.llSplashScreenFailed);
        btTryAgain = findViewById(R.id.btSplashTryAgain);
        pbTryingAgain = findViewById(R.id.pbSplashTryingAgain);

        btTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToConnectAgain();
            }
        });

        playFadeInAnimation();

        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                playFadeOutAnimation(new Animation.AnimationListener() {
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
                llSplashScreen.setVisibility(View.GONE);
                llSplashScreenFailed.setVisibility(View.VISIBLE);
            }
        });
    }

    private void playFadeInAnimation() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        llSplashScreen.startAnimation(fadeIn);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
    }

    private void playFadeOutAnimation(Animation.AnimationListener listener) {
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        llSplashScreen.startAnimation(fadeOut);
        fadeOut.setDuration(600);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(listener);
    }

    private void goToMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void tryToConnectAgain() {
        btTryAgain.setVisibility(View.GONE);
        pbTryingAgain.setVisibility(View.VISIBLE);

        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                goToMainActivity();
            }

            @Override
            public void onFailure() {
                btTryAgain.setVisibility(View.VISIBLE);
                pbTryingAgain.setVisibility(View.GONE);
            }
        });
    }
}