package com.example.epsilonnutrition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SearchActivity extends Fragment {
  private RequestQueue MyRequestQueue;
  String query;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    return inflater.inflate(R.layout.food_query, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Search Results");
    query = getArguments().getString("QUERY");
    MyRequestQueue = Volley.newRequestQueue(getActivity());
    makeQuery();
  }

  public void makeQuery() {
    // adapter stuff
    ArrayList<Food> arr = new ArrayList();
    final FoodAdapter adapter = new FoodAdapter(getActivity(), arr);
    ListView lv = getActivity().findViewById(R.id.lvItems);
    lv.setAdapter(adapter);

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected item text from ListView
        Food selectedItem = (Food) parent.getItemAtPosition(position);
        Bundle args = new Bundle();
        args.putString("FOOD", selectedItem.name);
        Fragment fragment = new NutritionalActivity();
        fragment.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
//        Intent intent = new Intent(SearchActivity.this, NutritionalActivity.class);
//        intent.putExtra(FOOD, selectedItem.name);
//        startActivity(intent);
      }
    });

    TextView headingView = getActivity().findViewById(R.id.search_heading);
    String url = "https://trackapi.nutritionix.com/v2/search/instant?query=" + query;
    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
      Request.Method.GET,
      url,
      null,
      new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
          //Success Callback
          // do jsonobject stuff
          try {
            JSONObject result;
            JSONArray resultsCommon = (JSONArray)response.get("common");
            JSONArray resultsBranded = (JSONArray)response.get("branded");
            for (int i = 0; i < resultsCommon.length(); i++) {
              result = resultsCommon.getJSONObject(i);
              Food res = new Food();
              res.name = result.get("food_name").toString();
              res.calories = -1.0;
              adapter.add(res);
              Log.d("RESULT", result.get("food_name").toString());
            }

            for (int i = 0; i < resultsBranded.length(); i++) {

              result = resultsBranded.getJSONObject(i);
              Food res = new Food();
              res.name = result.get("food_name").toString();
              res.calories = Double.parseDouble(result.get("nf_calories").toString());
              adapter.add(res);
              Log.d("RESULT", result.get("food_name").toString());
            }
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
      public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-app-id", "9b409f06");
        headers.put("x-app-key", "9646bfdc52e35ab7f72abf84eb0ca436");
        return headers;
      }
    };

    MyRequestQueue.add(jsonObjReq);
    headingView.setText(getString(R.string.search_heading, query));
  }
}
