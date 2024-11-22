package com.example.hackaton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Student_Activities.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FULLNAME = "Full_name";
    private static final String COLUMN_LOGIN = "Login";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_GROUP = "Group_number";
    private static final String COLUMN_INTERESTS = "Interests";

    private static final String TABLE_NAME1 = "Events";
    private static final String COLUMN_ID1 = "_id";
    private static final String COLUMN_EVENT_NAME = "Event_name";
    private static final String COLUMN_CATEGORY = "Category";
    private static final String COLUMN_INTEREST_TAG = "Interest";
    private static final String COLUMN_DATE = "Date";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLNAME + " TEXT, " +
                COLUMN_LOGIN + " TEXT, " +
                COLUMN_PASSWORD + " INTEGER, " +
                COLUMN_GROUP + " TEXT," +
                COLUMN_INTERESTS + " TEXT);";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_NAME1 +
                " (" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_INTEREST_TAG + " TEXT, " +
                COLUMN_CATEGORY + " TEXT,"+
                COLUMN_DATE + " DATE);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    void addUser(String fullname, String login, int password,String group_number,String interests){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FULLNAME, fullname);
        cv.put(COLUMN_LOGIN, login);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_GROUP, group_number);
        cv.put(COLUMN_INTERESTS, interests);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void addEvent(String event_name, String interest_tag, String category, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_NAME, event_name);
        cv.put(COLUMN_INTEREST_TAG, interest_tag);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_DATE, date);
        long result = db.insert(TABLE_NAME1,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllDataFromEvents(){
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FULLNAME, title);
        cv.put(COLUMN_LOGIN, author);
        cv.put(COLUMN_PASSWORD, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    void getData(List<String> login_list, List<Integer> password_list, List<String> group_number_list){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query("Users", null, null, null, null, null, null);
        if(c!=null&&c. moveToFirst()){
            do{
                String login = c.getString(c.getColumnIndexOrThrow ("Login"));
                int password  = c.getInt(c.getColumnIndexOrThrow ("Password"));
                String group_number = c.getString(c.getColumnIndexOrThrow("Group_number"));
                login_list.add(login);
                password_list.add(password);
                group_number_list.add(group_number);
            }while(c.moveToNext());
        }
    }
}
