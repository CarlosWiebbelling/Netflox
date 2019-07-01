package com.e.series.animation;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.e.series.screens.Login;
import com.e.series.R;

public class SplashArt extends AppCompatActivity {

    private ImageView splashLogo;
    private ProgressBar progressBar;
    private View mainID;

    //Splash Screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_art);

        splashLogo = findViewById(R.id.splashLogo);
        progressBar = findViewById(R.id.progressBar);
        mainID = findViewById(R.id.mainID);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        splashLogo.startAnimation(animation);
        progressBar.setAnimation(animation);

        getSupportActionBar().hide();
        mainID.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), Login.class));
                finish();
            }
        }, 1500);
    }
}
