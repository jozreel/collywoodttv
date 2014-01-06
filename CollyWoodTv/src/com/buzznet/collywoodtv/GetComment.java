package com.buzznet.collywoodtv;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class GetComment extends AsyncTask<String, String, String>  {
	private HashMap<String, String> pData = null;
	private teaser listener;
	public GetComment(HashMap<String, String> data, teaser lst)
	{
		pData = data;
		this.listener = lst;
		
	}
    
	@Override
	protected String doInBackground(String... params) {
		byte[] result = null;
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
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		listener.Callback(result);
	}   

}
