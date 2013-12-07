package com.buzznet.collywoodtv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;


public class DownloadTask extends AsyncTask<String, String, String>{
	private HashMap<String, String> pData = null;
	private Ifactivity activity;
	private JsonClass jc;
	public DownloadTask(HashMap<String, String> data, Ifactivity jc) {
        pData = data;
        this.activity = (Ifactivity)jc;
    }
	/*public DownloadTask(JsonClass ac)
	{  
		activity = ac;
	 	
	}*/
	
		    @Override
		    protected String doInBackground(String... params) {
		    	byte[] result = null;
		    	InputStream inStr = null;
		        String str = "";
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpPost httpPost = new HttpPost(params[0]);
		       
		        try {
		        	ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		        	Iterator<String> it = pData.keySet().iterator();
		        	while (it.hasNext()) {
		                String key = it.next();
		                nameValuePair.add(new BasicNameValuePair(key, pData.get(key)));
		            }
		        	 httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
		            HttpResponse execute = client.execute(httpPost);
		            StatusLine statusLine = execute.getStatusLine();
		            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
		            	result = EntityUtils.toByteArray(execute.getEntity());
		            	 str = new String(result, "UTF-8");
		               // inStr = new String(result, "UTF-8");
		            }
		          //InputStream content = execute.getEntity().getContent();

		        }
		        catch (ClientProtocolException e) {
		            e.printStackTrace();
		        }
		        catch (IOException e) {
		        	 e.printStackTrace();
					// TODO: handle exception
				}
		     
		     
		      return str;
		    }
		    
		   
		    

		    @Override
		    protected void onPostExecute(String result) {
		    	super.onPostExecute(result);
		      activity.callback(result);
		    }
			

}
