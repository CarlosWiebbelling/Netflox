package com.e.series.screens;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.e.series.common.Film;
import com.e.series.R;
import com.e.series.adapter.BrowserItem;
import com.e.series.adapter.BrowserListAdapter;
import com.e.series.database.UsrFilmDB;
import com.e.series.database.iGetFilm;

import java.util.ArrayList;


public class Browser extends AppCompatActivity {

    private String username;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private ArrayList<BrowserItem> bList;
    private final String urlPath = "https://image.tmdb.org/t/p/w500/";
    private final String baseURL = "https://api.themoviedb.org/3/";
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sidebar);

        drawer_layout = findViewById(R.id.drawer_layout);

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        drawer_layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        username = getIntent().getStringExtra("username");

        recyclerView = findViewById(R.id.recyclerView);

        recyclerManager = new LinearLayoutManager(this);
        bList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerManager);

        recyclerAdapter = new BrowserListAdapter(bList);

        configDB();
        setUser();
        configData();
    }

    public void setUser() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmDB) {
            UsrFilmDB usrFilmDB = realmDB.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                if(usrFilmDB == null) {
                    usrFilmDB = new UsrFilmDB();
                    usrFilmDB.setUsername(username);
                    realmDB.copyToRealm(usrFilmDB);
                }
            }
        });

        realm.close();
    }

    public void configDB() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("Netflox.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    public void hideSidebar(View view) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    public void configData() {
        getData("500");
        getData("501");
        getData("502");
        getData("503");
        getData("504");
        getData("505");
        getData("506");
        getData("507");
        getData("508");
        getData("509");
        getData("600");
        getData("601");
        getData("602");
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
        Intent intent = new Intent(Browser.this, Detail.class);

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

    public void showFavorites(MenuItem item) {
        Intent intent = new Intent(this, Favorites.class);

        intent.putExtra("username", username);

        startActivity(intent);
    }

    public void showPlaylist(MenuItem item) {
        Intent intent = new Intent(this, Playlist.class);

        intent.putExtra("username", username);

        startActivity(intent);
    }

    public void aboutMe(MenuItem item) {
        startActivity(new Intent(this, AboutMe.class));
    }

    public void finish(MenuItem item) {
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawer_layout.closeDrawer(GravityCompat.START);
        getSupportActionBar().hide();
        drawer_layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
