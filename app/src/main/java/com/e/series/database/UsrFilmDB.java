package com.e.series.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UsrFilmDB extends RealmObject {

    @PrimaryKey
    private String username;
    private RealmList<String> playlist = new RealmList<>();
    private RealmList<String> favorites = new RealmList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RealmList<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(RealmList<String> playlist) {
        this.playlist = playlist;
    }

    public RealmList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(RealmList<String> favorites) {
        this.favorites = favorites;
    }

    public void newFavorite(String favorite) {
        favorites.add(favorite);
    }

    public void removeFavorite(String favorite) {
        favorites.remove(favorite);
    }

    public void newFilmInPlaylist(String film) {
        playlist.add(film);
    }

    public void removeInPlaylist(String film) {
        favorites.remove(film);
    }
}
