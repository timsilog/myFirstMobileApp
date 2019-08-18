package com.example.epsilonnutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NutritionActivity extends Fragment {
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    return inflater.inflate(R.layout.nutrition_page, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Nutrition");

    getDb();

    getData();

  }

  public void getData() {
    Double protein = 0.0,
      carbs = 0.0,
      fat = 0.0,
      sFat = 0.0,
      cholesterol = 0.0,
      sodium = 0.0,
      potassium = 0.0,
      fiber = 0.0,
      sugar = 0.0;
    Date d = Calendar.getInstance().getTime();
    String date = (String) DateFormat.format("MM/dd/yyyy", d);
    try {
      ArrayList<Food> arr = db.foodData.get(date);
      for(Food food : arr) {
        protein += food.protein;
        carbs += food.carbs;
        fat += food.totalFat;
        sFat += food.saturatedFat;
        cholesterol += food.cholesterol;
        sodium += food.sodium;
        potassium += food.potassium;
        fiber += food.fiber;
        sugar += food.sugar;
      }
    } catch (Exception e) {
      Log.e("NO_FOODS_CONSUMED_YET", "go eat something");
    }
    TextView tv;
    tv = getActivity().findViewById(R.id.text_calories);
    tv.setText(String.format("Calories Consumed %d/%d", Math.round(db.profile.caloriesConsumed), Math.round(db.profile.tdee)));
    tv = getActivity().findViewById(R.id.text_protein);
    tv.setText(String.format("Protein: %dg", Math.round(protein)));
    tv = getActivity().findViewById(R.id.text_carbs);
    tv.setText(String.format("Carbohydrates: %dg", Math.round(carbs)));
    tv = getActivity().findViewById(R.id.text_totalfat);
    tv.setText(String.format("Total Fat: %dg", Math.round(fat)));
    tv = getActivity().findViewById(R.id.text_sfat);
    tv.setText(String.format("Saturdated Fat: %dg", Math.round(sFat)));
    tv = getActivity().findViewById(R.id.text_cholesterol);
    tv.setText(String.format("Cholesterol: %dmg", Math.round(cholesterol)));
    tv = getActivity().findViewById(R.id.text_sodium);
    tv.setText(String.format("Sodium: %dmg", Math.round(sodium)));
    tv = getActivity().findViewById(R.id.text_potassium);
    tv.setText(String.format("Pottassium: %dmg", Math.round(potassium)));
    tv = getActivity().findViewById(R.id.text_fiber);
    tv.setText(String.format("Fiber: %dmg", Math.round(fiber)));
    tv = getActivity().findViewById(R.id.text_sugar);
    tv.setText(String.format("Sugar: %dg", Math.round(sugar)));
  }

  public void getDb() {
    try {
      Context ctx = getActivity().getApplicationContext();
      FileInputStream fi = ctx.openFileInput("db");
      ObjectInputStream oi = new ObjectInputStream(fi);
      db = (Database)oi.readObject();
      Log.e("TIM_ISIN", "got a db in profile");
      fi.close();
      oi.close();
    } catch (FileNotFoundException e) {
      Log.e("TIM_FILENOTFOUND", e.toString());
      Intent welcome = new Intent(getActivity(), WelcomeActivity.class);
      startActivity(welcome);
      getActivity().finish();
    } catch (IOException e) {
      Log.e("TIM_IOEXCEPTION", e.toString());
    } catch (ClassNotFoundException e) {
      Log.e("TIM_CLASSNOTFOUND", e.toString());
    }
  }
}
