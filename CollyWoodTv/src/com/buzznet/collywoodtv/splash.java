package com.buzznet.collywoodtv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity {
	public static Activity instatce;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
	Bundle bd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		instatce = this;
	
	}
   
	public static Activity getinstatce()
	{
		return instatce;
	}
}
