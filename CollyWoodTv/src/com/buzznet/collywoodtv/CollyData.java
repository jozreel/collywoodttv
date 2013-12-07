package com.buzznet.collywoodtv;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CollyData extends SQLiteOpenHelper{
 private static final int DB_VERSION = 1;
 private static final String DB_NAME = "bel1309506483842";
 private static final String TBl_LOGIN = "vdok0z2_users";
 
 private static final String KEY_ID = "ID";
 private static final String KEY_NAME = "display_name";
 private static final String KEY_EMAIL = "user_email";
 private static final String KEY_UID = "user_login";
 
 public CollyData(Context contxt)
 {
	 super(contxt, DB_NAME, null,DB_VERSION);
 }
 

 @Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	 try{
	 String CREATE_USER_TABLE = "CREATE TABLE "+TBl_LOGIN+ "("+KEY_ID+" INTEGER PRIMARY KEY, "
			 +KEY_UID+" TEXT UNIQUE, "
			 +KEY_EMAIL+" TEXT UNIQUE, "
			 +KEY_NAME+" TEXT"+")";
	 db.execSQL(CREATE_USER_TABLE);
	 }
	 catch(SQLException e)
	 {
		 e.printStackTrace();
	 }
}
 
 @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABEL IF EXISTS" + TBl_LOGIN);
		onCreate(db);
	}
 
 public void addUser(String login, String dispname, String email)
 {
	 SQLiteDatabase db = this.getWritableDatabase();
	 ContentValues vals = new ContentValues();
	// String Displaynm = firstName+ " " + lastName;
	 vals.put(KEY_UID, login);
	 vals.put(KEY_NAME, dispname);
	 vals.put(KEY_EMAIL, email);
	 
	 db.insert(TBl_LOGIN, null, vals);
	 db.close();
 }
 
 
 public HashMap<String, String> getUserDetails(){
     HashMap<String,String> user = new HashMap<String,String>();
     String selectQuery = "SELECT  * FROM " + TBl_LOGIN;
       
     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery(selectQuery, null);
     cursor.moveToFirst();
     if(cursor.getCount() > 0){
         user.put("ID", cursor.getString(1));
         user.put("user_login", cursor.getString(2));
         user.put("display_name", cursor.getString(3));
         user.put("email", cursor.getString(4));
     }
     cursor.close();
     db.close();
     // return user
     return user;
 }
 
 public int getRowCount() {
     String countQuery = "SELECT  * FROM " + TBl_LOGIN;
     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery(countQuery, null);
     int rowCount = cursor.getCount();
     db.close();
     cursor.close();
      
     // return row count
     return rowCount;
 }
  
 public void resetTables(){
     SQLiteDatabase db = this.getWritableDatabase();
     // Delete All Rows
     db.delete(TBl_LOGIN, null, null);
     db.close();
 }

 
}
