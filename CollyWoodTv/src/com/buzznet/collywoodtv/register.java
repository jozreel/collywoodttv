package com.buzznet.collywoodtv;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class register extends Activity {
	Button btnRegister;
	EditText username;
	EditText fname;
	EditText lName;
	EditText emailad;
	CheckBox accept;
	TextView error;
	private static String KEY_SUCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "user_login";
    private static String KEY_NAME = "display_name";
    private static String KEY_EMAIL = "email";
    private static String KEY_DISPLAY = "display_name";
    register ca;
    CollyUserFunctions cuf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		username = (EditText)findViewById(R.id.editText1);
		fname = (EditText)findViewById(R.id.editText4);
		emailad =(EditText)findViewById(R.id.editText3);
		lName = (EditText)findViewById(R.id.editText5);
		accept = (CheckBox)findViewById(R.id.checkBox1);
		btnRegister =(Button)findViewById(R.id.button1);
	     error = (TextView)findViewById(R.id.textView3);
		//btnRegister.setEnabled(false);
		ca=this;
	/*	accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
					btnRegister.setEnabled(true);
				else
					btnRegister.setEnabled(false);
				
			}
		});*/
		
	     
	     
	     
	     
	     
	     

	     
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				
				
        
				 
					        cuf = new CollyUserFunctions(ca);
					    	String userName = username.getText().toString();
							String email = emailad.getText().toString();
							String firstName = fname.getText().toString();
							String lastName = lName.getText().toString();

			           cuf.registerUser(userName, email, firstName, lastName);
					   
				
			}
		});
		
	}
	
	public void completeTask(JSONObject jo)
	{
		try{
			
			if(jo.getString(KEY_SUCESS)!=null)
			{
				error.setText("");
				String res = jo.getString(KEY_SUCESS);
				if(Integer.parseInt(res)==1)
				{
					CollyData db = new CollyData(getApplicationContext());
                    JSONObject json_user = jo.getJSONObject("user");
                    
                    cuf.logoutUser(getApplicationContext());
                    db.addUser(json_user.getString(KEY_UID), json_user.getString(KEY_DISPLAY), json_user.getString(KEY_EMAIL));
                  /*  Intent dashboard = new Intent(getApplicationContext(), Dashboard.class);
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);*/
                    finish();
				}
				else
				{
					error.setText(jo.getString(KEY_ERROR_MSG));
				}
				
				
			}
			
			
		}
		catch (JSONException e) {
            e.printStackTrace();
        }
    
	}

}
