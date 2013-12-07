package com.buzznet.collywoodtv;

//import android.app.Activity;
//import android.app.ActionBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;
import android.app.ActionBar.LayoutParams;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Dashboard extends ActionBarActivity implements DashVidAc{
	SearchView searchView;
	MenuItem menuItem;
	//GridLayout gl;
	Bundle ex;
	GridView viddImages;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	boolean reload =true;
	setContentView(R.layout.dashboard);
	
	
	getWindow().setFormat(PixelFormat.RGBA_8888);
	//ActionBar ab= getSupportActionBar();
	ex = getIntent().getExtras();
//	viddImages = (GridView) findViewById(R.id.gridView1);
	    getVideoData(serverUrl);
}

@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activitymenu, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView)searchItem.getActionView();
    
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    
    menuItem = menu.findItem(R.id.menu_Filter);
    menuItem.setTitle("ALL");
    MenuItem usr = menu.findItem(R.id.action_profile);
    usr.setIcon(R.drawable.ic_action_user);
    String DisplayName ="";
    if(ex!=null)
    	DisplayName = ex.getString("user");
    usr.setTitle(DisplayName);
    return super.onCreateOptionsMenu(menu);

	}
 
    

   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	   switch(item.getItemId())
	   {
	   case R.id.action_search:
		   searchView.setIconifiedByDefault(false);
		   break;
	   case R.id.menuMovies:
		   menuItem.setTitle("Movies");
		   break;
	   case R.id.menuSeries:
		   menuItem.setTitle("Series");
		   break;
	   case R.id.menuAll:
		   menuItem.setTitle("ALL");
		   
		   
		 
		 //  openSearch();
	   }
		return super.onOptionsItemSelected(item);
	}
   
   public void getVideoData(String url)
   {
	   
	   HashMap<String, String> mp = new HashMap<String, String>();
	   mp.put("tag", "videos");
	   mp.put("cat", "ALL");
	   getVideoData vd = new getVideoData(mp,this);
	   vd.execute(url);
	   
   }

@Override
public void Callback(String Result) {
       JSONObject jsonObj = null;
       String str = Result;
      // String []imgs={};
       ArrayList<String> imgs = new ArrayList<String>();
       ArrayList<String> title = new ArrayList<String>();
       
       ArrayList<collyvideos> cv = new ArrayList<collyvideos>();
       String titles, excerpt,image,producer,country;
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
				//imgs.add(url);
				//title.add(jsonvid.getString("postTitle"));
				titles = jsonvid.getString("postTitle");
				excerpt = jsonvid.getString("excerpt");
				collyvideos acv = new collyvideos(image,excerpt,titles,cat,"","");
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
public void vidsSet(Imagedash[] Result) {
	LinearLayout tbl = null;// (LinearLayout)findViewById(R.id.hscrol);
	try{
		
		ImageView iv = null;
		//GridView gv = (GridView)findViewById(R.id.gridView1);
		for(Imagedash i: Result)
		{
			String category = i.getCat();
			String movie = new String("Movies");
			String series = new String("Series");
			String feat = new String("Featured");
			String ppv = new String("ppv");
			 iv= new ImageView(this);
			if(category.equals(movie)|| category.equals(feat))
				tbl = (LinearLayout)findViewById(R.id.hscrol);
			else if(category.equals(series))
				tbl = (LinearLayout)findViewById(R.id.hscrol2);
		
				else if(category.equals(ppv))
					tbl = (LinearLayout)findViewById(R.id.hscrol3);
				else
				continue;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(130,190);
			params.setMargins(10, 1, 50, 1);
			iv.setLayoutParams(params);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
		     iv.setImageBitmap(i.thumbimg);
		
		tbl.addView(iv);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
}


}
