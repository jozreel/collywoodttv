package com.buzznet.collywoodtv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class JsonClass implements Ifactivity {
  
	static InputStream inStr = null;
	static JSONObject jsonObj = null;
	private String json = "";
	
	
	//constructor
	
	public JsonClass()
	{
		
	}
	
	public void getjsonFromUrl(String url, HashMap<String, String> params)
	{
	/*	try{
		json = new DownloadTask(params,this).execute(url).get();
		}
		catch(ExecutionException e)
		{
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
		  e.printStackTrace();
		}
		
		*/
		  DownloadTask dt =   new DownloadTask(params,this);
		  dt.execute(url);
		
		//try
		//{
			//DefaultHttpClient httpClient = new DefaultHttpClient();
			//HttpPost httpPost = new HttpPost(url);
		   // httpPost.setEntity(new UrlEncodedFormEntity(params));
		   // HttpResponse httpResp = httpClient.execute(httpPost);
		   // HttpEntity httpEntity = httpResp.getEntity();
		   // inStr = httpEntity.getContent();
		//}
		/*catch(UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
			
		}
		catch(ClientProtocolException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
		}*/
		//try{
		//	BufferedReader br = new BufferedReader(new InputStreamReader(inStr, "so-8859-1"),8);
			//StringBuilder sb = new StringBuilder();
			//String ln = null;
			//while((ln = br.readLine())!=null){
				//sb.append(ln+"n");
				
			//}
			//inStr.close();
			//json = sb.toString();
			//Log.e("JSON", json);
			
		//}
		//catch(Exception e){
			
		  //  Log.e("Buffer Error", "Error converting result " + e.toString());
		//}
		/*try{
			jsonObj = new  JSONObject(json);
		}
		catch(Exception e)
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jsonObj;*/
	}

	

	@Override
	public void callback(String result) {
		json = result;
		//}
				try{
					jsonObj = new  JSONObject(json);
				}
				catch(Exception e)
				{
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}
				//return jsonObj;
		
	}

}
