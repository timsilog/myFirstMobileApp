package com.example.epsilonnutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WelcomeActivity extends AppCompatActivity {
  Database db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.welcome);

    initSpinners();
    createDb();
  }

  public void initSpinners() {
    Spinner feetDropdown = findViewById(R.id.spinner_height_feet);
    Integer[] feet = new Integer[] {3, 4, 5, 6, 7, 8};
    ArrayAdapter<Integer> feetAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, feet);
    feetDropdown.setAdapter(feetAdapter);

    Spinner inchesDropdown = findViewById(R.id.spinner_height_inches);
    Integer[] inches = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    ArrayAdapter<Integer> inchesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, inches);
    inchesDropdown.setAdapter(inchesAdapter);

    Spinner genderDropdown = findViewById(R.id.spinner_gender);
    String[] genders = new String[] {"Male", "Female"};
    ArrayAdapter<String> genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders);
    genderDropdown.setAdapter(genderAdapter);

    Spinner activityDropdown = findViewById(R.id.spinner_activity);
    String[] levels = new String[] {"Sedentary", "Lightly Active", "Moderately Active", "Very Active"};
    ArrayAdapter<String> activityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levels);
    activityDropdown.setAdapter(activityAdapter);
  }

  public void createProfile(View view) {
    EditText et;
    Spinner sp;
    try {
      et = findViewById(R.id.edit_name);
      db.profile.name = et.getText().toString();
      et = findViewById(R.id.edit_bday);
      db.profile.bday = et.getText().toString();
      et = findViewById(R.id.edit_weight);
      db.profile.weight = Double.parseDouble(et.getText().toString());
      sp = findViewById(R.id.spinner_height_feet);
      db.profile.heightFeet = Integer.parseInt(sp.getSelectedItem().toString());
      sp = findViewById(R.id.spinner_height_inches);
      db.profile.heightInches = Integer.parseInt(sp.getSelectedItem().toString());
      sp = findViewById(R.id.spinner_gender);
      db.profile.gender = sp.getSelectedItem().toString();
      sp = findViewById(R.id.spinner_activity);
      switch(sp.getSelectedItemPosition()) {
        case 0:
          db.profile.activityLvl = ActivityLvl.SEDENTARY;
          break;
        case 1:
          db.profile.activityLvl = ActivityLvl.LIGHTLY_ACTIVE;
          break;
        case 2:
          db.profile.activityLvl = ActivityLvl.MODERATELY_ACTIVE;
          break;
        case 3:
          db.profile.activityLvl = ActivityLvl.VERY_ACTIVE;
          break;
      }
      et = findViewById(R.id.edit_age);
      db.profile.age = Integer.parseInt(et.getText().toString());
    } catch (Exception e) {
      Log.e("ILLEGALSTATE", "User didn't fill everything in");
      Toast.makeText(this, "You must fill in all fields!", Toast.LENGTH_LONG).show();
      return;
    }
    int height = (db.profile.heightFeet * 12 + db.profile.heightInches);
    db.profile.bmi = 703 * db.profile.weight / (height * height);
    if (db.profile.gender == "male") {
      db.profile.bmr = ((6.24 * db.profile.weight) + (12.7 * height) - (6.755 * db.profile.age) + 66.47);
    } else {
      db.profile.bmr = ((4.35 * db.profile.weight) + (4.7 * height) - (4.7 * db.profile.age) + 655.1);
    }
    getTdee();
    try {
      File file = new File(getFilesDir(), "db");
      FileOutputStream f = new FileOutputStream(file);
      ObjectOutputStream o = new ObjectOutputStream(f);
      o.writeObject(db);
      o.close();
      f.close();
    } catch(FileNotFoundException er) {
      Log.d("TIM_STILL_FILENOTFOUND", er.toString());
    } catch(IOException er) {
      Log.d("TIM_STILL_IOERROR", er.toString());
    }
    Log.d("TIM_DBWRITE", "Successfully wrote profile to db moving to home");
    Intent goHome = new Intent(WelcomeActivity.this, MainActivity.class);
    startActivity(goHome);
    finish();
  }

  public void createDb() {
    try {
      File file = new File(getFilesDir(), "db");
      FileOutputStream f = new FileOutputStream(file);
      ObjectOutputStream o = new ObjectOutputStream(f);
      db = new Database();
      db.profile.name = "worked";
      o.writeObject(db);
      o.close();
      f.close();
    } catch(FileNotFoundException er) {
      Log.d("TIM_STILL_FILENOTFOUND", er.toString());
    } catch(IOException er) {
      Log.d("TIM_STILL_IOERROR", er.toString());
    }
  }

  public void getTdee() {
    switch (db.profile.activityLvl) {
      case SEDENTARY:
        db.profile.tdee = db.profile.bmr * 1.2;
      case LIGHTLY_ACTIVE:
        db.profile.tdee = db.profile.bmr * 1.375;
      case MODERATELY_ACTIVE:
        db.profile.tdee = db.profile.bmr * 1.5;
      case VERY_ACTIVE:
        db.profile.tdee = db.profile.bmr * 1.725;
    }
  }
}
