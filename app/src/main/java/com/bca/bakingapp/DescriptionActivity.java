package com.bca.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.io.Serializable;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity implements StepCallback, Serializable, Player.EventListener {
    RecyclerView recyclerViewIngredient;
    RecyclerView recyclerViewStep;
    TextView textViewName;
    TextView textViewServings;
    ToggleButton favorite_button;
    private SimpleExoPlayer simpleExoplayer;
    Long playbackPosition = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        simpleExoplayer = new SimpleExoPlayer.Builder(this).build();
//        playerView.setPlayer(simpleExoplayer);
        textViewName = findViewById(R.id.textViewName);
        textViewServings = findViewById(R.id.textViewServings);
        favorite_button = findViewById(R.id.toggleButton);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
        String id = recipe.getIdRecipe();
        String name = recipe.getNameRecipe();
        String servings = recipe.getNumberServings();
        String ingredients = recipe.getIngredients();
        String steps = recipe.getSteps();

        textViewName.setText(name);
        textViewServings.setText("Number of servings : " + servings);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewIngredient = findViewById(R.id.recyclerViewIngredients);
        IngredientsListAdapter adapterIngre = new IngredientsListAdapter(this);
        recyclerViewIngredient.setLayoutManager(llm);
        recyclerViewIngredient.setAdapter(adapterIngre);

        recyclerViewStep = findViewById(R.id.recyclerViewSteps);
        StepListAdapter stepListAdapter = new StepListAdapter(this);
        recyclerViewStep.setLayoutManager(llm2);
        recyclerViewStep.setAdapter(stepListAdapter);


        DescriptionViewModel descriptionViewModel = new ViewModelProvider(this).get(DescriptionViewModel.class);
        descriptionViewModel.setListIngredients(ingredients);
        descriptionViewModel.getListIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                adapterIngre.setmIngredients(ingredients);
            }
        });

        descriptionViewModel.setListSteps(steps);
        descriptionViewModel.getListSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                stepListAdapter.setmSteps(steps);
            }
        });

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        descriptionViewModel.checkFavorite(recipe);
        favorite_button.setChecked(descriptionViewModel.isFavorite);
        favorite_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);
                if (isChecked == true){
                    descriptionViewModel.insertFavoriteRecipe(recipe);
                } else {
                    descriptionViewModel.deleteFavoriteRecipe(recipe);
                }
            }
        });

    }


    @Override
    public void stepPressed(Step step) {
        Uri videoUri = Uri.parse(step.getLinkStep());
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        simpleExoplayer.setMediaItem(mediaItem);
        simpleExoplayer.prepare();
        simpleExoplayer.play();


    }
}