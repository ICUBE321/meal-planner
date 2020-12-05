package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cookathome.db";
    private static final String USER_TABLE = "users";
    private static final String ALLERGIES_TABLE = "allergies";
    private static final String COL_1 = "userid";
    private static final String COL_2 = "firstName";
    private static final String COL_3 = "lastName";
    private static final String COL_4 = "userName";
    private static final String COL_5 = "password";
    private static final String COL_6 = "email";

    private static final String SHOPPING_LIST_TABLE = "Shoppinglist";
    private static final String COL_7 = "ingredientId";
    private static final String COL_8 = "ingredientName";
    private static final String COL_9 = "recipeName";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String clearDBQuery = "DELETE FROM " + USER_TABLE;
//        db.execSQL(clearDBQuery);
//        clearDBQuery = "DELETE FROM " + ALLERGIES_TABLE;
//        db.execSQL(clearDBQuery);
//        clearDBQuery = "DELETE FROM " + SHOPPING_LIST_TABLE;
//        db.execSQL(clearDBQuery);

        String createTableUsers = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "( " +
                COL_1 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " Text NOT NULL," +
                COL_3 + " Text NOT NULL," +
                COL_4 + " Text NOT NULL," +
                COL_5 + " Text NOT NULL," +
                COL_6 + " Text NOT NULL)" + ";";
        db.execSQL(createTableUsers);

        String createTableAllergies = "CREATE TABLE IF NOT EXISTS " + ALLERGIES_TABLE + "(" +
                "userid Integer NOT NULL, "+
                "username Text NOT NULL, "+
                "allergies Text NOT NULL,"+
                " FOREIGN KEY (userid) REFERENCES users (userid))"+";";
        db.execSQL(createTableAllergies);

        String createTable = "CREATE TABLE IF NOT EXISTS " + SHOPPING_LIST_TABLE + "(" +
                COL_7 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_8 + " Text NOT NULL," +
                COL_9 +  " Text NOT NULL)" + ";" ;


        Log.d("DBText","createTable: "+createTable);
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table IF EXISTS " + USER_TABLE + ";");
        db.execSQL("Drop table IF EXISTS "+ ALLERGIES_TABLE + ";");
        db.execSQL("Drop table IF EXISTS "+ SHOPPING_LIST_TABLE + ";");
    }

    public boolean addAllergies(String username, ArrayList<String> allergies){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("allergies", String.valueOf(allergies));

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(ALLERGIES_TABLE, null, contentValues);
        db.close();

        if(result == 0) return false;
        else return true;
    }

    public boolean addUsers(String fname, String lname, String uname, String pword, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fname);
        contentValues.put(COL_3, lname);
        contentValues.put(COL_4, uname);
        contentValues.put(COL_5, pword);
        contentValues.put(COL_6, email);

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(USER_TABLE, null, contentValues);
        db.close();


        if(result == 0) return  false;
        else return true;

    }

    public boolean checkUsers(String uname, String pword){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COL_4 + " = " + "'" + uname +
                "'" + " and " + COL_5 + " = " + "'" + pword + "'" + ";";
        Cursor result = db.rawQuery(query, null);
        int rowCount = result.getCount();
        result.close();
        if(rowCount > 0) return true;
        else  return false;

    }

    public boolean validateUsername(String uname){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COL_4 + " = " + "'" + uname + "'" + ";";
        Cursor result = db.rawQuery(query, null);
        int rowCount = result.getCount();
        result.close();
        if(rowCount == 0) return true;
        else  return false;
    }

    public Cursor getAllergy(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ALLERGIES_TABLE + " WHERE 'username'"+"="+ "'" + user + "'"+";",null);
        return data;
    }

    public boolean addIngredientToDatabase(String ingredientName, String recipeName) {
        ContentValues values= new ContentValues();
        values.put(COL_8, ingredientName);
        values.put(COL_9, recipeName);

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(SHOPPING_LIST_TABLE,null,values);
        db.close();

        if (result ==0) return false;
        else
            return true;
    }


}
