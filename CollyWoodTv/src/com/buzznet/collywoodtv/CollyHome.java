package com.buzznet.collywoodtv;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CollyHome extends Activity {

	EditText userName;
    EditText password;
	TextView loginerror;
	
	private static String KEY_SUCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "name";
    private static String KEY_NAME = "displayname";
    private static String KEY_EMAIL = "email";
    private static String KEY_DISPLAY = "displayname";
    private static String KEY_ACCESS = "access";
    private static CollyHome mc = null;
    Intent splashy = null;
    CollyUserFunctions cuf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colly_home);
		Button b1 = (Button)findViewById(R.id.button2);
		Button b2 = (Button)findViewById(R.id.button1);
		userName = (EditText)findViewById(R.id.editText1);
		password = (EditText)findViewById(R.id.editText2);
		loginerror = (TextView)findViewById(R.id.errormsg);
		mc=this;
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button2:
				Intent myIntent1 = new Intent(CollyHome.this, com.buzznet.collywoodtv.register.class);
				startActivity(myIntent1);
				break;
			default:
				break;
			}
						
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
                 splashy =  new Intent(getApplicationContext(), splash.class);
                 splashy.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 splashy.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                 startActivity(splashy);
				 cuf = new CollyUserFunctions(mc);
				 String username = userName.getText().toString();
					String passwd = password.getText().toString();
					cuf.loginUser(username, passwd);
					
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void tskCompleted(JSONObject jo)
	{
	
	try{
		
		if(jo.getString(KEY_SUCESS)!=null)
		{
			loginerror.setText("");
			String res = jo.getString(KEY_SUCESS);
			if(Integer.parseInt(res)==1)
			{
				Activity sp =  splash.getinstatce();
			    sp.finish();
				CollyData db = new CollyData(getApplicationContext());
                JSONObject json_user = jo.getJSONObject("user");
                
                cuf.logoutUser(getApplicationContext());
               // db.resetTables();
                db.addUser(json_user.getString(KEY_UID), json_user.getString(KEY_DISPLAY), json_user.getString(KEY_EMAIL));
                Intent dashboard = new Intent(getApplicationContext(), Dashboard.class);
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dashboard.putExtra("user", json_user.getString(KEY_DISPLAY));
                dashboard.putExtra("access", json_user.getString(KEY_ACCESS));
                startActivity(dashboard);
                finish();
			}
			else
			{
				Activity sp =  splash.getinstatce();
			    sp.finish();
				loginerror.setText(jo.getString(KEY_ERROR_MSG));
			}
			
			
		}
		
		
	}
	catch (JSONException e) {
        e.printStackTrace();
    }
	
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.colly_home, menu);
		return true;
	}

}
