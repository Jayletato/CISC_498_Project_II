package com.justinjoseph.mulink_test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.justinjoseph.mulink_test2.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class PlaylistWidgetFragment extends Fragment implements View.OnClickListener {
    private final Context context;

    protected Playlist playlist;

    public PlaylistWidgetFragment(Context context, Playlist playlist) {
        // Required empty public constructor
        this.context = context;
        this.playlist = playlist;
    }

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

        queue.stop();
        return imageLoader;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_widget, container, false);

        // Assign all song components to UI
        ImageLoader imageLoader = getImageLoader();
        view.setOnClickListener(this);
        NetworkImageView songThumbnail = view.findViewById(R.id.playlist_thumbnail);
        songThumbnail.setImageUrl(playlist.getThumbnailURL(), imageLoader);


        TextView textView = view.findViewById(R.id.playlist_name);
        textView.setText(playlist.getPlaylistName());

        textView = view.findViewById(R.id.video_count);
        textView.setText("Videos: " + playlist.getSize());

//        button_submit = (Button)view.findViewById(R.id.playlist_fragment_container);

//        songThumbnail.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Log.d(".PlaylistWidgetFragment",this.playlist.getPlaylistName());
        replaceFragment(new PlaylistFragment(playlist));
        // TODO: get this onclick to return
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}