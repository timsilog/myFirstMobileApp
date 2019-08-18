package com.example.epsilonnutrition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NutritionalActivity extends Fragment {
  private RequestQueue MyRequestQueue;
  static Food currentFood;
  String foodName;
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    View rootView = inflater.inflate(R.layout.nutritional_facts, container, false);

    View addButton = rootView.findViewById(R.id.button);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addFood(view);
      }
    });

    return rootView;
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Nutritional Facts");

    MyRequestQueue = Volley.newRequestQueue(getActivity());
    getDb();
    getFacts();
  }

  public void getFacts() {
    foodName = getArguments().getString("FOOD");

    String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    JSONObject jsonBodyObj = new JSONObject();
    try{
      jsonBodyObj.put("query", foodName);
    }catch (JSONException e){
      e.printStackTrace();
    }
    final String requestBody = jsonBodyObj.toString();
    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
    Request.Method.POST,
    url,
    null,
    new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        //Success Callback
        // do jsonobject stuff
        Log.d("RESPONSE", response.toString());
        try {
          JSONArray arr = (JSONArray)response.get("foods");
          JSONObject food = arr.getJSONObject(0);
          Food f = new Food(food);
          currentFood = f;
          TextView tv;
          tv = getActivity().findViewById(R.id.text_name);
          tv.setText(f.name);
          tv = getActivity().findViewById(R.id.text_servingsize);
          tv.setText("Serving Size: 1 (" + f.servingWeightGrams + "g)\n" + f.servingUnit);
          tv = getActivity().findViewById(R.id.text_calories);
          tv.setText("Calories " + f.calories);
          tv = getActivity().findViewById(R.id.text_cff);
          tv.setText(String.format("Calories From Fat %.1f", f.totalFat * 9.0));
          tv = getActivity().findViewById(R.id.text_totalfat);
          tv.setText("Total Fat: " + f.totalFat + "g");
          tv = getActivity().findViewById(R.id.text_sfat);
          tv.setText("Saturated Fat: " +  f.saturatedFat + "g");
          tv = getActivity().findViewById(R.id.text_cholesterol);
          tv.setText("Cholesterol: " + f.cholesterol + "mg");
          tv = getActivity().findViewById(R.id.text_sodium);
          tv.setText("Sodium: " + f.sodium + "mg");
          tv = getActivity().findViewById(R.id.text_potassium);
          tv.setText("Potassium: " + f.potassium + "mg");
          tv = getActivity().findViewById(R.id.text_carbs);
          tv.setText("Total Carbohydrates: " + f.carbs + "g");
          tv = getActivity().findViewById(R.id.text_fiber);
          tv.setText("Dietary Fiber: " + f.fiber + "g");
          tv = getActivity().findViewById(R.id.text_sugar);
          tv.setText("Sugars: " + f.sugar + "g");
          tv = getActivity().findViewById(R.id.text_protein);
          tv.setText("Protein: " + f.protein + "g");
        } catch (org.json.JSONException e) {
          Log.d("JSONObject ERROR", e.toString());
        }
      }
    },
    new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        //Failure Callback
        Log.d("RESPONSE_ERROR", error.toString());
      }
    })
    {
      @Override
      public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-app-id", "9b409f06");
        headers.put("x-app-key", "9646bfdc52e35ab7f72abf84eb0ca436");
        headers.put("x-remote-user-id", "0");
        return headers;
      }
      @Override
      public byte[] getBody() {
        try {
          return requestBody == null ? null : requestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
          VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
          requestBody, "utf-8");
          return null;
        }
      }
    };

    MyRequestQueue.add(jsonObjReq);
  }

  public void addFood(View view) {
    Log.e("TIM_BUTTONCLICK", "adsasd");
    Date d = Calendar.getInstance().getTime();
    String date = (String)DateFormat.format("MM/dd/yyyy", d);
    currentFood.dateConsumed = date;
    try {
      db.foodData.get(date).add(currentFood);
    } catch(NullPointerException e) {
      Log.d("NO FOOD LIST YET", e.toString());
      ArrayList<Food> a = new ArrayList<>();
      a.add(currentFood);
      db.foodData.put(date, a);
    }
    db.profile.caloriesConsumed += currentFood.calories;
    writeToDb();
    Log.e("TIM_TRYING_TO_GO_HOME", "trying");
    Fragment fragment = new HomeActivity();
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.content_frame, fragment);
    ft.commit();
//    Intent goHome = new Intent(NutritionalActivity.this, HomeActivity.class);
//    goHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    startActivity(goHome);
  }

  public void getDb() {
    try {
      Context ctx = getActivity().getApplicationContext();
      FileInputStream fi = ctx.openFileInput("db");
      ObjectInputStream oi = new ObjectInputStream(fi);
      db = (Database)oi.readObject();
      Log.d("TIM_ISIN", "got a db in home");
      fi.close();
      oi.close();
    } catch (FileNotFoundException e) {
      Log.d("TIM_FILENOTFOUND", e.toString());
    } catch (IOException e) {
      Log.d("TIM_IOEXCEPTION", e.toString());
    } catch (ClassNotFoundException e) {
      Log.d("TIM_CLASSNOTFOUND", e.toString());
    }
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
}
