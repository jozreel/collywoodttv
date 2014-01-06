package com.buzznet.collywoodtv;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class teaser extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener{
	Bundle bd;
	String vid;
	String vidid;
	String VIDEO;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
	static private final String DEVELOPER_KEY = "AIzaSyBxYf7Pu3v0KumqZfUyeZMD_c8JhUsmJiE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.teaser);
		     bd = getIntent().getExtras();
		     vid = bd.getString("url");
		     TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
	        tabs.setup();
	    	
		    TabHost.TabSpec descTab = tabs.newTabSpec("Description");
		   
		    descTab.setIndicator("Description");
		    descTab.setContent(R.id.Description);
		    tabs.addTab(descTab);

		    // trailer
		    TabHost.TabSpec traiilerTab = tabs.newTabSpec("Trailer");
		    traiilerTab.setIndicator("Trailer");
		    traiilerTab.setContent(R.id.Trailer);
		    tabs.addTab(traiilerTab);

		   // commentys
		    TabHost.TabSpec ComTab = tabs.newTabSpec("Comments");
		    ComTab.setIndicator("Comments");
		    ComTab.setContent(R.id.Comments);
		    tabs.addTab(ComTab);
		TextView tv = (TextView)findViewById(R.id.textView1);
	//	bd = getIntent().getExtras();
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
		String st = bd.getString("Exc");
		String rst = st.replaceAll("\\\n", "\\<br \\/\\>");
		String rst1 = rst.replaceAll("\\[.*\\]", "");
		tv.setText(Html.fromHtml(rst1));
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,400);
		params.setMargins(10, 1, 50, 1);
		
		
		ImageView iv =  (ImageView)findViewById(R.id.imageView1);
		//iv.getLayoutParams().width = 500;
		//iv.getLayoutParams().height = 300;
		
		iv.setLayoutParams(params);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
	    iv.setImageBitmap(bitmap);
	    
	    int pos = vid.indexOf("?rel");
		if(pos!= -1)
			vidid = vid.substring(0, pos);
		else 
			vidid = vid;
		
		VIDEO = vidid;
        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubeView.initialize(DEVELOPER_KEY, this);
        
        getComments();
      
	}
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {
		player.loadVideo(VIDEO);
		// TODO Auto-generated method stub
		
	}
	
	public void getComments()
	{
		  HashMap<String, String> mp = new HashMap<String, String>();
		   mp.put("tag", "comment");
		   //mp.put("id", "ALL");
		   GetComment cm = new GetComment(mp,this);
		   cm.execute(serverUrl);
	}
	
	public void Callback(String Result)
	{
		 JSONObject jsonObj = null;
	       String str = Result;
	       
	       try{
	   		jsonObj = new  JSONObject(str);
	   		Iterator<?> keys = jsonObj.keys();
	   		JSONObject jsonvid = null;
	   		//LinearLayout ll = null;
	   		String author;
	   		int count   = Integer.parseInt(jsonObj.getString("count"));
	   		//int count  =  Integer.parseInt(jsonvid.getString("comment_author"));
	   		TextView comm[] = null;
	   		TextView auth[] = null;
				if(count !=0)
				{
					comm=  new TextView[count];
					auth=  new TextView[count];
				}
				
			LinearLayout myLayout = (LinearLayout) findViewById(R.id.Comments);
			LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
			//lp.setMargins(10, 1, 50, 1);
			
			LayoutParams lp1 = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
			lp1.setMargins(50, 1, 50, 50);
			int i=0;
	   		while(keys.hasNext())
	   		{
	   			String key = (String)keys.next();
	   			if( jsonObj.get(key) instanceof JSONObject ){
	   				jsonvid = jsonObj.getJSONObject(key);
	   				String content = jsonvid.getString("comment_content");
	   				author = jsonvid.getString("comment_author");
	   				
	   				
	   			 auth[i] = new TextView(this);
	             auth[i].setTextSize(15);
	             auth[i].setLayoutParams(lp1);
	             auth[i].setId(i);
	             auth[i].setPadding(20, 8, 8, 10);
	             auth[i].setText(author);
	             auth[i].setTextColor(Color.parseColor("#0099CC"));
	   				
	   			 comm[i] = new TextView(this);
	             comm[i].setTextSize(15);
	             comm[i].setLayoutParams(lp);
	             comm[i].setId(i);
	             comm[i].setText(content);
	             comm[i].setPadding(20, 8, 8, 50);
	             myLayout.addView(auth[i]);
	             myLayout.addView(comm[i]);
	   				
	   				
	   				//image =url;
	   				//String vidurl = jsonvid.getString("yotube");
	   				//imgs.add(url);
	   				//title.add(jsonvid.getString("postTitle"));
	   				//titles = jsonvid.getString("postTitle");
	   				//excerpt = jsonvid.getString("excerpt");
	   				//collyvideos acv = new collyvideos(image,vidurl,excerpt,titles,cat,"","");
	   				//cv.add(acv);
	   				i++;
	   				
	   			}
	   			
	   		}
	    }
	    catch(Exception e)
	   	{
	   		Log.e("JSON Parser", "Error parsing data " + e.toString());
	   	}
	}
	 

}
