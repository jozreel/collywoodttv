package com.buzznet.collywoodtv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class datald extends Activity {
	private static Activity ma = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadingdata);
		ma = this;
		ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar1);
		pb.setVisibility(View.VISIBLE);
	}
	
	public static Activity getinstance()
	{
		return ma;
	}

}
