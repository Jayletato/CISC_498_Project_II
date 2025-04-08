package com.justinjoseph.mulink_test2;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.Classes.Playlist;
import com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.Classes.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class JSONPlaylistAdapter {
    /**
     * Class for turning adapting a json file containing playlists (playlists.json).
     * @Context is the constructor parameter
     * Holds an ArrayList of the playilsts which is constructed when method setPlaylistArray is run
     */
    //TODO: add playlists functionality
    Context context;
    String jsonFileName;
    ArrayList<Playlist> playlistArrayList;

    public JSONPlaylistAdapter(Context context){
        this.context = context;
    }

    public ArrayList<Playlist> getPlaylistArray(){
        if (playlistArrayList==null){
            return null;
        }
        return playlistArrayList;
    }

    public void setPlaylistArray(){
        //TODO: Make this class able to read and write to a json file
        JSONObject jsonObject = this.getPlaylists();
        try{
            playlistArrayList = new ArrayList<Playlist>();
            JSONArray playlistsJsonArray = jsonObject.getJSONArray("playlists");
            for (int i = 0; i < playlistsJsonArray.length(); i++){ // for each playlist
                JSONObject playlistJsonObject = playlistsJsonArray.getJSONObject(i);

                //Create the ArrayList of songs to go into the Playlist
                ArrayList<Song> songs = new ArrayList<Song>();
                for (int j = 0; j < playlistJsonObject.getJSONArray("songs").length(); j++) { // for each song in the playlist
                    JSONObject songJsonObject = playlistJsonObject.getJSONArray("songs").getJSONObject(j);
                    // Song parameters
                    String url = songJsonObject.getString("song_url");
                    String name = songJsonObject.getString("song_name");
                    String album = songJsonObject.getString("album_name");
                    String artist = songJsonObject.getString("artist_name");
                    String thumbnail = songJsonObject.getString("song_thumbnail");
                    songs.add(new Song(url,name,album,artist,thumbnail));
                }

                //Create the Playlist and insert into the ArrayList of Playlists
                Song firstSong = songs.get(0); // The playlist thumbnail will automatically be set to the first songs
                Playlist playlist = new Playlist(songs, firstSong.getThumbnailURL(), playlistJsonObject.getString("playlist_name"));
                playlistArrayList.add(playlist);
            }
            Log.d("./JSONPlaylistAdapter", "playlists json object: " + jsonObject.get("playlists").toString());
        } catch (JSONException jsone) {
            Log.d("./JSONPlaylistAdapter", "JSONException");
            jsone.printStackTrace();
        }

        return;
    }

    private JSONObject getPlaylists(){
        // Reads the playlist json file and gets playlists. If file does not exist, creates it.
        jsonFileName = "playlists.json";
        InputStream playlistInputStream;
        // If file already exists
        try {
            playlistInputStream = context.getAssets().open(jsonFileName);
            int inputStreamSize = playlistInputStream.available();
            byte[] buffer = new byte[inputStreamSize];

            playlistInputStream.read(buffer);
            String bufferString = new String(buffer, StandardCharsets.UTF_8);
            JSONObject inputJSONObject = new JSONObject(bufferString);
            Log.d("./JSONPlaylistAdapter/getPlaylists", "JSONObject: " + inputJSONObject.toString());
            playlistInputStream.close();

            return inputJSONObject;
        // If file doesn't exist
        } catch (IOException ioe) {

        // Unforeseen exception
        } catch (Exception e){
            Log.d(".JSONPlaylistAdapter.java", "Unforeseen Error");
        }
        return null;
    }

    public void createFile(){
        jsonFileName = "playlists.json";
        try {
            FileOutputStream playlistOutputStream = context.openFileOutput(jsonFileName, Context.MODE_PRIVATE);

            String emptyJSONArray = "[]";
            byte[] emptyJSONArrayBytes = emptyJSONArray.getBytes();
            playlistOutputStream.write(emptyJSONArrayBytes);
            Log.d("./JSONPlaylistAdapter/createFile", "playlists.json file created!");

            playlistOutputStream.close();
        } catch (FileNotFoundException fnfe) {
            Log.d("./JSONPlaylistAdapter/createFile", "FileNotFoundException");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            Log.d("./JSONPlaylistAdapter/createFile", "IOException");
            ioe.printStackTrace();
        }
    }
}
