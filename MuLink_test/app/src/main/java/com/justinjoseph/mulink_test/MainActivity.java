package com.justinjoseph.mulink_test;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WebView video_webview = findViewById(R.id.video_webview);
        WebSettings webSettings = video_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        video_webview.setWebChromeClient(new WebChromeClient());

        // This can load webpages for YouTube
        String url = "https://music.youtube.com/embed/sOJhZ7nhN7g";
        video_webview.loadUrl(url);

        // This can load embed content such as Spotify songs, but not YouTube songs
//        String iframe_url = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/watch?v=sOJhZ7nhN7g\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
//        video_webview.loadData(iframe_url, "text/html","utf-8");

//        setContentView(video_webview);

        /////////////////////////////////////////////////////////////
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//        );
//        try {
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.prepare(); // might take long! (for buffering, etc)
//            mediaPlayer.start();
//        } catch (IOException IOe) {
//            Log.d(".MainActivity.java", "IOException");
//            IOe.printStackTrace();
//        } catch (IllegalArgumentException IAe) {
//            Log.d(".MainActivity.java", "IllegalArgumentException");
//            IAe.printStackTrace();
//        }
    }
}
