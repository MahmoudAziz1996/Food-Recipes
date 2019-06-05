
package com.aziz.foodsapp.view.favorite;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.aziz.foodsapp.R;
import com.aziz.foodsapp.adapter.RecyclerViewMealByCategory;
import com.aziz.foodsapp.model.MealDatabase;
import com.aziz.foodsapp.model.Meals;
import com.aziz.foodsapp.view.detail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aziz.foodsapp.view.home.HomeActivity.EXTRA_DETAIL;

public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewfavorite)
    RecyclerView recyclerView;

    MealDatabase mealDatabase;
    List<Meals.Meal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        Toolbar toolbar =  findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mealDatabase= Room.
                databaseBuilder(this,MealDatabase.class,"fav").
                allowMainThreadQueries()
                .build();

        list=mealDatabase.mealDao().getItems();
        RecyclerViewMealByCategory adapter =
                new RecyclerViewMealByCategory(this, list ,mealDatabase);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new RecyclerViewMealByCategory.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView mm = view.findViewById(R.id.mealName);
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_DETAIL, mm.getText().toString());
                FavoriteActivity.this.startActivity(intent);
            }
        });
    }
}
