package com.bca.bakingapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Query("SELECT * from recipe_table ORDER BY idRecipe ASC")
    LiveData<List<Recipe>> getFavoriteRecipes();

    @Query("SELECT COUNT(*) FROM recipe_table where idRecipe= :id")
    int checkRecipe(String id);

    @Query("DELETE FROM recipe_table where idRecipe = :id")
    void deleteRecipe(String id);

    @Insert
    void insertRecipe(Recipe recipe);

}
