package com.example.leroymerlin.ui.profil;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
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
import com.example.leroymerlin.models.Ad;
import com.example.leroymerlin.utils.PreferencesManager;
import com.example.leroymerlin.utils.XMLManager;

import java.util.Iterator;
import java.util.List;

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

        layoutProfil = root.findViewById(R.id.layoutProfil);
        layoutProfil.setGravity(Gravity.CENTER_HORIZONTAL);

        creationLayout();

        return root;
    }

    @SuppressLint("ResourceAsColor")
    public void creationLayout() {
        // Création d'un scrollView principal qui va contenir toutes les annonces
        ScrollView scrollViewPrincipal = new ScrollView(getContext());

        final LinearLayout linearLayoutPrincipal = new LinearLayout(getContext());
        layoutProfil.addView(scrollViewPrincipal);
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        scrollViewPrincipal.addView(linearLayoutPrincipal);

        final List<Ad> listAds = XMLManager.readFile(getContext(), "ads.xml");

        //On garde que les offres aimées
        Iterator<Ad> it = listAds.iterator();

        while (it.hasNext()){
            Ad ad = it.next();
            //On supprime les offres non lues ou évitées
            if (!PreferencesManager.getAllPrefAds().contains(ad.getId())){
                it.remove();
            }
        }

        // pour chaque annonce, on crée une "fiche"
        for (int i = 0 ; i < listAds.size() ; i++) {
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
            linearLayoutAdPrincipal.setPadding(40,20,40,30);
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
            nameAd.setText(listAds.get(i).getTitle());
            nameAd.setTypeface(nameAd.getTypeface(), Typeface.BOLD);
            nameAd.setTextSize(convertSpToPixel(10));
            relativeLayoutNameAd.addView(nameAd);

            ImageView removeAdd = new ImageView(getContext());
            removeAdd.setImageResource(R.drawable.ic_cross_black);
            RelativeLayout.LayoutParams layoutImageRemove = new RelativeLayout.LayoutParams((int)convertDpToPixel(20), (int)convertDpToPixel(20));
            layoutImageRemove.setMargins(0,20,0,0);
            removeAdd.setLayoutParams(layoutImageRemove);
            layoutImageRemove.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeLayoutNameAd.addView(removeAdd);

            final int finalI = i;
            removeAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAnnonceSauvegardee(linearLayoutPrincipal, scrollViewAd);
                    PreferencesManager.removePrefAd(listAds.get(finalI).getId());
                }
            });

            TextView textViewDescription = new TextView(getContext());
            textViewDescription.setTextColor(getResources().getColor(R.color.colorTextWhite));
            textViewDescription.setText("Description");
            textViewDescription.setTextSize(convertSpToPixel(7));
            textViewDescription.setTypeface(textViewDescription.getTypeface(), Typeface.BOLD);
            linearLayoutAdPrincipal.addView(textViewDescription);

            TextView missionsAd = new TextView(getContext());
            missionsAd.setTextColor(getResources().getColor(R.color.colorTextWhite));
            missionsAd.setText(listAds.get(i).getDescription());
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
            linearLayoutAdSecondary.setPadding(30,0,30,20);
            linearLayoutAdSecondary.setBackgroundResource(R.drawable.style_ad_saved_part2);
            linearLayoutAdSecondary.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linearLayoutPart2Params = new LinearLayout.LayoutParams(linearLayoutAdPrincipal.getWidth() - 30, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayoutAdSecondary.setLayoutParams(linearLayoutPart2Params);
            linearLayoutScrollViewAd.addView(linearLayoutAdSecondary);

            TextView textViewTasks = new TextView(getContext());
            textViewTasks.setTextColor(getResources().getColor(R.color.colorTextWhite));
            textViewTasks.setText("Missions à effectuer ");
            textViewTasks.setPadding(0,10,0,0);
            textViewTasks.setTextSize(convertSpToPixel(6));
            textViewTasks.setTypeface(textViewTasks.getTypeface(), Typeface.BOLD);
            linearLayoutAdSecondary.addView(textViewTasks);

            // on récupère toutes les tâches que l'on affiche dans le deuxième linearLayout
            final TextView description = new TextView(getContext());
            description.setTextColor(getResources().getColor(R.color.colorTextWhite));
            List<String> tasks = listAds.get(i).getTasks();
            for (int j = 0 ; j < tasks.size() ; j++) {
                description.setText(description.getText() + "\n" + tasks.get(j));
            }
            linearLayoutAdSecondary.addView(description);
            linearLayoutAdSecondary.setVisibility(View.GONE);

            TextView textViewContact = new TextView(getContext());
            textViewContact.setText("Contact :\n" + listAds.get(i).getContact());
            linearLayoutAdSecondary.addView(textViewContact);

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

    private void removeAnnonceSauvegardee(final LinearLayout linearLayout, final ScrollView scrollView) {
        ((LinearLayout)scrollView.getChildAt(0)).getChildAt(0).setBackgroundResource(R.drawable.style_ad_suppressed);
        ((LinearLayout)scrollView.getChildAt(0)).getChildAt(1).setBackgroundResource(R.drawable.style_ad_suppressed);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int maxWidth = displayMetrics.widthPixels;
        int width = (int) scrollView.getX();
        scrollView.animate().translationX(maxWidth - width).alpha(0).setDuration(600);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.removeView(scrollView);
            }
        }, 650);

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
                //LinearLayout.LayoutParams scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                //linearLayoutAdSecondary.setLayoutParams(scrollViewParams);
                for (int i = 0; i < maxSizeInformations; i++) {
                    Handler handler = new Handler();
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
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

        final int maxSizeInformations = linearLayout.getHeight();
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