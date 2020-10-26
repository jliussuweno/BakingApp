package com.bca.bakingapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mFavoriteRecipes;

    RecipeRepository(Application application){
            RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
            mRecipeDao = db.recipeDao();
            mFavoriteRecipes = mRecipeDao.getFavoriteRecipes();
    }

    LiveData<List<Recipe>> getmFavoriteRecipes(){
        return mFavoriteRecipes;
    }

    public void insert(Recipe recipe){
        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private RecipeDao mAsyncRecipeDao;

        insertAsyncTask(RecipeDao dao){
            mAsyncRecipeDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncRecipeDao.insert(params[0]);
            return null;
        }
    }

}
