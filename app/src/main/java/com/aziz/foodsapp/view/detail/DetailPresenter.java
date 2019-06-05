package com.aziz.foodsapp.view.detail;

import android.support.annotation.NonNull;

import com.aziz.foodsapp.Utils;
import com.aziz.foodsapp.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {
    private DetailView view;

    public DetailPresenter(DetailView view) {
        this.view = view;
    }

    void getMealById(String mealName) {
        view.showLoading();
        Call<Meals> mealsCall=Utils.getApi().getMealByName(mealName);

        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call,@NonNull Response<Meals> response) {
                view.hideLoading();
                if(response.body()!=null && response.isSuccessful())
                {
                    view.setMeals(response.body().getMeals().get(0));
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call,@NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });


    }
}
