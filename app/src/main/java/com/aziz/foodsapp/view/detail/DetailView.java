package com.aziz.foodsapp.view.detail;

import com.aziz.foodsapp.model.Meals;

public interface DetailView {

    void showLoading();
    void hideLoading();
    void setMeals(Meals.Meal meal);
    void onErrorLoading(String message);
}
