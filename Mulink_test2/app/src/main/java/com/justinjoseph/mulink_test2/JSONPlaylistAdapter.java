package com.justinjoseph.mulink_test2;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class JSONPlaylistAdapter {

    public JSONPlaylistAdapter(){}
    public JSONObject jsonFileReader(){
        //TODO: Make this class able to read and write to a json file
        JSONObject playlistFile = new JSONObject();

        return playlistFile;
    }

    public void readFile(Context context){
        String jsonFileName;
        jsonFileName = "playlists.json";
        // If file already exists
        try {
            FileInputStream fileInputStream = context.openFileInput(jsonFileName);

        // If file doesn't exist
        } catch (FileNotFoundException fe) {
            File file = new File("\\Mulink_test2\\app\\src\\main\\res");
        // Unforeseen exception
        } catch (Exception e){
            Log.d(".JSONPlaylistAdapter.java", "Unforeseen Error");
        }
    }
}
