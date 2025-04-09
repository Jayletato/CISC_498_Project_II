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
    //TODO: fix functionality for adding playlists to file
    //TODO: fix functionality for creating a file when no file exists
    Context context;
    String jsonFileName = "playlists.json";
    ArrayList<Playlist> playlistArrayList;

    public JSONPlaylistAdapter(Context context){
        this.context = context;
        setPlaylistArray();
    }

    public void addPlaylistToFile(Playlist playlist){
        playlistArrayList.add(playlist);
        JSONObject playlistsJsonObject = getPlaylists();
        JSONArray playlistsJsonArray;

        //Construct playlist as a JSONObject
        try {
            JSONObject newPlaylistJsonObject;

            String playlistName = playlist.getPlaylistName();
            JSONArray songs = new JSONArray();
            String playlistThumbnail = playlist.getThumbnailURL();
            if (playlistThumbnail.isEmpty()){
                //playlistThumbnail
                playlistThumbnail= "\"\""; // bandage fix
            }

            //Add songs to the songs JSONArray so they can be put into the playlist JSON object
            for (int i = 0; i < playlist.getSongs().size(); i++) {
                String songName = playlist.getSong(i).getSongName();
                String albumName = playlist.getSong(i).getAlbumName();
                String artistName = playlist.getSong(i).getArtistName();
                String songUrl = playlist.getSong(i).getSongURL();
                String songThumbnail = playlist.getSong(i).getThumbnailURL();
                JSONObject song = new JSONObject(String.format("{\"song_name\": %s, \"album_name\": %s, \"artist_name\": %s, \"song_url\": %s, \"song_thumbnail\": %s}", songName, albumName, artistName, songUrl, songThumbnail));
                songs.put(song);
                Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "Song: " + song.toString());
            }
            newPlaylistJsonObject = new JSONObject(String.format("{\"playlist_name\": \"%s\", \"songs\": %s, \"playlist_thumbnail\": %s}", playlistName,songs.toString(), playlistThumbnail));
            Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "playlistJsonObject: " + newPlaylistJsonObject.toString());

            //Take the existing playlists.json data and append the new playlist to it
            playlistsJsonArray = playlistsJsonObject.getJSONArray("playlists");
            playlistsJsonArray.put(newPlaylistJsonObject);
            Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "playlistJsonArray: " + playlistsJsonArray.toString());

            //Update the playlists.json file with the updated data
            FileOutputStream playlistOutputStream = context.openFileOutput(jsonFileName, Context.MODE_PRIVATE);

            String updatedData = String.format("{\"playlists\": %s}", playlistsJsonArray.toString());
            byte[] JSONObjectBytes = updatedData.getBytes();
            playlistOutputStream.write(JSONObjectBytes);

            playlistOutputStream.close();
            Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "playlists.json file recreated!");

        } catch (JSONException jsone) {
            jsone.printStackTrace();
            Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "JSONException");
            return;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            Log.d("./JSONPlaylistAdapter/addPlaylistToFile", "FileNotFoundException");
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public ArrayList<Playlist> getPlaylistArray(){
        // Get the ArrayList<Playlist> object stored within the adapter class
        if (playlistArrayList==null){
            return null;
        }
        return playlistArrayList;
    }

    public void setPlaylistArray(){
        // Takes the playlists in the playlists.json file and creates the ArrayList<Playlist> accordingly
        //TODO: Make this class able to read and write to a json file
        JSONObject jsonObject = getPlaylists();
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
        } catch (NullPointerException npe) {
            createFile();
        }

        return;
    }

    private JSONObject getPlaylists(){
        // Reads the playlist json file and gets playlists. If file does not exist, creates it.
//        jsonFileName = "playlists.json";
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
            ioe.printStackTrace();
            createFile();
        // Unforeseen exception
        } catch (Exception e){
            Log.d(".JSONPlaylistAdapter.java", "Unforeseen Error");
        }
        return null;
    }

    public void createFile(){
        try {
            FileOutputStream playlistOutputStream = context.openFileOutput(jsonFileName, Context.MODE_PRIVATE);

            String emptyJSONArray = "{\"playlists\": []}";
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
