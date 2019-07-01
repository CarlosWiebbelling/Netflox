package com.e.series.screens;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.series.R;
import com.e.series.database.UsrFilmDB;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    private View mainID;

    private int id;
    private boolean isFavorite;
    private boolean isInPlaylist;

    private ImageView backdrop_path;
    private TextView release_date;
    private TextView runtime;
    private TextView title;
    private TextView tagline;
    private TextView vote_average;
    private TextView vote_count;
    private TextView overview;
    private TextView original_language;
    private TextView original_title;
    private TextView budget;
    private TextView popularity;

    private ImageView starFavorite;
    private ImageView addToPlaylist;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainID = findViewById(R.id.mainID);
        mainID.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        backdrop_path = findViewById(R.id.backdrop_path);
        release_date = findViewById(R.id.release_date);
        runtime = findViewById(R.id.runtime);
        title = findViewById(R.id.title);
        tagline = findViewById(R.id.tagline);
        vote_average = findViewById(R.id.vote_average);
        vote_count = findViewById(R.id.vote_count);
        overview = findViewById(R.id.overview);
        original_language = findViewById(R.id.original_language);
        original_title = findViewById(R.id.original_title);
        budget = findViewById(R.id.budget);
        popularity = findViewById(R.id.popularity);

        starFavorite = findViewById(R.id.starFavorite);
        addToPlaylist = findViewById(R.id.addToPlaylist);

        id = getIntent().getIntExtra("id", 0);
        username = getIntent().getStringExtra("username");

        Picasso.get().load(getIntent().getStringExtra("backdrop_path")).into(backdrop_path);
        release_date.setText(getIntent().getStringExtra("release_date"));
        runtime.setText(getIntent().getStringExtra("runtime") + "min");
        title.setText(getIntent().getStringExtra("title"));
        vote_average.setText(getIntent().getStringExtra("vote_average"));
        vote_count.setText("votes: " + getIntent().getStringExtra("vote_count"));
        overview.setText(getIntent().getStringExtra("overview"));
        original_language.setText(getIntent().getStringExtra("original_language"));
        original_title.setText(getIntent().getStringExtra("original_title"));
        budget.setText("U$" + getIntent().getStringExtra("budget"));
        popularity.setText(getIntent().getStringExtra("popularity"));

        String tagLine = getIntent().getStringExtra("tagline");

        if(tagLine.length() > 0)
            tagline.setText("\"" + tagLine + "\"");

        else
            tagline.setText("");

        isFavorite();
        isInPlaylist();

        setStarFavoriteResource();
        setPlaylistResource();
    }

    public void isInPlaylist() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UsrFilmDB usrFilmDB = realm.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                if(usrFilmDB.getPlaylist().contains(String.valueOf(id)))
                    isInPlaylist = true;

                else
                    isInPlaylist = false;
            }
        });

        setPlaylistResource();
        realm.close();
    }

    public void isFavorite() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UsrFilmDB usrFilmDB = realm.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                if(usrFilmDB.getFavorites().contains(String.valueOf(id)))
                    isFavorite = true;

                else
                    isFavorite = false;
            }
        });

        setStarFavoriteResource();
        realm.close();
    }

    public void setStarFavoriteResource() {
        if (isFavorite)
            starFavorite.setImageResource(R.drawable.ic_isfavorite);

        else
            starFavorite.setImageResource(R.drawable.ic_isnotfavorite);
    }

    public void setPlaylistResource() {
        if(isInPlaylist)
            addToPlaylist.setImageResource(R.drawable.ic_isinplaylist);

        else
            addToPlaylist.setImageResource(R.drawable.ic_isnotinplaylist);
    }

    public void playlistFilm(View view) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UsrFilmDB usrFilmDB = realm.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                if(usrFilmDB.getPlaylist().contains(String.valueOf(id))) {
                    isInPlaylist = false;
                    usrFilmDB.removeInPlaylist(String.valueOf(id));
                } else {
                    isInPlaylist = true;
                    usrFilmDB.newFilmInPlaylist(String.valueOf(id));
                }

                realm.copyToRealmOrUpdate(usrFilmDB);
            }
        });

        setPlaylistResource();
        realm.close();
    }

    public void favoriteFilm(View view) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UsrFilmDB usrFilmDB = realm.where(UsrFilmDB.class).equalTo("username", username).findFirst();

                if(usrFilmDB.getFavorites().contains(String.valueOf(id))) {
                    isFavorite = false;
                    usrFilmDB.removeFavorite(String.valueOf(id));
                } else {
                    isFavorite = true;
                    usrFilmDB.newFavorite(String.valueOf(id));
                }

                realm.copyToRealmOrUpdate(usrFilmDB);
            }
        });

        setStarFavoriteResource();
        realm.close();
    }

    public void back(View view) {
        finish();
    }

}
