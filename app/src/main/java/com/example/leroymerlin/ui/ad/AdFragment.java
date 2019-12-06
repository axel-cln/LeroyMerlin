package com.example.leroymerlin.ui.ad;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.leroymerlin.R;
import com.example.leroymerlin.models.Ad;
import com.example.leroymerlin.utils.PreferencesManager;
import com.example.leroymerlin.utils.XMLManager;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AdFragment extends Fragment {

    private ConstraintLayout constraintLayout;
    private ScrollView scrolAdView;
    private List<Ad> adList;
    private View card, lastCard;
    private TextView errorTV;
    private boolean clickable;
    private Ad currentAd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ad, container, false);
        //Mouvement horizontal
        constraintLayout = root.findViewById(R.id.constraintLayout);
        scrolAdView = root.findViewById(R.id.scrolAdView);
        errorTV = root.findViewById(R.id.errorTV);
        errorTV.setVisibility(View.GONE);
        card = null;

        PreferencesManager.setContext(getContext());

        this.adList = XMLManager.readFile(getContext(), "ads.xml");
        //On garde que les offres non vues
        Iterator<Ad> it = adList.iterator();

        while (it.hasNext()){
            Ad ad = it.next();
            if (PreferencesManager.getAllPrefAds().contains(ad.getId()) || PreferencesManager.getAllUninterestingAds().contains(ad.getId())){
                it.remove();
            }
        }

        this.clickable = true;

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        //Vers la gauche
                        if(e1.getX() - e2.getX() > 120 && Math.abs(velocityX) > 200) {
                            removeAd();
                        }
                        //Vers la droite
                        else if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
                            likeAd();
                        }
                        return false;
                    }
                });

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        createCard();
        return root;
    }

    //Créer une carte d'offre
    private void createCard(){
        //On créé la carte
        this.card = LayoutInflater.from(getActivity()).inflate(R.layout.ad_card, constraintLayout, false);
        TextView titleAdTV = this.card.findViewById(R.id.titleAdTV);
        TextView descAdTV = this.card.findViewById(R.id.descAdTV);
        TextView tasksAdTV = this.card.findViewById(R.id.tasksAdTV);

        Log.e("creer card", adList.size()+"");
        if (!adList.isEmpty()){
            constraintLayout.addView(this.card);
            errorTV.setVisibility(View.GONE);
        }
        else{
            errorTV.setVisibility(View.VISIBLE);
        }

        //On modifie les propriétés de l'offre
        Collections.shuffle(adList);
        if (adList.size() != 0){
            Ad ad = adList.get(0);
            titleAdTV.setText(ad.getTitle());
            descAdTV.setText(ad.getDescription());

            StringBuilder stringBuilder = new StringBuilder();
            for (String task : ad.getTasks()){
                stringBuilder.append("- ").append(task).append("\n");
            }
            tasksAdTV.setText(stringBuilder);

            this.currentAd = ad;
            this.adList.remove(ad);
        }

        //Préparation de la transition
        this.card.setScaleX(0.7f);
        this.card.setScaleY(0.7f);
        this.card.animate().scaleX(1f).scaleY(1f).setDuration(400);
        final ImageView heartBtn = this.card.findViewById(R.id.heartBtn);
        final ImageView crossBtn = this.card.findViewById(R.id.crossBtn);

        //Clique sur le coeur
        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAd();
            }
        });

        //Clique sur la croix
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAd();
            }
        });
    }

    //Anime la carte vers la gauche
    private void removeAd(){
        if (clickable){
            clickable = false;
            if (currentAd != null){
                PreferencesManager.addUninterestedAd(currentAd.getId());
                adList.remove(currentAd);
            }

            this.card.animate().rotation(-25).translationX(-250).alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    constraintLayout.removeView(AdFragment.this.lastCard);
                    clickable = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            this.lastCard = this.card;
            createCard();
            lastCard.bringToFront();
        }
    }

    //Anime la carte vers la droite
    private void likeAd(){
        if (clickable){
            clickable = false;
            if (currentAd != null) {
                PreferencesManager.addPrefAd(currentAd.getId());
                adList.remove(currentAd);
            }

            this.card.animate().rotation(25).translationX(250).alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    constraintLayout.removeView(AdFragment.this.lastCard);
                    clickable = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            this.lastCard = this.card;
            createCard();
            lastCard.bringToFront();
        }
    }
}