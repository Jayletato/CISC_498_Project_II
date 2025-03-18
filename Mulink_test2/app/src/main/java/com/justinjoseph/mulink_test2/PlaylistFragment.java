package com.justinjoseph.mulink_test2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinjoseph.mulink_test2.databinding.FragmentPlaylistBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {
    FragmentPlaylistBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Playlist playlist;

    public PlaylistFragment(Playlist playlist) {
        // Required empty public constructor
        this.playlist = playlist;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    public static PlaylistFragment newInstance(Playlist playlist) {
        PlaylistFragment fragment = new PlaylistFragment(playlist);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Song testSong = new Song("https://youtu.be/YzzcKXXjxYY?si=h3rgm4XgkGfrzScr", "Runaway", "MOVING OUT", "Phoneboy", "https://i.ytimg.com/vi/QTaHmSR5Vzw/default.jpg");
        addSong(testSong, fragmentManager);
        addSong(new Song("https://youtu.be/YzzcKXXjxYY?si=h3rgm4XgkGfrzScr", "Runaway", "MOVING OUT", "Phoneboy", "https://i.ytimg.com/vi/QTaHmSR5Vzw/default.jpg"), fragmentManager);


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