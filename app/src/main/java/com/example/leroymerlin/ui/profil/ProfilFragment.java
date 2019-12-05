package com.example.leroymerlin.ui.profil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.leroymerlin.R;

public class ProfilFragment extends Fragment {

    private ProfilViewModel notificationsViewModel;
    private LinearLayout layoutProfil;
    private LinearLayout linearLayoutOuvert;

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

        LinearLayout linearLayoutPrincipal = new LinearLayout(getContext());
        layoutProfil.addView(scrollViewPrincipal);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        scrollViewPrincipal.addView(linearLayoutPrincipal);

        // pour chaque annonce, on crée une "fiche"
        for (int i = 0 ; i < 5 ; i++) {
            // Création du scroll view de la fiche
            ScrollView scrollViewAd = new ScrollView(getContext());
            LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            scrollViewParams.setMargins(0,30,0,0);
            scrollViewAd.setLayoutParams(scrollViewParams);
            linearLayoutPrincipal.addView(scrollViewAd);

            // On crée un linearLayout pour le scrollview
            LinearLayout linearLayoutScrollViewAd = new LinearLayout(getContext());
            linearLayoutScrollViewAd.setPadding(50,5,50,5);
            linearLayoutScrollViewAd.setOrientation(LinearLayout.VERTICAL);
            scrollViewAd.addView(linearLayoutScrollViewAd);

            // On crée le premier linearLayout qui contient les infos principales
            final LinearLayout linearLayoutAdPrincipal = new LinearLayout(getContext());
            linearLayoutAdPrincipal.setPadding(10,0,10,15);
            linearLayoutAdPrincipal.setOrientation(LinearLayout.VERTICAL);
            linearLayoutAdPrincipal.setBackgroundResource(R.color.colorPrimary);
            linearLayoutScrollViewAd.addView(linearLayoutAdPrincipal);

            // on y ajoute les informations principales
            TextView nameAd = new TextView(getContext());
            nameAd.setTextColor(getResources().getColor(R.color.colorTextWhite));
            nameAd.setText("Engineer BI - STAGE");
            nameAd.setTextSize(convertSpToPixel(10));
            Log.i("Engineer BI - STAGE", nameAd.getText().toString());
            linearLayoutAdPrincipal.addView(nameAd);
            TextView missionsAd = new TextView(getContext());
            missionsAd.setTextColor(getResources().getColor(R.color.colorTextWhite));
            missionsAd.setText("Mission :\n" +
                    "Au sein de l’équipe DATA Leroy Merlin, tes missions seront les suivantes\n" +
                    "Tu travailles au contact des équipes métiers ; \n" +
                    "Tu proposes des solutions techniques innovantes ; \n" +
                    "Tu modélises et construis le socle Data ; \n" +
                    "Tu conçois et construis les solutions de restitutions. ");
            linearLayoutAdPrincipal.addView(missionsAd);
            ImageView imageViewMore = new ImageView(getContext());
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(40, 40);
            imageViewParams.weight = 1;
            imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;
            imageViewMore.setLayoutParams(imageViewParams);
            imageViewMore.setImageResource(R.drawable.ic_arrow_down);
            linearLayoutAdPrincipal.addView(imageViewMore);


            // On crée le second linearLayout qui contient les infos complémentaires
            final LinearLayout linearLayoutAdSecondary = new LinearLayout(getActivity());
            linearLayoutAdSecondary.setPadding(10,0,10,0);
            linearLayoutAdSecondary.setBackgroundResource(R.color.colorPrimaryDark);
            linearLayoutAdSecondary.setOrientation(LinearLayout.VERTICAL);
            linearLayoutScrollViewAd.addView(linearLayoutAdSecondary);
            linearLayoutAdSecondary.setVisibility(View.GONE);
            TextView description = new TextView(getContext());
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

            linearLayoutAdPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linearLayoutAdSecondary.getVisibility() == View.GONE) {
                        linearLayoutAdSecondary.setVisibility(View.VISIBLE);
                        if (null != linearLayoutOuvert) {
                            linearLayoutOuvert.setVisibility(View.GONE);
                        }
                        linearLayoutOuvert = linearLayoutAdSecondary;
                    } else {
                        linearLayoutAdSecondary.setVisibility(View.GONE);
                        linearLayoutOuvert = null;
                    }
                }
            });
        }
    }

    public float convertSpToPixel(float sp){
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }
}