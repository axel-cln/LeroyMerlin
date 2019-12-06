package com.example.leroymerlin.ui.profil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.leroymerlin.R;

public class ProfilFragment extends Fragment {

    private ProfilViewModel notificationsViewModel;
    private LinearLayout layoutProfil;
    private LinearLayout linearLayoutOpen;
    private ImageView imageViewRotated; // sauvegarde l'imageView qui a été tournée
    private ScrollView scrollViewOpen;
    private boolean seeInformations = true; // booléen qui sert à éviter d'activer deux défilements à la fois

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        layoutProfil = root.findViewById(R.id.layoutProfil);
        layoutProfil.setGravity(Gravity.CENTER_HORIZONTAL);
        //Log.i("Test" , layoutProfil.toString());

        creationLayout();

        return root;
    }

    @SuppressLint("ResourceAsColor")
    public void creationLayout() {
        Log.i("Test" , layoutProfil.toString());

        // Création d'un scrollView principal qui va contenir toutes les annonces
        ScrollView scrollViewPrincipal = new ScrollView(getContext());

        final LinearLayout linearLayoutPrincipal = new LinearLayout(getContext());
        layoutProfil.addView(scrollViewPrincipal);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        scrollViewPrincipal.addView(linearLayoutPrincipal);

        // pour chaque annonce, on crée une "fiche"
        for (int i = 0 ; i < 5 ; i++) {
            // Création du scroll view de la fiche
            final ScrollView scrollViewAd = new ScrollView(getContext());
            LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            scrollViewParams.setMargins(0,30,0,0);
            scrollViewAd.setLayoutParams(scrollViewParams);
            linearLayoutPrincipal.addView(scrollViewAd);

            // On crée un linearLayout pour le scrollview
            final LinearLayout linearLayoutScrollViewAd = new LinearLayout(getContext());
            linearLayoutScrollViewAd.setPadding(50,5,50,5);
            linearLayoutScrollViewAd.setOrientation(LinearLayout.VERTICAL);
            scrollViewAd.addView(linearLayoutScrollViewAd);

            // On crée le premier linearLayout qui contient les infos principales
            final LinearLayout linearLayoutAdPrincipal = new LinearLayout(getContext());
            linearLayoutAdPrincipal.setPadding(40,0,40,30);
            linearLayoutAdPrincipal.setOrientation(LinearLayout.VERTICAL);
            linearLayoutAdPrincipal.setBackgroundResource(R.drawable.style_ad_saved_part1);
            linearLayoutScrollViewAd.addView(linearLayoutAdPrincipal);

            // création d'un relativeLayout qui contient le nom de l'offre et une image pour supprimer l'offre de son panier
            RelativeLayout relativeLayoutNameAd = new RelativeLayout(getContext());
            linearLayoutAdPrincipal.addView(relativeLayoutNameAd);


            // on y ajoute les informations principales
            TextView nameAd = new TextView(getContext());
            //LinearLayout.LayoutParams nameAdParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //nameAdParams.setMargins();
            nameAd.setTextColor(getResources().getColor(R.color.colorTextWhite));
            nameAd.setText("Engineer BI - STAGE");
            nameAd.setTextSize(convertSpToPixel(10));
            Log.i("Engineer BI - STAGE", nameAd.getText().toString());
            relativeLayoutNameAd.addView(nameAd);

            ImageView removeAdd = new ImageView(getContext());
            removeAdd.setImageResource(R.drawable.ic_cross_black);
            RelativeLayout.LayoutParams layoutImageRemove = new RelativeLayout.LayoutParams((int)convertDpToPixel(20), (int)convertDpToPixel(20));
            removeAdd.setLayoutParams(layoutImageRemove);
            layoutImageRemove.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //layoutImageRemove.setMargins(0,);
            relativeLayoutNameAd.addView(removeAdd);

            TextView textViewMission = new TextView(getContext());
            textViewMission.setTextColor(getResources().getColor(R.color.colorTextWhite));
            textViewMission.setText("Mission");
            textViewMission.setTextSize(convertSpToPixel(7));
            textViewMission.setTypeface(textViewMission.getTypeface(), Typeface.BOLD);
            linearLayoutAdPrincipal.addView(textViewMission);

            TextView missionsAd = new TextView(getContext());
            missionsAd.setTextColor(getResources().getColor(R.color.colorTextWhite));
            missionsAd.setText("Au sein de l’équipe DATA Leroy Merlin, tes missions seront les suivantes\n" +
                    "Tu travailles au contact des équipes métiers ; \n" +
                    "Tu proposes des solutions techniques innovantes ; \n" +
                    "Tu modélises et construis le socle Data ; \n" +
                    "Tu conçois et construis les solutions de restitutions. ");
            linearLayoutAdPrincipal.addView(missionsAd);
            final ImageView imageViewMore = new ImageView(getContext());
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(40, 40);
            imageViewParams.weight = 1;
            imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;
            imageViewParams.setMargins(0,20,0,0);
            imageViewMore.setLayoutParams(imageViewParams);
            imageViewMore.setImageResource(R.drawable.ic_arrow_down);
            linearLayoutAdPrincipal.addView(imageViewMore);


            // On crée le second linearLayout qui contient les infos complémentaires
            final LinearLayout linearLayoutAdSecondary = new LinearLayout(getActivity());
            linearLayoutAdSecondary.setPadding(25,0,25,0);
            linearLayoutAdSecondary.setBackgroundResource(R.drawable.style_ad_saved_part2);
            linearLayoutAdSecondary.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linearLayoutPart2Params = new LinearLayout.LayoutParams(linearLayoutAdPrincipal.getWidth() - 30, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayoutAdSecondary.setLayoutParams(linearLayoutPart2Params);
            linearLayoutScrollViewAd.addView(linearLayoutAdSecondary);
            final TextView description = new TextView(getContext());
            description.setTextColor(getResources().getColor(R.color.colorTextWhite));
            description.setText("Voici les technos que vous devrez utiliser :\n" +
                    "Java\n" +
                    "C\n" +
                    "PHP\n" +
                    "Profil :\n" +
                    "Issu d'une formation sup\n" +
                    "appétence forte aux méthodes de prévisions de ventes\n" +
                    "Tu as des connaissances sur Google Cloud Platform et python ou tu as envie d'apprendre\n" +
                    "Tu es agile et force de propositions.\n\n" +
                    "Interessé ? Contactez marine.coisne@gmail.com");
            linearLayoutAdSecondary.addView(description);
            linearLayoutAdSecondary.setVisibility(View.GONE);

            linearLayoutAdPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // on vérifie qu'il n'y a pas d'action en cours (par exemple un linearLayout qui est déjà en train de s'agrandir ou de rétrécir)
                    if (seeInformations) {
                        seeInformations = false;
                        // si les informations complémentaires de l'offre cliquée ne sont pas visibles actuellement
                        if (linearLayoutAdSecondary.getVisibility() == View.GONE) {
                            openLinearInformation(scrollViewAd, linearLayoutAdPrincipal, linearLayoutAdSecondary, imageViewMore);
                            if (null != linearLayoutOpen) {
                                closeLinearInformations(scrollViewOpen, linearLayoutOpen, imageViewRotated);
                                //imageViewRotated.setRotation(0);
                                //linearLayoutOpen.setVisibility(View.GONE);
                            }
                            scrollViewOpen = scrollViewAd;
                            imageViewRotated = imageViewMore;
                            linearLayoutOpen = linearLayoutAdSecondary;
                        } else {
                            closeLinearInformations(scrollViewAd, linearLayoutAdSecondary, imageViewMore);

                            imageViewRotated = null;
                            linearLayoutOpen = null;
                        }
                    }
                }
            });

            linearLayoutAdSecondary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (seeInformations) {
                        seeInformations = false;
                        closeLinearInformations(scrollViewAd, linearLayoutAdSecondary, imageViewMore);
                        imageViewRotated = null;
                        linearLayoutOpen = null;
                    }
                }
            });
        }
    }

    // on affiche les informations supplémentaires à l'utilisateur
    private void openLinearInformation(final ScrollView scrollView, LinearLayout linearLayoutP, final LinearLayout linearLayoutS, ImageView imageView) {
        final int linearHeight = scrollView.getHeight();
        LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, linearHeight);
        scrollViewParams.setMargins(0, 30, 0, 0);
        scrollView.setLayoutParams(scrollViewParams);
        LinearLayout.LayoutParams linearLayoutPart2Params = new LinearLayout.LayoutParams(linearLayoutP.getWidth() - 30, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayoutPart2Params.weight = 1;
        linearLayoutPart2Params.gravity = Gravity.CENTER_HORIZONTAL;
        linearLayoutS.setLayoutParams(linearLayoutPart2Params);
        linearLayoutS.setVisibility(View.VISIBLE);
        linearLayoutS.post(new Runnable() {
            @Override
            public void run() {
                int maxSizeInformations = linearLayoutS.getHeight();
                Log.i("Taille MAXIMALE", "" + maxSizeInformations);
                //LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                //linearLayoutAdSecondary.setLayoutParams(scrollViewParams);
                for (int i = 0; i < maxSizeInformations; i++) {
                    Handler handler = new Handler();
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Log.i("Taille", finalI +"");
                            LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, linearHeight + finalI);
                            scrollViewParams.setMargins(0, 30, 0, 0);
                            scrollView.setLayoutParams(scrollViewParams);
                        }
                    }, (i) / 2);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        seeInformations = true;
                    }
                }, (maxSizeInformations+1) / 2);
            }
        });
        imageView.setRotation(180);
    }

    // on masque les informations supplémentaires à l'utilisateur
    private void closeLinearInformations(final ScrollView scrollView, final LinearLayout linearLayout, ImageView imageView) {
        final int linearHeight = scrollView.getHeight();
        Log.i("Test taille linear", linearHeight +"");

        final int maxSizeInformations = linearLayout.getHeight();
        Log.i("Taille MAXIMALE", "" + maxSizeInformations);
        //LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        //linearLayoutAdSecondary.setLayoutParams(scrollViewParams);
        for (int i = maxSizeInformations; i > 0 ; i--) {
            Handler handler = new Handler();
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, linearHeight - maxSizeInformations + finalI);
                    scrollViewParams.setMargins(0,30,0,0);
                    scrollView.setLayoutParams(scrollViewParams);
                }
            }, (maxSizeInformations - i)/2);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.GONE);
                seeInformations = true;
            }
        }, (maxSizeInformations + 2)/2);
        imageView.setRotation(0);
    }

    public float convertSpToPixel(float sp){
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }

    public float convertDpToPixel(float dp){
        return dp * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}