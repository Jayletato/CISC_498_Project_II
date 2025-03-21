package com.justinjoseph.mulink_test2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinjoseph.mulink_test2.databinding.FragmentPlaylistBinding;


public class PlaylistFragment extends Fragment {
    FragmentPlaylistBinding binding;
    private Playlist playlist;

    public PlaylistFragment(Playlist playlist) {
        // Required empty public constructor
        this.playlist = playlist;
    }

    public static PlaylistFragment newInstance(Playlist playlist) {
        PlaylistFragment fragment = new PlaylistFragment(playlist);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        for (int i = 0; i < playlist.getSize(); i++){
            addSong(playlist.getSong(i), fragmentManager);
        }
    }

    //Takes a Song and FragmentManager, creates a new song fragment and adds it to the song fragment container
    public void addSong(Song song, FragmentManager fm) {
        SongFragment songFragment = new SongFragment(this.getContext(), song);
        fm.beginTransaction().add(R.id.song_fragment_container, songFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }
}