package com.buzznet.collywoodtv;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class DownloadImages extends AsyncTask<String, String, Drawable> {

	@Override
	protected Drawable doInBackground(String... params) {
		   String url = params[0];
		   Drawable d =null;
		   try{
		   InputStream is = (InputStream) new URL(url).getContent();
	        d = Drawable.createFromStream(is, "src name");
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	        return d;
		
	}

}
