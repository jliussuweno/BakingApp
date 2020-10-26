package com.bca.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeCallback, Serializable{

    private RecipeViewModel mRecipeViewModel;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(viewPagerAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        final RecipeListAdapter adapter = new RecipeListAdapter(this);
//        adapter.setCallback(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
//        mRecipeViewModel.initDataRecipe();
//        mRecipeViewModel.getmRecipesNotFavorite().observe(this, new Observer<List<Recipe>>() {
//            @Override
//            public void onChanged(List<Recipe> recipes) {
//                adapter.setmRecipes(recipes);
//            }
//        });
    }

    @Override
    public void recipePressed(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("recipe", (Serializable) recipe);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}