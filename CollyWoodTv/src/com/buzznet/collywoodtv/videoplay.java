package com.buzznet.collywoodtv;

//import com.stream.R;

//import com.stream.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

public class videoplay extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {
	static private final String DEVELOPER_KEY = "AIzaSyBxYf7Pu3v0KumqZfUyeZMD_c8JhUsmJiE";
	Bundle ex;
	String vid;
	String vidid;
	String VIDEO;
	 YouTubePlayerView youTubeView1;
	JSONObject jsonvid = null;
	private static YouTubePlayer player = null;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		ex = getIntent().getExtras();
		 

	       HashMap<String, String> mp = new HashMap<String, String>();
		   mp.put("tag", "realVid");
		   mp.put("title", ex.getString("title"));
		   GetRealVidData gvd = new GetRealVidData(mp,this);
		   gvd.execute(serverUrl);
		   youTubeView1 = (YouTubePlayerView)findViewById(R.id.youtube_view1);
		  
		/*
		 if(ex!=null)
		    	vid = ex.getString("url");
		//TextView tv = (TextView)findViewById(R.id.textView1);
		//VideoView videoView = (VideoView)findViewById(R.id.videoV);
		int pos = vid.indexOf("?rel");
		if(pos!= -1)
			vidid = vid.substring(0, pos);
		else 
			vidid = vid;
		
		VIDEO = vidid;
        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        */
     //   youTubeView.initialize(DEVELOPER_KEY, this);

    }
		
	/*	MediaController mc = new MediaController(this);
		
		 videoView.setMediaController(mc);
		 String s = "http://www.youtube.com/watch?v="+vidid;
		 //  String s = "http://www.youtube.com/watch?v=ygnEPRGX090?rel=0";
		// tv.setText(s);
		Intent intt = new Intent(Intent.ACTION_VIEW);
		 intt.setData(Uri.parse(s));
		 startActivity(intt);
		 Uri uri = Uri.parse("s");
		   
		   videoView.setVideoURI(uri);
		  
		 //  
		  videoView.requestFocus();
		   
		  videoView.start();
		  */
		
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		Log.e("Youtube error", "Error parsing data " + arg1.toString());
		
	}
	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {
		player.loadVideo(VIDEO);
		videoplay.player = player;
		// TODO Auto-generated method stub
		
	}
	public void Callback(String result)
	{
		 JSONObject jsonObj = null;
	       String str = result;
	     
	       try{
	    	 
	   		jsonObj = new  JSONObject(str);
	   	     
	   	
	   	   
	   		Iterator<?> keys = jsonObj.keys();
	   		
	   	
	   		int count = Integer.parseInt(jsonObj.getString("count"));
	   		if(count == 1)
	   		{
	   		jsonvid = jsonObj.getJSONObject("videos");
	   		
	   		  String vid =  jsonvid.getString("video");
		   	    int pos = vid.lastIndexOf("/");
				if(pos!= -1)
					vidid = vid.substring(pos+1);
				else 
					vidid = vid;
				
				VIDEO = vidid;
				
			     youTubeView1.initialize(DEVELOPER_KEY, this);
	   		}
	   		else if(count>1)
	   		{
	   		
	   			JSONArray ja =  jsonObj.getJSONArray("videos");
	   		
	   			ImageView iv[] = new ImageView[count+1];
	   			TextView tv[] = new TextView[count+1];
	   			LinearLayout ll[] = new LinearLayout[count+1];
	   			LinearLayout myLayout = (LinearLayout) findViewById(R.id.series);
				LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
				LayoutParams lp1 = new LayoutParams( LayoutParams.MATCH_PARENT,    LayoutParams.WRAP_CONTENT);
				lp1.setMargins(100, 1, 50, 1);
				lp.setMargins(100, 1, 50, 1);
				
				int i=0;
		   		while(i < ja.length())
		   		{  // JSONObject jo = jsonObj.getJSONObject("videos");
		   			//String key = (String)keys.next();
		   			if( ja.get(i) instanceof JSONObject ){
		   				jsonvid = ja.getJSONObject(i);
		   				
		   			
		   				 
		   				//Collections.sort(jsonValues());
		   				
		   				
		   				
		   				String vid =  jsonvid.getString("title");
		   				ll[i] = new LinearLayout(this);
		   			 ll[i].setLayoutParams(lp1);
		   			 ll[i].setPadding(10, 1, 10, 10);
		   			 ll[i].setOrientation(LinearLayout.HORIZONTAL);
		   			 tv[i] = new TextView(this);
		             tv[i].setTextSize(15);
		             tv[i].setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,    LayoutParams.WRAP_CONTENT));
		             tv[i].setId(i);
		             tv[i].setPadding(20, 8, 8, 10);
		             tv[i].setText(vid);
		             tv[i].setTextColor(Color.BLACK);
		             
		             iv[i] =new ImageView(this);
		             
		             iv[i].setLayoutParams(new LayoutParams(50, 50));
		     		 iv[i].setScaleType(ImageView.ScaleType.FIT_XY);
		     		// iv[i].getLayoutParams().width = 200;
		     		// iv[i].getLayoutParams().height = 200;
		     		 Intent intent = getIntent();
		    		 Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
		     	     iv[i].setImageBitmap(bitmap);
		     	     ll[i].setTag(jsonvid.getString("video"));
		     	     ll[i].addView(iv[i]);
		     	     ll[i].addView(tv[i]);
		     	     ll[i].setOnClickListener(new View.OnClickListener() {
		     	    	
						@Override
						public void onClick(View v) {
							String vid1 = (String) v.getTag();
			     	   	    int pos = vid1.lastIndexOf("/");
			     			if(pos!= -1)
			     				vidid = vid1.substring(pos+1);
			     			else 
			     				vidid = vid1;
			     			
			     			VIDEO = vidid;
						
							playVid();
							
						}
					});
		     	     if(i==0)
		     	     {
		     	    	 ll[i].setBackgroundColor(Color.DKGRAY);
		     	    /*	String vid1 =  jsonvid.getString("video");
				   	    int pos = vid.lastIndexOf("/");
						if(pos!= -1)
							vidid = vid1.substring(pos+1);
						else 
							vidid = vid1;
						
						VIDEO = vidid;
						
						
					     youTubeView1.initialize(DEVELOPER_KEY, this);*/
		     	    	 
		     	     }
		     	     if(i==1)
		     	    	ll[i].setBackgroundColor(Color.LTGRAY);
		     	     
		     	     if(i >=2 && i%2==0)
		     	    	 ll[i].setBackgroundColor(Color.DKGRAY);
		     	     else if(i >=2 && i%2 !=0)
		     	     {
		     	    	ll[i].setBackgroundColor(Color.LTGRAY);
		     	     }
		     	    	 
		     	     myLayout.addView(ll[i]);
		   			}
		   			i++;
		   		}
	   		}
	   	
	       }
	       catch(Exception e)
		   	{
		   		Log.e("JSON Parser", "Error parsing data " + e.toString());
		   	}
		
	}
	
	public void playVid()
	{
		try{
		
		
		
		// if(videoplay.player !=null)
			// videoplay.player.loadVideo(VIDEO);
		
			
		
	       youTubeView1.initialize(DEVELOPER_KEY, this);
	     
	       if(videoplay.player.isPlaying())
	             videoplay.player.loadVideo(VIDEO);
		}
		catch(Exception e)
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
	}
	
	
}
