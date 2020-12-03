package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Meals extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RelativeLayout main;
    Spinner dropdown;
    RequestQueue requestQueue;
    int viewLength; //keep track of view length
    SearchView searchView;
    DisplayMetrics displayMetrics;
    int screen_height;
    int screen_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals);

        //screen width and height
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_height = displayMetrics.heightPixels;
        screen_width = displayMetrics.widthPixels;

        main = findViewById(R.id.recipesLayout);
        //for search button
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String params = "https://www.themealdb.com/api/json/v1/1/search.php?s="+ query;
                getMealsResponse(params);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //For the meals
        //using Volley to access the api
        String params = "https://www.themealdb.com/api/json/v1/1/search.php?f="+getRandomChar();
        requestQueue = Volley.newRequestQueue(this);
        getMealsResponse(params);

        //For the category dropdown
        //get the spinner from the xml.
        dropdown = findViewById(R.id.categoryDropdown);

        dropdown.setOnItemSelectedListener(this);
        //using Volley to access the api
        String categoryParams = "https://www.themealdb.com/api/json/v1/1/list.php?c=list";
        getCategoryResponse(categoryParams);

    }

    //function that takes meal url and returns meals jsonobject
    private void getMealsResponse(String params) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("mealLogResponse", response.toString());
                try {
                    ParseMealsJSON(response);
                } catch (JSONException e) {
                    Toast.makeText(Meals.this, "NO RESULTS FOUND", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mealLogError", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //function that takes category url and returns category jsonobject
    private void getCategoryResponse(String params) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("mealLogResponse", response.toString());
                try {
                    ParseCategoryJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mealLogError", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //function for getting category names
    private boolean ParseCategoryJSON(JSONObject json) throws JSONException {
        String jsonResult = "";
        //create a list of items for the spinner.
        List<String> items = new ArrayList<String>();
        JSONObject JsonObject = json;
        JSONArray meals = jsonHelperGetJSONArray(JsonObject, "meals");
        items.add("All Categories");
        for(int i=0; i<meals.length(); i++) {
            JSONObject obj = meals.getJSONObject(i);
            String categoryName = obj.getString("strCategory");
            items.add(categoryName);
        }

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        if(items != null) {
            return true;
        } else {
            return false;
        }
    }

    //function for getting the recipe names and images
    private boolean ParseMealsJSON(JSONObject json) throws JSONException {
        if(viewLength > 0) {
            for(int j=0; j<viewLength; j++){
                main.removeView(findViewById(j + 1));
            }
        }

        String jsonResult = "";
        JSONObject JsonObject = json;
        JSONArray meals = jsonHelperGetJSONArray(JsonObject, "meals");
        if(meals != null) {
            Log.d("mealLogArray", meals.toString());
            viewLength = meals.length();
            for( int i=0; i<meals.length(); i++) {
                View hiddenLayout = LayoutInflater.from(this).inflate(R.layout.hidden_layout, main, false);
                hiddenLayout.setId(i + 1);
                //imageButton
                ImageButton imageButton = (ImageButton) hiddenLayout.findViewById(R.id.recipeImage);
                JSONObject obj = meals.getJSONObject(i);
                String imagePath = obj.getString("strMealThumb");
                Log.d("mealLogImage", imagePath);
                Picasso.get().load(imagePath).resize((screen_width/2) - 100, (screen_width/2) - 100).centerInside().into(imageButton);

                //textView
                TextView textView = (TextView) hiddenLayout.findViewById(R.id.recipeName);
//            Log.d("mealLogObject", obj.toString());
                String mealName = obj.getString("strMeal");
                Log.d("mealLogName", mealName);
                textView.setText(mealName);
                textView.setWidth((screen_width/2) - 100);
                //position settings
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(i > 0) {
                    //if i is one
                    if(i == 1) {
                        //place below the dropdown and to the right of the first picture
                        lp.addRule(RelativeLayout.RIGHT_OF, i);
                        lp.addRule(RelativeLayout.BELOW, R.id.categoryDropdown);
                    } else if(i % 2 != 0) {//if i is odd
                        //place layout to right of prev layout
                        lp.addRule(RelativeLayout.RIGHT_OF, i);
                        lp.addRule(RelativeLayout.BELOW, (i-1));
                    } else {
                        //if i is even, place under the layout on the left side
                        lp.addRule(RelativeLayout.BELOW, (i-1));
                    }
//                    lp.addRule(RelativeLayout.BELOW, i);
                } else {
                    lp.addRule(RelativeLayout.BELOW, R.id.categoryDropdown);
                }

                hiddenLayout.setLayoutParams(lp);
                main.addView(hiddenLayout);
            }
        } else {
            Toast.makeText(Meals.this, "NO RESULTS FOUND", Toast.LENGTH_LONG).show();
            dropdown.setSelection(0);
            String params = "https://www.themealdb.com/api/json/v1/1/search.php?f="+getRandomChar();
            showMealsByCategory(params);
        }

        if(meals != null) {
            return true;
        } else {
            return false;
        }
    }

    //json parsers
    private String jsonHelperGetString(JSONObject obj, String k){
        String v = null;
        try {
            v = obj.getString(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }
    private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
        JSONObject o = null;
        try {
            o = obj.getJSONObject(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
    private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
        JSONArray a = null;
        try {
            a = obj.getJSONArray(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }

    //function for running Volley to showing meals by category
    private void showMealsByCategory(String params) {

        JsonObjectRequest jsonObjectCategoryRequest = new JsonObjectRequest(Request.Method.GET, params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("mealLogResponse", response.toString());
                try {
                    ParseMealsJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mealLogError", error.toString());
            }
        });
        requestQueue.add(jsonObjectCategoryRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCategory = (String) parent.getItemAtPosition(position);
        Log.d("mealLogSelected", selectedCategory);
        String params;
        //For the meals
        //using Volley to access the api
        if(selectedCategory == "All Categories") {
            params = "https://www.themealdb.com/api/json/v1/1/search.php?f="+getRandomChar();
        } else {
            params = "https://www.themealdb.com/api/json/v1/1/filter.php?c="+selectedCategory;
        }
        Log.d("mealLogParams", params);
        showMealsByCategory(params);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getMealName(View view) {
        Log.d("mealLogView", view.toString());
        ViewGroup parent = (ViewGroup) view.getParent();
        TextView mealName = (TextView) parent.findViewById(R.id.recipeName);
        String meal = String.valueOf(mealName.getText());
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("mealName", meal);
        startActivity(intent);
//        Toast.makeText(this, meal+" chosen!", Toast.LENGTH_LONG).show();
    }

    public char getRandomChar() {
        Random rnd = new Random();
        char c = (char) ('a' + rnd.nextInt(26));
        return c;
    }

}