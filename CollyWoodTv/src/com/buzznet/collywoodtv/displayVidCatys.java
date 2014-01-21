package com.buzznet.collywoodtv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Video.VideoColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class displayVidCatys extends Activity implements DashVidAc{

	private String hasVideos;
	private String real;
	private String title;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
	
	Bundle ex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filterdisplay);
		ex = getIntent().getExtras();
		HashMap<String, String> mp = new HashMap<String, String>();
		   mp.put("tag", "videos");
		   mp.put("cat",ex.getString("tag"));
		 getVideoData vd = new getVideoData(mp,this);
		   vd.execute(serverUrl);
	}
	
	
	public void callback(String result)
	{
	}
	
	
	
	public void layoutVids(ArrayList<collyvideos> cv)
	{ @SuppressWarnings("deprecation")
		final Object data = getLastNonConfigurationInstance();
		VideosDash vd = new VideosDash(this,data,cv,this);
		
		//@SuppressWarnings("deprecation")
		//final Object data = getLastNonConfigurationInstance();
		//Fetchthumbs ff =new Fetchthumbs(this, data, cv);
		//viddImages.setAdapter(ff);
		//ImageView iv = (ImageView)findViewById(R.id.imageView1);
		
		
		
		
	}



	@Override
	public void Callback(String result) {
		// TODO Auto-generated method stub

		 JSONObject jsonObj = null;
	       String str = result;
	      // String []imgs={};
	       ArrayList<String> imgs = new ArrayList<String>();
	       ArrayList<String> title = new ArrayList<String>();
	       
	       ArrayList<collyvideos> cv = new ArrayList<collyvideos>();
	       String titles, excerpt,image,producer,country, id;
	       try{
			jsonObj = new  JSONObject(str);
			Iterator<?> keys = jsonObj.keys();
			JSONObject jsonvid = null;
			LinearLayout ll = null;
			String cat;
			while(keys.hasNext())
			{
				
				String key = (String)keys.next();
				if( jsonObj.get(key) instanceof JSONObject ){
					jsonvid = jsonObj.getJSONObject(key);
					String url = jsonvid.getString("thumb");
					cat = jsonvid.getString("cat");
					image =url;
					String vidurl = jsonvid.getString("yotube");
				    real =  jsonvid.getString("hasreal");
					
					//imgs.add(url);
					//title.add(jsonvid.getString("postTitle"));
					titles = jsonvid.getString("postTitle");
					excerpt = jsonvid.getString("excerpt");
					id = jsonvid.getString("postId");
					collyvideos acv = new collyvideos(image,vidurl,excerpt,titles,cat,id,real,"","");
					cv.add(acv);
					
				}
				
			}
			layoutVids(cv);
			
				
		}
		catch(Exception e)
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

	}



	@Override
	public void vidsSet(Imagedash[] Result) {
		// TODO Auto-generated method stub
		try{
			
			ImageView iv = null;
			//GridView gv = (GridView)findViewById(R.id.gridView1);
			for(final Imagedash i: Result)
			{
				String category = i.getCat();
				String movie = new String("Movies");
				String series = new String("Series");
				String feat = new String("Featured");
				String ppv = new String("ppv");
				 iv= new ImageView(this);
				
					LinearLayout tbl = (LinearLayout)findViewById(R.id.scl);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(230,290);
				params.setMargins(1, 1, 50, 1);
				iv.setLayoutParams(params);
				iv.setScaleType(ImageView.ScaleType.FIT_XY);
			     iv.setImageBitmap(i.thumbimg);
			     TextView tv = (TextView)findViewById(R.id.textViewcat);
			    tv.setText(ex.getString("tag"));
			     iv.setOnClickListener(new View.OnClickListener() {
				
				
					@Override
					public void onClick(View v) {
						String accesss = "";
						String hasVid = "";
						   if(ex != null)
						   {
						    accesss =  ex.getString("access");
						   
						   }
						 String vidid;
						   Intent vid = new Intent(getApplicationContext(), teaser.class);
			               vid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			               vid.putExtra("url", i.vidUrl);
			               vid.putExtra("Exc", i.desc);
			               vid.putExtra("bitmap", i.thumbimg);
			               vid.putExtra("id", i.pid);
			               vid.putExtra("access", accesss);
			               vid.putExtra("realvid", i.realv);
			               vid.putExtra("title", i.title);
			               startActivity(vid);
			              // finish();
						 
						 
						 /*commented out for testing youtube api 
						 String vid = i.vidUrl;
						int pos = vid.indexOf("?rel");
						if(pos!= -1)
							vidid = vid.substring(0, pos);
						else 
							vidid = vid;
						  String s = "http://www.youtube.com/watch?v="+vidid;
						  Uri uri = Uri.parse(s);
						  Intent intt = new Intent(Intent.ACTION_VIEW, uri);
						  // intt.setData(Uri.parse(i.vidUrl));
						   startActivity(intt);
						   
						   */
						   
						   
						  // Uri uri = Uri.parse(s);
						   
						  // videoView.setVideoURI(uri);
						   
						 //  videoView.requestFocus();
						   
						 //  videoView.start();
						
					}
				});
			
			tbl.addView(iv);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
