package com.example.leroymerlin.ui.ad;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.leroymerlin.R;
import com.example.leroymerlin.models.Ad;
import com.example.leroymerlin.utils.XMLManager;

import java.util.Collections;
import java.util.List;

public class AdFragment extends Fragment {

    private AdViewModel homeViewModel;
    private ConstraintLayout constraintLayout;
    private List<Ad> adList;

    private boolean clickable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(AdViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ad, container, false);
        constraintLayout = root.findViewById(R.id.constraintLayout);

        this.adList = XMLManager.readFile(getContext(), "ads.xml");
        this.clickable = true;

        createCard();
        return root;
    }

    //Créer une carte d'offre
    private void createCard(){
        //On crééé la carte
        final View card = LayoutInflater.from(getActivity()).inflate(R.layout.ad_card, constraintLayout, false);
        TextView titleAdTV = card.findViewById(R.id.titleAdTV);
        TextView descAdTV = card.findViewById(R.id.descAdTV);
        TextView tasksAdTV = card.findViewById(R.id.tasksAdTV);
        constraintLayout.addView(card);

        //On modifie les propriétés de l'offre
        Collections.shuffle(adList);
        if (adList.size() != 0){
            Log.e("yes", "yes");
            Ad ad = adList.get(0);
            titleAdTV.setText(ad.getTitle());
            descAdTV.setText(ad.getDescription());

            StringBuilder stringBuilder = new StringBuilder();
            for (String task : ad.getTasks()){
                stringBuilder.append("- ").append(task).append("\n");
            }
            tasksAdTV.setText(stringBuilder);
        }

        card.setScaleX(0.7f);
        card.setScaleY(0.7f);
        card.animate().scaleX(1f).scaleY(1f).setDuration(400);
        final ImageView heartBtn = card.findViewById(R.id.heartBtn);
        final ImageView crossBtn = card.findViewById(R.id.crossBtn);

        //Clique sur le coeur
        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickable){
                    clickable = false;
                    card.animate().rotation(25).translationX(250).alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            constraintLayout.removeView(card);
                            clickable = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    createCard();
                    card.bringToFront();
                }
            }
        });

        //Clique sur la croix
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickable){
                    clickable = false;
                    card.animate().rotation(-25).translationX(-250).alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            constraintLayout.removeView(card);
                            clickable = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    createCard();
                    card.bringToFront();
                }
            }
        });
    }
}