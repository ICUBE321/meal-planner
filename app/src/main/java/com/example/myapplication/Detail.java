package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail extends AppCompatActivity {

    TextView mealView;
    RequestQueue requestQueue;
    TextView instructionsView;
    LinearLayout scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        mealView = findViewById(R.id.mealName);
        instructionsView = findViewById(R.id.instructions);
        scrollView = findViewById(R.id.ingredientsContainer);
        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealName");
        mealView.setText(mealName);
        requestQueue = Volley.newRequestQueue(this);

        mealName = mealName.trim();
        mealName = mealName.replaceAll(" ", "%20");
        Log.d("detailLogMealName", mealName);
        String params = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + mealName;
//        String params = "https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata";
        Log.d("detailLogParams", params);
        getIngredientsResponse(params);

    }

    private void getIngredientsResponse(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ParseIngredientsJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private boolean ParseIngredientsJSON(JSONObject json) throws JSONException {
        JSONObject JsonObject = json;
        if(json != null) {
            JSONArray meals = jsonHelperGetJSONArray(JsonObject, "meals");
            JSONObject obj = meals.getJSONObject(0);
            String recipe = obj.getString("strInstructions");
            instructionsView.setText(recipe);
            instructionsView.setMovementMethod(new ScrollingMovementMethod());

            //add textviews of ingredients
            String[] ingredients = new String[20];
            String[] measure = new String[20];

            for(int i=0; i<20; i++) {
                String indexIngr = "strIngredient"+(i+1);
                ingredients[i] = obj.getString(indexIngr);
                String indexMeasure = "strMeasure"+(i+1);
                measure[i] = obj.getString(indexMeasure);

                if(ingredients[i] == null || ingredients[i] == "") {

                } else {
                    TextView ingredient = new TextView(this);
                    ingredient.setId((i+1));
                    ingredient.setText(ingredients[i]);
//                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT
//                    );
//                    ingredient.setLayoutParams(lp);
                    scrollView.addView(ingredient);
                    Log.d("mealIngredients", ingredients[i]);
                }

            }

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
}