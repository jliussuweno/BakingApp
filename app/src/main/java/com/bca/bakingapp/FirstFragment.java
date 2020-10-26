package com.bca.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.List;

public class FirstFragment extends Fragment implements RecipeCallback {

    private RecipeViewModel mRecipeViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(getContext());
        adapter.setCallback((RecipeCallback) getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.initDataRecipe();
        mRecipeViewModel.getmRecipesNotFavorite().observe((LifecycleOwner) getContext(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setmRecipes(recipes);
            }
        });

    }

    @Override
    public void recipePressed(Recipe recipe) {
        Intent intent = new Intent(getContext(), DescriptionActivity.class);
        intent.putExtra("recipe", (Serializable) recipe);
        startActivity(intent);
    }
}