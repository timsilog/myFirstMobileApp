package com.example.epsilonnutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class EditProfileActivity extends Fragment {
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    return inflater.inflate(R.layout.edit_profile, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Edit Profile");

    getDb();
    initSpinners();
    //do button stuff
    Button b = getActivity().findViewById(R.id.button_submit);
    b.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        updateProfile();
        writeToDb();
        Fragment fragment = new ProfileActivity();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
      }
    });
  }

  public void initSpinners() {
    Spinner feetDropdown = getActivity().findViewById(R.id.spinner_height_feet);
    Integer[] feet = new Integer[] {3, 4, 5, 6, 7, 8};
    ArrayAdapter<Integer> feetAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, feet);
    feetDropdown.setAdapter(feetAdapter);

    Spinner inchesDropdown = getActivity().findViewById(R.id.spinner_height_inches);
    Integer[] inches = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    ArrayAdapter<Integer> inchesAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, inches);
    inchesDropdown.setAdapter(inchesAdapter);

    Spinner genderDropdown = getActivity().findViewById(R.id.spinner_gender);
    String[] genders = new String[] {"Male", "Female"};
    ArrayAdapter<String> genderAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, genders);
    genderDropdown.setAdapter(genderAdapter);

    Spinner activityDropdown = getActivity().findViewById(R.id.spinner_activity);
    String[] levels = new String[] {"Sedentary", "Lightly Active", "Moderately Active", "Very Active"};
    ArrayAdapter<String> activityAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, levels);
    activityDropdown.setAdapter(activityAdapter);
  }

  public void updateProfile() {
    EditText et;
    Spinner sp;
    try {
      et = getActivity().findViewById(R.id.edit_name);
      db.profile.name = et.getText().toString();
      et = getActivity().findViewById(R.id.edit_bday);
      db.profile.bday = et.getText().toString();
      et = getActivity().findViewById(R.id.edit_weight);
      db.profile.weight = Double.parseDouble(et.getText().toString());
      sp = getActivity().findViewById(R.id.spinner_height_feet);
      db.profile.heightFeet = Integer.parseInt(sp.getSelectedItem().toString());
      sp = getActivity().findViewById(R.id.spinner_height_inches);
      db.profile.heightInches = Integer.parseInt(sp.getSelectedItem().toString());
      sp = getActivity().findViewById(R.id.spinner_gender);
      db.profile.gender = sp.getSelectedItem().toString();
      sp = getActivity().findViewById(R.id.spinner_activity);
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
      et = getActivity().findViewById(R.id.edit_age);
      db.profile.age = Integer.parseInt(et.getText().toString());
    } catch (Exception e) {
      Log.e("ILLEGALSTATE", "User didn't fill everything in");
      Toast.makeText(getActivity(), "You must fill in all fields!", Toast.LENGTH_LONG).show();
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
  }

  public void writeToDb() {
    try {
      File file = new File(getActivity().getFilesDir(), "db");
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
