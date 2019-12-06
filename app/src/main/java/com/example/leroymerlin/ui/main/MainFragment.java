package com.example.leroymerlin.ui.main;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.leroymerlin.MainActivity;
import com.example.leroymerlin.R;
import com.example.leroymerlin.ui.ad.AdFragment;

public class MainFragment extends Fragment {

    private Button becomeAssociateBtn;
    private VideoView videoView;
    private ImageView logo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        becomeAssociateBtn = root.findViewById(R.id.becomeAssociateBtn);
        videoView = root.findViewById(R.id.videoView);
        logo = root.findViewById(R.id.logo);

        //Préparer la vidéo
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        try{
            int id = this.getRawResIdByName("video");
            videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + id));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        videoView.requestFocus();

        //Lancer la vidéo dès qu'elle est chargée
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.seekTo(10);
                //videoView.start();
            }
        });

        becomeAssociateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).loadFragment();
            }
        });
        return root;
    }

    //Récupérer l'identifiant de la vidéo
    public int getRawResIdByName(String resName) {
        String pkgName = getActivity().getPackageName();
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }
}