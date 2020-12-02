package com.example.cook_at_home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table IF EXISTS " + USER_TABLE + ";");
        db.execSQL("Drop table IF EXISTS "+ ALLERGIES_TABLE + ";");
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


}
