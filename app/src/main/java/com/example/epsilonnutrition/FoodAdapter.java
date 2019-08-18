package com.example.epsilonnutrition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {
  public FoodAdapter(Context context, ArrayList<Food> results) {
    super(context, 0, results);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Food result = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_cals, parent, false);
    }
    // Lookup view for data population
    TextView foodName = convertView.findViewById(R.id.queryName);
    TextView foodCals = convertView.findViewById(R.id.queryCals);
    // Populate the data into the template view using the data object
    foodName.setText(result.name);
    if (result.calories >= 0) {
      foodCals.setText(Math.round(result.calories) + " calories");
    } else {
      foodCals.setText("");
    }
    // Return the completed view to render on screen
    return convertView;
  }
}

