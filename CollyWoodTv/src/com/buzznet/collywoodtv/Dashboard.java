package com.buzznet.collywoodtv;

//import android.app.Activity;
//import android.app.ActionBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
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
	private String hasVideos;
	private String real;
	private String title;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	boolean reload =true;
	Intent intnt =  new Intent(getApplicationContext(), datald.class);
	startActivity(intnt);
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
		   searchBy("movies");
		   break;
	   case R.id.menuSeries:
		   menuItem.setTitle("Series");
		   searchBy("series");
		   break;
	/*   case R.id.menuAll:
		   menuItem.setTitle("ALL");
		   searchBy("ALL");
		   break;
		   */
		   
		 
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
		int dim = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
		int dimh = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190, getResources().getDisplayMetrics());
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
			if(category.equals(movie)|| category.equals(feat))
				tbl = (LinearLayout)findViewById(R.id.hscrol);
			else if(category.equals(series))
				tbl = (LinearLayout)findViewById(R.id.hscrol2);
		
				else if(category.equals(ppv))
					tbl = (LinearLayout)findViewById(R.id.hscrol3);
				else
				continue;
			
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dim,dimh);
			params.setMargins(10, 1, 50, 1);
			iv.setLayoutParams(params);
			//iv.setScaleType(ImageView.ScaleType.FIT_XY);
		     iv.setImageBitmap(i.thumbimg);
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
		               vid.putExtra("cat", i.cat);
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
		Activity dff =  datald.getinstance();
		dff.finish();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
}

public void searchBy(String tag)
{
	Intent inttt =  new Intent(getApplicationContext(),  displayVidCatys.class);
	inttt.putExtra("access", ex.getString("access"));
	inttt.putExtra("tag", tag);
	startActivity(inttt);
	
	  
	
}

}
