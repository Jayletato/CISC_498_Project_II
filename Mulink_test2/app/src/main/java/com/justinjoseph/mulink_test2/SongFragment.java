package com.justinjoseph.mulink_test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

//**
// * A simple {@link Fragment} subclass.
// * Use the {@link SongFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SongFragment extends Fragment {
    private final Context context;

    protected Song song;

    public SongFragment(Context context, Song song) {
        // Required empty public constructor
        this.context = context;
        this.song = song;
    }
//
//    public static SongFragment newInstance(String param1, String param2) {
//        SongFragment fragment = new SongFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageLoader getImageLoader() {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(20);

            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

        return imageLoader;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        // Assign all song components to UI
        ImageLoader imageLoader = getImageLoader();
        NetworkImageView songThumbnail = view.findViewById(R.id.song_thumbnail);
        songThumbnail.setImageUrl(song.getThumbnailURL(), imageLoader);


        TextView textView = view.findViewById(R.id.song_name);
        textView.setText(song.getSongName());

        textView = view.findViewById(R.id.song_album);
        textView.setText(song.getAlbumName());

        textView = view.findViewById(R.id.artist_name);
        textView.setText(song.getArtistName());



        return view;
    }
}