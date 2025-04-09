package com.justinjoseph.mulink_test2.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.justinjoseph.mulink_test2.JSONPlaylistAdapter;
import com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.Classes.Playlist;
import com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.PlaylistWidgetFragment;
import com.justinjoseph.mulink_test2.R;
import com.justinjoseph.mulink_test2.MainFragments.MusicSubFragments.Classes.Song;
import com.justinjoseph.mulink_test2.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private View myFragmentView;
    private PopupWindow popupWindow;
    private Button addPlaylistButton;
    private JSONPlaylistAdapter jsonPlaylistAdapter;

    public MusicFragment() {
        // Required empty public constructor
    }

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
    }

    //Takes a Playlist and FragmentManager, creates a new song fragment and adds it to the song fragment container
    public void addPlaylist(Playlist playlist, FragmentTransaction transaction) {
        // Adds a playlist's widget to the list of playlists
        PlaylistWidgetFragment playlistWidgetFragment = new PlaylistWidgetFragment(this.getContext(), playlist);
//        playlistWidgetFragment.view.setOnClickListener(R.id.playlist_fragment_container);
        transaction.add(R.id.playlist_fragment_container, playlistWidgetFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_music, container, false);

        // Boiler plate?
        fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentManager parentManager = fragmentManager.getPrimaryNavigationFragment().getParentFragmentManager();
        transaction = fragmentManager.beginTransaction();

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
//        addPlaylist(new Song(), fragmentManager);


        addPlaylistButton = myFragmentView.findViewById(R.id.add_playlist_button);
        addPlaylistButton.setOnClickListener(v -> {
            Log.d(".MusicFragment", "Button pressed :D");

            drawPopUpWindow(v, inflater);

        });

        jsonPlaylistAdapter = new JSONPlaylistAdapter(getContext());
//        JSONObject playlistsObject = jsonPlaylistAdapter.getPlaylists();
//        Log.d("./MusicFragment", "playlists json object: " + playlistsObject.toString());
        jsonPlaylistAdapter.setPlaylistArray();
        for (int i = 0; i < jsonPlaylistAdapter.getPlaylistArray().size(); i++) {
            Playlist playlist = jsonPlaylistAdapter.getPlaylistArray().get(i);
            addPlaylist(playlist, transaction);
        }
        transaction.commit();
        transaction.setReorderingAllowed(true);

        return myFragmentView;
    }

    @Override
    public void onClick(View v) {
        Log.d(".MusicFragment.java", String.valueOf(v));
        return;
    }

    public void drawPopUpWindow(View v, LayoutInflater inflater) {
        //Draw a pop up window
        View popupView = inflater.inflate(R.layout.ap_popup_layout, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(myFragmentView, Gravity.CENTER,0,0);//

        // Functionality for add and cancel buttons
        Button addPlaylistConfirm = popupView.findViewById(R.id.add_confirm_button);
        Button addPlaylistCancel = popupView.findViewById(R.id.add_cancel_button);

        addPlaylistConfirm.setOnClickListener(vCo -> {
            try {
                FragmentTransaction newTransaction = fragmentManager.beginTransaction();
                EditText enteredPlaylistName = popupView.findViewById(R.id.playlist_enter_name);
                Playlist newPlaylist = new Playlist(new ArrayList<Song>(), "", enteredPlaylistName.getText().toString());
                addPlaylist(newPlaylist, newTransaction);
                jsonPlaylistAdapter.addPlaylistToFile(newPlaylist);
                newTransaction.commit();
                Toast.makeText(v.getContext(), "Added playlist", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(".MusicFragment.java/drawPopUpWindow", "Error creating playlist");
            }
            popupWindow.dismiss();
        });

        addPlaylistCancel.setOnClickListener(vCa -> {
            popupWindow.dismiss();
        });
    }

}