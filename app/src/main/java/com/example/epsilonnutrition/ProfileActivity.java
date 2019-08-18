package com.example.epsilonnutrition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;


public class ProfileActivity extends Fragment {
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    return inflater.inflate(R.layout.profile_page, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Profile");

    getDb();

    getData();
    //do button stuff
    Button b = getActivity().findViewById(R.id.button_edit_profile);
    b.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment fragment = new EditProfileActivity();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
      }
    });
  }

  public void getData() {
    TextView tv;
    tv = getActivity().findViewById(R.id.profile_title);
    tv.setText(db.profile.name);
    tv = getActivity().findViewById(R.id.label_bday);
    tv.setText(String.format("Birthday: %s (Age %d)", db.profile.bday, db.profile.age));
    tv = getActivity().findViewById(R.id.label_weight);
    tv.setText(String.format("Weight: %.0flbs", db.profile.weight));
    tv = getActivity().findViewById(R.id.label_height);
    tv.setText(String.format("Height: %d'%d\"", db.profile.heightFeet, db.profile.heightInches));
    tv = getActivity().findViewById(R.id.label_gender);
    tv.setText(String.format("Gender: %s", db.profile.gender));
    tv = getActivity().findViewById(R.id.label_activity);
    tv.setText(String.format("Activity Level: %s", db.profile.activityLvl));
    tv = getActivity().findViewById(R.id.label_bmi);
    String s;
    if (db.profile.bmi < 18.5) {
      s = "You are considered underweight.";
    } else if (db.profile.bmi < 25) {
      s = "You are considered normal weight.";
    } else if (db.profile.bmi < 30) {
      s = "You are considered overweight.";
    } else {
      s = "You are considered obese.";
    }
    tv.setText(String.format("BMI: %.0f\n%s", db.profile.bmi, s));
    tv = getActivity().findViewById(R.id.label_bmr);
    tv.setText(String.format("BMR: %.0f\n(The number of calories you burn sitting still each day)", db.profile.bmr));
    tv = getActivity().findViewById(R.id.label_tdee);
    tv.setText(String.format("TDEE: %.0f\n(The number of estimated calories you burn each day based on your activity level)", db.profile.tdee));
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
