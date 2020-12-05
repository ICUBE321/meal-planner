package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class Detail extends AppCompatActivity {

    TextView mealView;
    RequestQueue requestQueue;
    TextView instructionsView;
    LinearLayout scrollView;
    ImageView imageView;
    DisplayMetrics displayMetrics;
    int screen_height;
    int screen_width;
    String[] ingredients;
    DatabaseHelper db;
    String mealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        db = new DatabaseHelper(Detail.this, null, null, 1);

        //screen width and height
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_height = displayMetrics.heightPixels;
        screen_width = displayMetrics.widthPixels;

        ingredients = new String[20];

        mealView = findViewById(R.id.mealName);
        instructionsView = findViewById(R.id.instructions);
        scrollView = findViewById(R.id.ingredientsContainer);
        imageView = findViewById(R.id.mealImage);
        Intent intent = getIntent();
        mealName = intent.getStringExtra("mealName");
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
            String imagePath = obj.getString("strMealThumb");
//            Picasso.get().load(imagePath).resize((screen_width/2) - 100, (screen_width/2) - 100).centerInside().into(imageButton);
            Picasso.get().load(imagePath).into(imageView);
            String recipe = obj.getString("strInstructions");
            instructionsView.setText(recipe);
            instructionsView.setMovementMethod(new ScrollingMovementMethod());

            //add textviews of ingredients
            String[] measure = new String[20];

            for(int i=0; i<20; i++) {
                String indexIngr = "strIngredient"+(i+1);
                ingredients[i] = obj.getString(indexIngr);
                String indexMeasure = "strMeasure"+(i+1);
                measure[i] = obj.getString(indexMeasure);

                if(ingredients[i].equals(null) || ingredients[i].equals("") || ingredients[i].equals("null")) {

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

//    public void addIngredients(View view) {
//        for(int i=0; i < ingredients.length; i++) {
//            if(ingredients[i].equals(null) || ingredients[i].equals("") || ingredients[i].equals("null")) {
//            } else {
//                db.addIngredientToDatabase(ingredients[i], mealName);
//            }
//        }
//        Intent intent = new Intent(this, ShoppingList.class);
////        intent.putExtra("mealIngredients", ingredients);
//        startActivity(intent);
//    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Meals.class);
        startActivity(intent);
    }
}