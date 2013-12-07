package com.buzznet.collywoodtv;

import java.io.File;
import java.net.URLEncoder;



import android.content.Context;

public class FileCache {
	
	private static File cacheDir;
	
	
	public FileCache(Context context)
	{
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"Collytv_cache");
		}
		else 
			cacheDir = context.getCacheDir();
		if(!cacheDir.exists())
			cacheDir.mkdirs();
	

		
	}
	
	public static File getCacheDir()
	{
		return cacheDir;
	}
	
	
	public File getFile(String url)
	{
		String filename="";
		File F =null;
		try
		{
		filename = URLEncoder.encode(url,"UTF-8");
		F = new File(cacheDir, filename);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return F;
		
	}
	
	public static void clear(){
		File[] mf = cacheDir.listFiles();
		if(mf == null)
			return;
		for(File f:mf)
			f.delete();
					
	}

}
