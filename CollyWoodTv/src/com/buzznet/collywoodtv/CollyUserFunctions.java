package com.buzznet.collywoodtv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class CollyUserFunctions  implements Ifactivity{
	
	private JsonClass jsParser;
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
    private static String loginTag = "login";
    private  static String regTag ="register";
    static InputStream inStr = null;
	static JSONObject jsonObj = null;
	private String json = "";
	private CollyHome myview;
	private register mr;
    
    public CollyUserFunctions(CollyHome ac)
    {
    	myview =ac;
    	//jsParser = new JsonClass();
    }
    
    public CollyUserFunctions(register rc)
    {
    	mr =rc;
    	//jsParser = new JsonClass();
    }
    public void loginUser(String uname, String passwd)
    {
    	
    	//List<NameValuePair> params = new ArrayList<NameValuePair>();
    	HashMap<String, String> params = new HashMap<String, String>();
    	params.put("tag", loginTag);
    	params.put("uname", uname);
    	params.put("passwd", passwd);
        getjsonFromUrl(serverUrl,params);
    	//return jo;
    }
    
    public boolean isUserLoggedIn(Context context){
       CollyData db = new CollyData(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
    
    public boolean logoutUser(Context context){
        CollyData db = new CollyData(context);
        db.resetTables();
        return true;
    }
    
    
    public void registerUser(String uname, String email, String fname, String lname)
    {
    	//List<NameValuePair> params = new ArrayList<NameValuePair>();
    	HashMap<String, String> params = new HashMap<String, String>();
    	
    	params.put("tag", regTag);
    	params.put("uname", uname);
    	params.put("email", email);
    	params.put("fname", fname);
    	params.put("lname", lname);
    	getjsonFromUrl(serverUrl,params);
    }
    
    public void getjsonFromUrl(String url, HashMap<String, String> params)
	{
    	  DownloadTask dt =   new DownloadTask(params,this);
		  dt.execute(url);
	}

	@Override
	public void callback(String result) {
		json = result;
		//}
				try{
					jsonObj = new  JSONObject(json);
					if(myview!=null && myview instanceof CollyHome)
					     myview.tskCompleted(jsonObj);
					if(mr!=null && mr instanceof register)
						mr.completeTask(jsonObj);
				}
				catch(Exception e)
				{
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}
		
	}
    
}
