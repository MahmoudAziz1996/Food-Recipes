
package com.aziz.foodsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aziz.foodsapp.R;
import com.aziz.foodsapp.model.MealDatabase;
import com.aziz.foodsapp.model.Meals;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewMealByCategory extends
        RecyclerView.Adapter<RecyclerViewMealByCategory.RecyclerViewHolder> {

    private List<Meals.Meal> meals;
    private Context context;
    private MealDatabase mealDatabase;

    private static ClickListener clickListener;

    public RecyclerViewMealByCategory(Context context, List<Meals.Meal> meals,MealDatabase database) {
        this.meals = meals;
        this.context = context;
        this.mealDatabase=database;
    }

    @NonNull
    @Override
    public RecyclerViewMealByCategory.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_meal,
                viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewMealByCategory.RecyclerViewHolder viewHolder, int i) {

        String strMealThumb = meals.get(i).getStrMealThumb();
        Picasso.get().load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(viewHolder.mealThumb);
         if(IsFound(mealDatabase,meals.get(viewHolder.getAdapterPosition())))
         {
             viewHolder.mealFav.setChecked(true);
         }
       viewHolder.mealFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {

                   mealDatabase.mealDao().insert(meals.get(viewHolder.getAdapterPosition()));

               }
               else
               {
                   mealDatabase.mealDao().delete(meals.get(viewHolder.getAdapterPosition()));
                   meals.remove(viewHolder.getAdapterPosition());
                   notifyItemRemoved(viewHolder.getAdapterPosition());

               }
           }
       });





        String strMealName = meals.get(i).getStrMeal();
        viewHolder.mealName.setText(strMealName);
    }

    public  boolean IsFound(MealDatabase database, Meals.Meal item)
    {

        List<Meals.Meal> favist=database.mealDao().getItems();
        for (Meals.Meal mm:favist)
        {
            if(item.getIdMeal().equals(mm.getIdMeal()))
            {
                return true;
            }

        }
       return false;

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.mealThumb)
        ImageView mealThumb;
        @BindView(R.id.love)
        ToggleButton mealFav;
        @BindView(R.id.mealName)
        TextView mealName;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }



    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewMealByCategory.clickListener = clickListener;

    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
