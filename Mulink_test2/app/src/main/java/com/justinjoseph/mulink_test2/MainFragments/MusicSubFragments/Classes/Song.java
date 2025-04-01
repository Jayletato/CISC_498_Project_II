package com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.Classes;

public class Song {
    protected String song_url;
    protected String song_name;
    protected String album_name;
    protected String artist_name;
    protected String thumbnail_url;

    public Song(String song_url, String song_name, String album_name, String artist_name, String thumbnail_url) {
        this.song_url = song_url;
        this.song_name = song_name;
        this.album_name = album_name;
        this.artist_name = artist_name;
        this.thumbnail_url = thumbnail_url;
    }

    public String getSongURL() {return song_url;}

    public String getSongName() {
        return song_name;
    }
    public String getAlbumName() { return album_name; }

    public String getArtistName() {
        return artist_name;
    }

    public String getThumbnailURL() {
        return thumbnail_url;
    }
}
