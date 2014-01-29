package com.buzznet.collywoodtv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

public class teaser extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener{
	Bundle bd;
	String vid;
	String vidid;
	String VIDEO;
	Bitmap bitmap;
private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;
    
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";
    // when testing in sandbox, this is likely the -facilitator email address. 
    private static final String CONFIG_RECEIVER_EMAIL = "junior.hart@yahoo.co.uk"; 
	private static String serverUrl = "http://www.collywoodcinemas.com/androidapp.php";
	static private final String DEVELOPER_KEY = "AIzaSyBxYf7Pu3v0KumqZfUyeZMD_c8JhUsmJiE";
	Button myb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.teaser);
		     bd = getIntent().getExtras();
		     vid = bd.getString("url");
		     
		     
		     
		     
		     
		     
		     TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
	        tabs.setup();
	    	
		    TabHost.TabSpec descTab = tabs.newTabSpec("Description");
		   
		    descTab.setIndicator("Description");
		    descTab.setContent(R.id.Description);
		    tabs.addTab(descTab);

		    // trailer
		    TabHost.TabSpec traiilerTab = tabs.newTabSpec("Trailer");
		    traiilerTab.setIndicator("Trailer");
		    traiilerTab.setContent(R.id.Trailer);
		    tabs.addTab(traiilerTab);

		   // commentys
		    TabHost.TabSpec ComTab = tabs.newTabSpec("Comments");
		    ComTab.setIndicator("Comments");
		    ComTab.setContent(R.id.Comments);
		    tabs.addTab(ComTab);
		TextView tv = (TextView)findViewById(R.id.textView1);
	//	bd = getIntent().getExtras();
		Intent intent = getIntent();
		bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
		
		String st = bd.getString("Exc");
		String rst = st.replaceAll("\\\n", "\\<br \\/\\>");
		String rst1 = rst.replaceAll("\\[.*\\]", "");
		tv.setText(Html.fromHtml(rst1));
		int dim = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 340, getResources().getDisplayMetrics());
		int dimh = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 430, getResources().getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dim,dimh);
		params.setMargins(10, 1, 50, 1);
		
		
		ImageView iv =  (ImageView)findViewById(R.id.imageView1);
		//iv.getLayoutParams().width = 500;
		//iv.getLayoutParams().height = 300;
		
		iv.setLayoutParams(params);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
	    iv.setImageBitmap(bitmap);
	    
	    int pos = vid.indexOf("?rel");
		if(pos!= -1)
			vidid = vid.substring(0, pos);
		else 
			vidid = vid;
		
		VIDEO = vidid;
        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubeView.initialize(DEVELOPER_KEY, this);
        
        getComments();
        getRattings();
        //String stf =bd.getString("realvid");
        String access = bd.getString("access");
        if(Integer.parseInt(bd.getString("realvid"))   > 0)
        {
           
            myb = new Button(this);
            myb.setText("Watch NOW");
            LinearLayout ll = (LinearLayout)findViewById(R.id.Description);
            LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
            myb.setLayoutParams(lp);
            if(!bd.getString("cat").equals(new String("ppv")))
                ll.addView(myb);
            
        //ppv stuff
            
            
            if(bd.getString("cat").equals(new String("ppv")) && Integer.parseInt(bd.getString("realvid")) ==1)
            {
            
            	PayPal ppObjt = initService();
            	
            	CheckoutButton launchPayPalButton = ppObjt.getCheckoutButton(this,PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
            	 ((LinearLayout)findViewById(R.id.Description)).addView(launchPayPalButton);
            	 
            	 
            	 Intent i = new Intent(this, PayPalService.class);
                 
                 i.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
                 i.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
                 i.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
                 
                 startService(i);
                 final Intent intent1 = new Intent(this, PaymentActivity.class);
                 
                 launchPayPalButton.setOnClickListener(new View.OnClickListener(
                		 ) {
					
					@Override
					public void onClick(View v) {
						 PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("5.00"), "USD", bd.getString("title"));
					        
					       
					        
					        intent1.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
					        intent1.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
					        intent1.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
					        
					        // It's important to repeat the clientId here so that the SDK has it if Android restarts your 
					        // app midway through the payment UI flow.
					        intent1.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "credential-from-developer.paypal.com");
					        intent1.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
					        intent1.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
					        
					        startActivityForResult(intent1, 0);
						
						
						// TODO Auto-generated method stub
						
					}
				});
            	 
            	
            }
            
            
        
        //END ppv stuff
            
      if(!bd.getString("cat").equals(new String("ppv")))  
      {
        if(access.equals(new String("s2member_level1")) || access.equals(new String("s2member_level2"))|| access.equals(new String("s2member_level4")) || access.equals(new String("Administrator")) ||access.equals(new String("s2member_level3")))
        {
          myb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  Intent vid = new Intent(getApplicationContext(), videoplay.class);
	               vid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	               String ttl = bd.getString("title");
	               vid.putExtra("title",ttl);
	               vid.putExtra("bitmap",  bitmap);
	               startActivity(vid);
			}
		});
        }
        else
        {
        	 myb.setOnClickListener(new View.OnClickListener() {
     			
     			@Override
     			public void onClick(View v) {
     				// TODO Auto-generated method stub
     				  Intent pay = new Intent(getApplicationContext(), Pay.class);
     	               pay.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     	               String ttl = bd.getString("title");
     	               pay.putExtra("title",ttl);
     	               startActivity(pay);
     			}
     		});
        }
        }
       }
        
      
	}
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {
		player.loadVideo(VIDEO);
		// TODO Auto-generated method stub
		
	}
	
	public void getComments()
	{
		   String idd = bd.getString("id");
		  HashMap<String, String> mp = new HashMap<String, String>();
		   mp.put("tag", "comment");
		   mp.put("id",idd);
		   GetComment cm = new GetComment(mp,this);
		   cm.execute(serverUrl);
	}
	
	public void getRattings()
	{
		   String idd = bd.getString("id");
		  HashMap<String, String> mp = new HashMap<String, String>();
		   mp.put("tag", "ratings");
		   mp.put("id",idd);
		   getRatting gr = new getRatting(mp,this);
		   gr.execute(serverUrl);
	}
	
	
	
	
	public void Callback(String Result)
	{
		 JSONObject jsonObj = null;
	       String str = Result;
	       
	       try{
	   		jsonObj = new  JSONObject(str);
	   		Iterator<?> keys = jsonObj.keys();
	   		JSONObject jsonvid = null;
	   		//LinearLayout ll = null;
	   		String author;
	   		int count   = Integer.parseInt(jsonObj.getString("count"));
	   		//int count  =  Integer.parseInt(jsonvid.getString("comment_author"));
	   		TextView comm[] = null;
	   		TextView auth[] = null;
				if(count !=0)
				{
					comm=  new TextView[count];
					auth=  new TextView[count];
				}
				
			LinearLayout myLayout = (LinearLayout) findViewById(R.id.Comments);
			LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
			//lp.setMargins(10, 1, 50, 1);
			
			LayoutParams lp1 = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
			lp1.setMargins(50, 1, 50, 50);
			int i=0;
	   		while(keys.hasNext())
	   		{
	   			String key = (String)keys.next();
	   			if( jsonObj.get(key) instanceof JSONObject ){
	   				jsonvid = jsonObj.getJSONObject(key);
	   				String content = jsonvid.getString("comment_content");
	   				author = jsonvid.getString("comment_author");
	   				
	   				
	   			 auth[i] = new TextView(this);
	             auth[i].setTextSize(15);
	             auth[i].setLayoutParams(lp1);
	             auth[i].setId(i);
	             auth[i].setPadding(20, 8, 8, 10);
	             auth[i].setText(author);
	             auth[i].setTextColor(Color.parseColor("#0099CC"));
	   				
	   			 comm[i] = new TextView(this);
	             comm[i].setTextSize(15);
	             comm[i].setLayoutParams(lp);
	             comm[i].setId(i);
	             comm[i].setText(content);
	             comm[i].setPadding(20, 8, 8, 50);
	             myLayout.addView(auth[i]);
	             myLayout.addView(comm[i]);
	   				
	   				
	   				//image =url;
	   				//String vidurl = jsonvid.getString("yotube");
	   				//imgs.add(url);
	   				//title.add(jsonvid.getString("postTitle"));
	   				//titles = jsonvid.getString("postTitle");
	   				//excerpt = jsonvid.getString("excerpt");
	   				//collyvideos acv = new collyvideos(image,vidurl,excerpt,titles,cat,"","");
	   				//cv.add(acv);
	   				i++;
	   				
	   			}
	   			
	   		}
	    }
	    catch(Exception e)
	   	{
	   		Log.e("JSON Parser", "Error parsing data " + e.toString());
	   	}
	}
	
	
	public void CallBackRt(String Results)
	{

		 JSONObject jsonObj = null;
	       String str = Results;
	       
	       try{
	   		jsonObj = new  JSONObject(str);
	   		Iterator<?> keys = jsonObj.keys();
	   		float rating = Float.parseFloat(jsonObj.getString("rating"));
	   		RatingBar rtb = (RatingBar)findViewById(R.id.ratingBar1);
	   		rtb.setRating(rating);
	       }
	       catch(Exception e)
		   	{
		   		Log.e("JSON Parser", "Error parsing data " + e.toString());
		   	}
		
	}
	
	
	 public PayPal initService()
	 {
		  PayPal ppObj = PayPal.getInstance();
          
          bd  = getIntent().getExtras();
            

            if (ppObj == null) {  // Test to see if the library is already initialized

            	// This main initialization call takes your Context, AppID, and target server
            	ppObj = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_NONE);

            	// Required settings:

            	// Set the language for the library
            	ppObj.setLanguage("en_US");

            	// Some Optional settings:

            	// Sets who pays any transaction fees. Possible values are:
            	// FEEPAYER_SENDER, FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY
            	ppObj.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);

            	// true = transaction requires shipping
            	ppObj.setShippingEnabled(true);

            	//_paypalLibraryInit = true;
            	}
           return ppObj;
	 }
	 
	 
	 
	 @Override
	    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
	        if (resultCode == Activity.RESULT_OK) {
	            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	            if (confirm != null) {
	                try {
	                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
	                    
	                    String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
	                    
	                    
	                    String vidttl ="";
	                    String vidtitle =  bd.getString("title");
	    		   	    int pos = vidtitle.indexOf("Pay Per View");
	    				if(pos!= -1)
	    					vidttl = vidtitle.substring(0,pos-1);
	    				
	                    
	                   Intent vid = new Intent(getApplicationContext(), videoplay.class);
	 	               vid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	               String ttl = bd.getString("title");
	 	               vid.putExtra("title",vidttl);
	 	               vid.putExtra("bitmap",  bitmap);
	 	               startActivity(vid);

	                    // TODO: send 'confirm' to your server for verification.
	                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	                    // for more details.

	                } catch (JSONException e) {
	                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
	                }
	            }
	        }
	        else if (resultCode == Activity.RESULT_CANCELED) {
	            Log.i("paymentExample", "The user canceled.");
	        }
	        else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
	            Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
	        }
	    }
	    
	    @Override
	    public void onDestroy() {
	        stopService(new Intent(this, PayPalService.class));
	        super.onDestroy();
	    }
	    
	 
	 
	 
	 
	 

}
