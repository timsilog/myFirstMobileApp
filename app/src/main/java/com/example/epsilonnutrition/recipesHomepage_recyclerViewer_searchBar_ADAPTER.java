package com.example.epsilonnutrition;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recipesHomepage_recyclerViewer_searchBar_ADAPTER extends RecyclerView.Adapter<recipesHomepage_recyclerViewer_searchBar_ADAPTER.RecipeViewHolder> implements Filterable {
    private Context context_object;
    private List<Recipes> recipesList;
    private List<Recipes> recipesList_copy; //For search functionality

    /*
        Provide a reference to the views for each data item
        Complex data items may need more than one view per item, and
        you provide access to all the views for a data item in a view holder
    */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CheckBox vegan_checkBox, vegetarian_checkBox, glutenFree_checkBox;
        TextView recipe_name, ingredient_names, serving_size, calories, total_fat, total_carbs, total_protein;

        public RecipeViewHolder(View itemView_object) {
            super(itemView_object);

            vegan_checkBox = itemView_object.findViewById(R.id.veganCheckBox);
            vegetarian_checkBox = itemView_object.findViewById(R.id.vegetarianCheckBox);
            glutenFree_checkBox = itemView_object.findViewById(R.id.glutenFreeCheckBox);
            recipe_name = itemView_object.findViewById(R.id.recipeName);
            ingredient_names = itemView_object.findViewById(R.id.displayIngredients);
            serving_size = itemView_object.findViewById(R.id.displayServingSize);
            calories = itemView_object.findViewById(R.id.caloriesUSER_INPUT);
            total_fat = itemView_object.findViewById(R.id.totalFat_USER_INPUT);
            total_carbs = itemView_object.findViewById(R.id.totalCarbs_USER_INPUT);
            total_protein = itemView_object.findViewById(R.id.totalProtein_USER_INPUT);
        }
    }

    //Constructor
    public recipesHomepage_recyclerViewer_searchBar_ADAPTER(Context input_contextObject, List<Recipes> inputRecipes_list) {
        this.context_object = input_contextObject;
        this.recipesList = inputRecipes_list;
        recipesList_copy = new ArrayList<>(recipesList); //Makes a copy of recipes list
    }

    // Create new views (invoked by the layout manager)
    @Override
    public recipesHomepage_recyclerViewer_searchBar_ADAPTER.RecipeViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                int viewType) {
        //This tells the recycleViewer to use the card layout you made
        LayoutInflater card_layoutInflater = LayoutInflater.from(context_object);

        View view_object;

        //view_object = card_layoutInflater.inflate(R.layout.recipe_card_view_layout_with_nutritional_information, parent, false);


        view_object = card_layoutInflater.inflate(R.layout.recipe_card_view_layout_with_nutritional_information, parent, false);

        RecipeViewHolder recipeViewHolder_object = new RecipeViewHolder(view_object);
        return recipeViewHolder_object;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Recipes recipe_object = recipesList.get(position);

        holder.recipe_name.setText(recipe_object.getName_recipe());
        holder.ingredient_names.setText(recipe_object.getName_ingredient().toLowerCase());
        holder.serving_size.setText(String.valueOf(recipe_object.getNum_servings()));
        holder.vegan_checkBox.setChecked(recipe_object.isVeganFlag());
        holder.vegetarian_checkBox.setChecked(recipe_object.isVegetarianFlag());
        holder.glutenFree_checkBox.setChecked(recipe_object.isGlutenFreeFlag());
        holder.calories.setText(String.valueOf(recipe_object.getCalories()));
        holder.total_fat.setText(String.valueOf(recipe_object.getTotal_fat()));
        holder.total_carbs.setText(String.valueOf(recipe_object.getTotal_carbs()));
        holder.total_protein.setText(String.valueOf(recipe_object.getTotal_protein()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    //Filtering for real-time search results
    @Override
    public Filter getFilter(){
        return recipesFilter;
    }

    private Filter recipesFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Recipes> filtered_recipesList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) //If nothing is typed into recipes searchView
            {
                filtered_recipesList.addAll(recipesList_copy);
            }
            else
            {
                String recipes_filterPattern = constraint.toString().toLowerCase().trim();

                for(Recipes recipe_item : recipesList_copy)
                    if(recipe_item.getName_recipe().toLowerCase().contains(recipes_filterPattern))
                    {
                        filtered_recipesList.add(recipe_item);
                    }
            }

            FilterResults recipes_searchResults = new FilterResults();
            recipes_searchResults.values = filtered_recipesList;
            return recipes_searchResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recipesList.clear();
            //@SuppressWarnings("unchecked")
            recipesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}