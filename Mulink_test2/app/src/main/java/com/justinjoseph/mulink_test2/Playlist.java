package com.justinjoseph.mulink_test2;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Playlist {
    protected ArrayList<Song> songs;
    protected String thumbnailURL;
    public String playlistName;


    public Playlist(ArrayList<Song> songs, String thumbnailURL, String playlistName) {
        this.songs = songs;
        this.thumbnailURL = thumbnailURL;
        this.playlistName = playlistName;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song getSong(int i) {return songs.get(i);}

    public int getSize() {return songs.size();}

    public void addSong(Song song) {songs.add(song);}

    public void delSong(int i) {
        songs.remove(i);
    }

    public void swapSongs(int first, int second) {
        // Swaps two songs given their indexes as parameters. Logs an IndexOutOfBound Exception if index is out of bounds
        try {
            songs.set(first, songs.get(second));
            songs.set(second, songs.get(first));
        } catch (IndexOutOfBoundsException e) {
            Log.d(".Playlist.java", "IndexOutOfBounds Exception");
        }
    }

    public String getThumbnailURL() {return thumbnailURL;}

    public String getPlaylistName() {return playlistName;}
}
