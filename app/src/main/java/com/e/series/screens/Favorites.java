package com.e.series.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.series.common.Film;
import com.e.series.R;
import com.e.series.adapter.BrowserItem;
import com.e.series.adapter.BrowserListAdapter;
import com.e.series.database.UsrFilmDB;
import com.e.series.database.iGetFilm;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    private View mainID;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private ArrayList<BrowserItem> bList;
    private final String urlPath = "https://image.tmdb.org/t/p/w500/";
    private final String baseURL = "https://api.themoviedb.org/3/";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mainID = findViewById(R.id.mainID);

        getSupportActionBar().hide();
        mainID.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);

        bList = new ArrayList<>();
        recyclerManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerManager);

        recyclerAdapter = new BrowserListAdapter(bList);

        username = getIntent().getStringExtra("username");

        configData();
    }

    public void configData() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UsrFilmDB usrFilmDB = realm.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                for(String str : usrFilmDB.getFavorites())
                    getData(str);

            }
        });

    }

    public void getData(String collection) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(iGetFilm.class)
                .getFilm(collection).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {

                if(response == null)
                    return;

                bList.add(
                        new BrowserItem(
                                response.body().id,
                                (urlPath + response.body().backdrop_path),
                                response.body().budget,
                                response.body().original_language,
                                response.body().original_title,
                                response.body().overview,
                                response.body().popularity,
                                (urlPath + response.body().poster_path),
                                response.body().release_date,
                                response.body().runtime,
                                response.body().tagline,
                                response.body().title,
                                response.body().vote_average,
                                response.body().vote_count
                        )
                );

                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                // No time for failure, brother
            }
        });

    }

    public void showDetail(View view) {

        Intent intent = new Intent(this, Detail.class);

        intent.putExtra("username", username);
        intent.putExtra("id", bList.get(view.getId()).id);
        intent.putExtra("backdrop_path", bList.get(view.getId()).backdrop_path);
        intent.putExtra("budget", bList.get(view.getId()).budget);
        intent.putExtra("original_language", bList.get(view.getId()).original_language);
        intent.putExtra("original_title", bList.get(view.getId()).original_title);
        intent.putExtra("overview", bList.get(view.getId()).overview);
        intent.putExtra("popularity", bList.get(view.getId()).popularity);
        intent.putExtra("release_date", bList.get(view.getId()).release_date);
        intent.putExtra("runtime", bList.get(view.getId()).runtime);
        intent.putExtra("tagline", bList.get(view.getId()).tagline);
        intent.putExtra("title", bList.get(view.getId()).title);
        intent.putExtra("vote_average", bList.get(view.getId()).vote_average);
        intent.putExtra("vote_count", bList.get(view.getId()).vote_count);

        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bList.clear();
        configData();

        getSupportActionBar().hide();
        mainID.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void back(View view) {
        finish();
    }
}
