package com.brandshoutcom.emedicals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.brandshoutcom.panning.HorizontalPanning;
import com.brandshoutcom.panning.PanningView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class HomeActivity extends AppCompatActivity {
    private PanningView panningGraph;
    private Toolbar toolbar;
    private CardView card_quest, card_prof, card_med, card_search;
    private ImageView im_quest, im_prof, im_med, im_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initPanning();
    }

    private void initPanning() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("eMedical");
        panningGraph = (PanningView) findViewById(R.id.panning);
        card_quest = (CardView) findViewById(R.id.card_quest);
        card_prof = (CardView) findViewById(R.id.card_prof);
        card_med = (CardView) findViewById(R.id.card_med);
        card_search = (CardView) findViewById(R.id.card_search);

        im_quest = (ImageView) findViewById(R.id.im_quest);
        im_prof = (ImageView) findViewById(R.id.im_prof);
        im_med = (ImageView) findViewById(R.id.im_med);
        im_search = (ImageView) findViewById(R.id.im_search);

        YoYo.with(Techniques.BounceInUp).duration(1250).playOn(im_search);
        YoYo.with(Techniques.BounceInUp).duration(1500).playOn(im_quest);
        YoYo.with(Techniques.BounceInUp).duration(1750).playOn(im_med);
        YoYo.with(Techniques.BounceInUp).duration(2000).playOn(im_prof);

        HorizontalPanning horizontalPanning = new HorizontalPanning(HorizontalPanning.RIGHT_TO_LEFT);
        horizontalPanning.setInterpolator(new DecelerateInterpolator());
        horizontalPanning.setStartXOffset(1f);
        horizontalPanning.setEndXOffset(1f);

        panningGraph.setPanning(horizontalPanning);
    }

    public void loadSearch(View v) {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    public void loadMed(View v) {
//        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    public void loadProf(View v) {
//        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    public void loadQuest(View v) {
//        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }
}
