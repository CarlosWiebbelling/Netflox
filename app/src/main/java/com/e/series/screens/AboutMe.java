package com.e.series.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.e.series.R;

public class AboutMe extends AppCompatActivity {

    private TextView textAboutMe;
    private TextView textAboutTheApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        getSupportActionBar().hide();
        (findViewById(R.id.mainID)).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        textAboutMe = findViewById(R.id.textAboutMe);
        textAboutTheApp = findViewById(R.id.textAboutTheApp);

        textAboutMe.setText("Me chamo Carlos Eduardo Wunsch Wiebbelling, sou discente no curso de Análise e Desenvolvimento de Sistemas" +
                "pelo IFRS - Campus Osório.\n");

        textAboutTheApp.setText("A construção deste aplicativo foi proposta para fins avaliativos na disciplina de Mobile.\n" +
                "Nele, há exemplos de uso das libs Picasso, Gson, Retrofit. Além do uso do banco de dados in memory Realm " +
                "e o uso da plataforma Firebase, da Google, para autenticação dos usuários.\n" +
                "O código fonte está disponível no meu GitHub.");
    }

    public void back(View view) {
        finish();
    }
}
