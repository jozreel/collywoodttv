package com.buzznet.collywoodtv;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class Fetchthumbs extends BaseAdapter{

	private class Image {
		String url;
		Bitmap thumb;
	}
	private Image[] images;
	private Context myContext;
	private LoadThumbsTask thumbnailGen;
	private ArrayList<String> imageURLs;
	private ArrayList<String> titles;
	private ArrayList<collyvideos> cvs;
	private String ttls;
	public Fetchthumbs(Context c, Object previousList, ArrayList<collyvideos> cv) {

		myContext = c;
        //imageURLs = cv.
       // titles =title;
        cvs = cv;
		// get our thumbnail generation task ready to execute
		thumbnailGen = new LoadThumbsTask();
		
		// we'll want to use pre-existing data, if it exists
		if(previousList != null) {
			images = (Image[]) previousList;
	

			// continue processing remaining thumbs in the background
			thumbnailGen.execute(images);

			// no more setup required in this constructor
		
			return;
		}
		
		// if no pre-existing data, we need to generate it from scratch.

		// initialize array
		images = new Image[cv.size()];

		for(int i = 0, j = cv.size(); i < j; i++) {
			images[i] = new Image();
			images[i].url =cv.get(i).getImgUrl();
		}
		
		// start the background task to generate thumbs
		thumbnailGen.execute(images);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position].url;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
       ImageView imgView;
       if(convertView == null) {
    	   convertView = LayoutInflater.from(myContext).inflate(R.layout.dispvew, null,true);
    	  
			// no view to recycle; create a new view
		//	imgView = new ImageView(myContext);
			// imgView=(ImageView)convertView.findViewById(R.id.imageView1);
			//imgView.setScaleType(ImageView.ScaleType.MATRIX);
			//imgView.setLayoutParams(new GridView.LayoutParams(150,150));
			
			

		}// else {
	
			// recycle an old view (it might have old thumbs in it!)
			//imgView = (ImageView) convertView;
	
		//}
	
       LinearLayout ll=(LinearLayout) convertView.findViewById(R.id.topbarParent);
      imgView=(ImageView)convertView.findViewById(R.id.imageView1);
      LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(170,240);
      LinearLayout.LayoutParams parms1 = new LinearLayout.LayoutParams(90,120);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        if((convertView.getResources().getConfiguration().screenLayout &   Configuration.SCREENLAYOUT_SIZE_MASK) ==   Configuration.SCREENLAYOUT_SIZE_SMALL)
        {
        
        imgView.setLayoutParams(parms1);
        }
        else
         imgView.setLayoutParams(parms);
       //TextView tv= (TextView) ll.findViewById(R.id.textView1);
       //TextView tvExcerpt= (TextView)convertView.findViewById(R.id.textView2);
		//LinearLayout ll = new LinearLayout(myContext);
		//tv.setTypeface(Typeface.DEFAULT);
		ll.setOrientation(LinearLayout.VERTICAL);
		// pull the cached data for the image assigned to this position
		Image cached = images[position];
      //  String ttls = cvs.get(position).getTitle();
       // String exc = cvs.get(position).getExcerpt();
		// can we recycle an old view?
			

		// do we have a thumb stored in cache?
		if(cached.thumb == null) {
			
			// no cached thumb, so let's set the view as blank
			imgView.setImageResource(R.drawable.ic_action_photo);		
			imgView.setScaleType(ScaleType.CENTER);
			//tv.setText(ttls);
			//tvExcerpt.setText(Html.fromHtml(exc));

		} else {

			// yes, cached thumb! use that image
			imgView.setScaleType(ScaleType.FIT_XY);
			imgView.setImageBitmap(cached.thumb);
			//tv.setText(ttls);
			//tvExcerpt.setText(Html.fromHtml(exc));
			
		}

		return convertView;
	}
	
	public Object getData() {
		// stop the task if it isn't finished
		if(thumbnailGen != null && thumbnailGen.getStatus() != AsyncTask.Status.FINISHED) {
			// cancel the task
			thumbnailGen.cancel(true);

		}

		// return generated thumbs
		return images;
	}
	
	private void cacheUpdated() {
		this.notifyDataSetChanged();
	}
	
	private Bitmap loadThumb(String url) {

		// the downloaded thumb (none for now!)
		Bitmap thumb = null;

		// sub-sampling options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		//opts.inSampleSize = 4;

		try {

			// open a connection to the URL
			// Note: pay attention to permissions in the Manifest file!
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			
			// read data
			BufferedInputStream stream = new BufferedInputStream(c.getInputStream());

			// decode the data, subsampling along the way
			thumb = BitmapFactory.decodeStream(stream, null, opts);

			// close the stream
			stream.close();

		} catch (MalformedURLException e) {
			Log.e("Threads03", "malformed url: " + url);
		} catch (IOException e) {
			Log.e("Threads03", "An error has occurred downloading the image: " + url);
		}

		// return the fetched thumb (or null, if error)
		return thumb;
	}
	
	
	private class LoadThumbsTask extends AsyncTask<Image, Void, Void> {

		/**
		 * Generate thumbs for each of the Image objects in the array
		 * passed to this method. This method is run in a background task.
		 */
		@Override
		protected Void doInBackground(Image... cache) {

			
			// define the options for our bitmap subsampling 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 2;

			// iterate over all images ...
			for (Image i : cache) {

				// if our task has been cancelled then let's stop processing
				if(isCancelled()) return null;

				// skip a thumb if it's already been generated
				if(i.thumb != null) continue;

				// artificially cause latency!
				SystemClock.sleep(500);
				
				// download and generate a thumb for this image
				i.thumb = loadThumb(i.url);

				// some unit of work has been completed, update the UI
				publishProgress();
			}
			
			return null;
		}


		/**
		 * Update the UI thread when requested by publishProgress()
		 */
		@Override
		protected void onProgressUpdate(Void... param) {
			cacheUpdated();
		}
		
		
	}
	

}
