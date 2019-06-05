
package com.aziz.foodsapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(Meals.Meal meal);

    @Delete
    void delete(Meals.Meal meal);

    @Query("Delete From FavoriteList")
    void DeleteAllNotes();

    @Query("SELECT COUNT(*) from FavoriteList")
    int countUsers();

    @Update
    void update(Meals.Meal meal);

    @Query("SELECT * FROM FavoriteList ")
      List<Meals.Meal> getItems();

}
