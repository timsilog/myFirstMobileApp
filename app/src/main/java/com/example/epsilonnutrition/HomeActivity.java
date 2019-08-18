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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends Fragment {

  SearchView searchView;
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File

    return inflater.inflate(R.layout.fragment_menu_1, container, false);
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Home");


    getDb();

    // set remaining calories
    TextView tv = getView().findViewById(R.id.count);
    tv.setText(String.format("Calories Consumed %d/%d", Math.round(db.profile.caloriesConsumed), Math.round(db.profile.tdee)));

    // fill listview with consumed foods
    getConsumedFoods();

    // TEMP
    Button b = getActivity().findViewById(R.id.button);
    b.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        deleteDb();
      }
    });
    // TEMP

    searchView = getView().findViewById(R.id.home_search_bar);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        Fragment fragment = new SearchActivity();
        Bundle args = new Bundle();
        args.putString("QUERY", query);
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();

//        Intent searchIntent = new Intent(HomeActivity.this, SearchActivity.class);
//        searchIntent.putExtra(QUERY, query);
//        startActivity(searchIntent);
        return true;
      }
      @Override
      public boolean onQueryTextChange(String newText) {
        //    adapter.getFilter().filter(newText);
        return false;
      }
    });
  }

  public void getConsumedFoods() {
    Log.e("TIM_GETTINGFOODS", "trying to get consumedd foods");
    TextView label = getView().findViewById(R.id.label_consumed_foods);
    ArrayList<Food> arr;
    Date d = Calendar.getInstance().getTime();
    String date = (String) DateFormat.format("MM/dd/yyyy", d);
    arr = db.foodData.get(date);
    Log.e("TIM_GOTFOODS", "IM IN HERE");
    if (arr == null) {
      arr = new ArrayList();
      label.setText("");
    } else {
      label.setText(String.format("Food consumed for %s", date));
    }
    final FoodAdapter adapter = new FoodAdapter(getActivity(), arr);
    ListView lv = getView().findViewById(R.id.consumed_foods);
    lv.setAdapter(adapter);
  }

  public void getDb() {
    try {
      Context ctx = getActivity().getApplicationContext();
      FileInputStream fi = ctx.openFileInput("db");
      ObjectInputStream oi = new ObjectInputStream(fi);
      db = (Database)oi.readObject();
      Log.e("TIM_ISIN", "got a db in home");
      fi.close();
      oi.close();
    } catch (FileNotFoundException e) {
      Log.e("TIM_FILENOTFOUND", e.toString());
      Intent welcome = new Intent(getActivity(), WelcomeActivity.class);
      startActivity(welcome);
      getActivity().finish();
    } catch (IOException e) {
      Log.e("TIM_IOEXCEPTION", e.toString());
      Intent welcome = new Intent(getActivity(), WelcomeActivity.class);
      startActivity(welcome);
      getActivity().finish();
    } catch (ClassNotFoundException e) {
      Log.e("TIM_CLASSNOTFOUND", e.toString());
    }
  }

  //
  public void deleteDb() {
    File dir = getActivity().getFilesDir();
    File file = new File(dir, "db");
    boolean deleted = file.delete();
  }
}
