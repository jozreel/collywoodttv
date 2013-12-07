package com.buzznet.collywoodtv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;







import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

public class VideosDash {
	
	private DashVidAc mylist;
	private Loadthmbs lt;
	private Imagedash[] myImages;
	private FileCache filecache;
	//ExecutorService executorService; 
	MemoryCache memoryCache=new MemoryCache();
	public VideosDash(DashVidAc list,Object previousList, ArrayList<collyvideos> cv, Context contxt) {
		mylist = list;
		lt = new Loadthmbs();
		filecache = new FileCache(contxt);
		//executorService=Executors.newFixedThreadPool(5);
		myImages = new Imagedash[cv.size()];
		if(previousList != null) {
			myImages = (Imagedash[]) previousList;
	

			// continue processing remaining thumbs in the background
			lt.execute(myImages);
            
			// no more setup required in this constructor
		
			return;
		}
		
		for(int i = 0, j = cv.size(); i < j; i++) {
			myImages[i] = new Imagedash();
			myImages[i].urlimg =cv.get(i).getImgUrl();
			myImages[i].cat =cv.get(i).getCat();
		}
		lt.execute(myImages);
		// setStuff();
	}

	
	private Bitmap loadThumb(String url) {

		// the downloaded thumb (none for now!)
		Bitmap thumb = null;
        File f = filecache.getFile(url);
        thumb = this.decodeFiles(f);
        if(thumb!=null)
        {
        	return thumb;
        }
		// sub-sampling options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 3;

		try {

			// open a connection to the URL
			// Note: pay attention to permissions in the Manifest file!
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			// read data
			BufferedInputStream stream = new BufferedInputStream(c.getInputStream());
			OutputStream os = new FileOutputStream(f);
            CpfileUtil.CopyStream(is, os);
			// decode the data, subsampling along the way
			thumb = decodeFiles(f);
					//BitmapFactory.decodeStream(stream, null, opts);

			// close the stream
			stream.close();
			//os.close();

		} catch (MalformedURLException e) {
			Log.e("Threads03", "malformed url: " + url);
		} catch (IOException e) {
			Log.e("Threads03", "An error has occurred downloading the image: " + url);
		}

		// return the fetched thumb (or null, if error)
		return thumb;
	}
	
	
	
	
	private class Loadthmbs extends AsyncTask<Imagedash, Void, Imagedash[]> {

		/**
		 * Generate thumbs for each of the Image objects in the array
		 * passed to this method. This method is run in a background task.
		 * @return 
		 */
		protected Imagedash[] doInBackground(Imagedash... cache) {

			Imagedash myimgs = null;
			// define the options for our bitmap subsampling 
			//BitmapFactory.Options opts = new BitmapFactory.Options();
			//opts.inSampleSize = 3;

			// iterate over all images ...
			for (Imagedash i : cache) {

				// if our task has been cancelled then let's stop processing
				if(isCancelled()) return null;

				// skip a thumb if it's already been generated
				if(i.thumbimg != null) continue;

				// artificially cause latency!
				//SystemClock.sleep(500);
				
				// download and generate a thumb for this image
				Bitmap bitmap=memoryCache.get(i.urlimg);
				 if(bitmap!=null)
				 i.thumbimg = bitmap;
				 else
				 {
					 i.thumbimg = loadThumb(i.urlimg);
					 memoryCache.put(i.urlimg, i.thumbimg);
				  }
				
                
				
			}
			return myImages;
			
			
		}
		
		/*boolean imageViewReused(Imagedash photoToLoad){
			   String tag=imageViews.get(photoToLoad.imageView);
			        if(tag==null || !tag.equals(photoToLoad.url))
			        return true;
			        return false;*/
		
		
		@Override
		protected void onPostExecute(Imagedash[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mylist.vidsSet(result);
		}
	
		
		//@Override
	/*	protected void onPostExecute(Imagedash result) {
			
			super.onPostExecute(result);
			mylist.vidsSet(result);
		}*/
		


		
		
	}
	
	private void setStuff()
	{
		
		mylist.vidsSet(myImages);
	}
	
	private Bitmap decodeFiles(File F)
	{
		Bitmap bmp = null;
		try
		{
		BitmapFactory.Options o = new BitmapFactory.Options();
	//	o.inJustDecodeBounds = true;
		o.inSampleSize = 3;
	     bmp= BitmapFactory.decodeStream(new FileInputStream(F),null,o);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return bmp;
	}
}
