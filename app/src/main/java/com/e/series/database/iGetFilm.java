package com.e.series.database;

import com.e.series.common.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface iGetFilm {

    @GET("https://api.themoviedb.org/3/movie/{collection}?api_key=7300a46e176a6ee42f3af71bea58abee")
    Call<Film> getFilm(@Path("collection") String collection);
}
