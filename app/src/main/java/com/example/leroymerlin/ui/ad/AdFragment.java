package com.example.leroymerlin.ui.ad;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.leroymerlin.R;
import com.example.leroymerlin.utils.XMLManager;

public class AdFragment extends Fragment {

    private AdViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(AdViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ad, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Log.e("Leroy", "read xml");

        XMLManager.readFile(getContext(), "ads.xml");

        return root;
    }
}