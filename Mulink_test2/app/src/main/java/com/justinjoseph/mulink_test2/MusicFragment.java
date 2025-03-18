package com.justinjoseph.mulink_test2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinjoseph.mulink_test2.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public MusicFragment() {
        // Required empty public constructor
    }

    // Boiler plate? //

    // TODO: Rename and change types and number of parameters
    // //

    //Boiler plate?
    public static MusicFragment newInstance(ActivityMainBinding binding) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Boiler plate?
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentManager parentManager = fragmentManager.getPrimaryNavigationFragment().getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Song testSong1 = new Song("https://youtu.be/YzzcKXXjxYY?si=h3rgm4XgkGfrzScr", "Runaway", "MOVING OUT", "Phoneboy", "https://i.ytimg.com/vi/QTaHmSR5Vzw/default.jpg");
        Song testSong2 = new Song("https://youtu.be/heyMNhMOa3c?si=2qPhwzFY76St--ci", "[spoons rattling]", "GAMI GANG", "Origami Angel", "https://i.ytimg.com/vi/heyMNhMOa3c/default.jpg");
//        Song[] songs = {testSong1, testSong2};
        ArrayList<Song> testArrayList = new ArrayList<Song>();
        testArrayList.add(testSong1);
        testArrayList.add(testSong2);

        Playlist testPlaylist = new Playlist(testArrayList, "https://i.ytimg.com/vi/heyMNhMOa3c/default.jpg", "My favorite songs");
        Playlist testPlaylist2 = new Playlist(testArrayList, "https://i.ytimg.com/vi/heyMNhMOa3c/default.jpg", "My second favorite songs");
        addPlaylist(testPlaylist, transaction);
        addPlaylist(testPlaylist2, transaction);
        transaction.commit();
//        addPlaylist(new Song(), fragmentManager);


    }

    //Takes a Playlist and FragmentManager, creates a new song fragment and adds it to the song fragment container
    public void addPlaylist(Playlist playlist, FragmentTransaction transaction) {
        PlaylistWidgetFragment playlistWidgetFragment = new PlaylistWidgetFragment(this.getContext(), playlist);
//        playlistWidgetFragment.view.setOnClickListener(R.id.playlist_fragment_container);
        transaction.add(R.id.playlist_fragment_container, playlistWidgetFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onClick(View v) {
        Log.d(".MusicFragment.java", String.valueOf(v));
        return;
    }

}