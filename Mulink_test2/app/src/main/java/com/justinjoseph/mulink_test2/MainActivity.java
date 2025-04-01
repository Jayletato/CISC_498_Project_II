package com.justinjoseph.mulink_test2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.justinjoseph.mulink_test2.MainFragments.HomeFragment;
import com.justinjoseph.mulink_test2.MainFragments.MusicFragment;
import com.justinjoseph.mulink_test2.MainFragments.SettingsFragment;
import com.justinjoseph.mulink_test2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        replaceFragment(new HomeFragment());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Sets binding to fragment from the navigation menu
        binding.bottomNavigationView.setOnItemSelectedListener(item -> { // I couldn't get this in a switch statement
            if (item.getItemId() == R.id.home_tab) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.music_tab) {
                replaceFragment(new MusicFragment());
            } else if (item.getItemId() == R.id.settings_tab) {
                replaceFragment(new SettingsFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}