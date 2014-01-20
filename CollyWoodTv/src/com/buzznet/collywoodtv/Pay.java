package com.buzznet.collywoodtv;

import java.math.BigDecimal;

import org.json.JSONException;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;




import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class Pay extends Activity implements android.view.View.OnClickListener{

	
	// set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
    // set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials from https://developer.paypal.com
    // set to PaymentActivity.ENVIRONMENT_NO_NETWORK to kick the tires without communicating to PayPal's servers.
    private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;
    
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";
    // when testing in sandbox, this is likely the -facilitator email address. 
    private static final String CONFIG_RECEIVER_EMAIL = "junior.hart@yahoo.co.uk"; 
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_activity);
        
        
        
        
            PayPal ppObj = PayPal.getInstance();
            
            
            

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
            
            
            
    		CheckoutButton launchPayPalButton = ppObj.getCheckoutButton(this,PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
    		CheckoutButton launchPayPalButton1 = ppObj.getCheckoutButton(this,PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
    		
    		LinearLayout.LayoutParams params = new
    				LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
    						LinearLayout.LayoutParams.WRAP_CONTENT);
    		//params.addRule(LinearLayout.ALIGN_PARENT_BOTTOM);
    		
    		params.bottomMargin = 10;
    		
    		launchPayPalButton.setLayoutParams(params);
    		launchPayPalButton.setOnClickListener(this);
    		
    		((LinearLayout)findViewById(R.id.paylaout)).addView(launchPayPalButton);
    		((LinearLayout)findViewById(R.id.paylaout)).addView(launchPayPalButton1);
        
        
        
        
        
Intent i = new Intent(this, PayPalService.class);
        
        i.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        i.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        i.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
        
        startService(i);
    }

    public void onBuyPressed(View pressed) {
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("5.00"), "USD", "TheArrangment");
        
        Intent intent = new Intent(this, PaymentActivity.class);
        
        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
        
        // It's important to repeat the clientId here so that the SDK has it if Android restarts your 
        // app midway through the payment UI flow.
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "credential-from-developer.paypal.com");
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        
        startActivityForResult(intent, 0);
    }
    
    
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		 PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("5.00"), "USD", "TheArrangment");
	        
	        Intent intent = new Intent(this, PaymentActivity.class);
	        
	        
	        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
	        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
	        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
	        
	        // It's important to repeat the clientId here so that the SDK has it if Android restarts your 
	        // app midway through the payment UI flow.
	        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "credential-from-developer.paypal.com");
	        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
	        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
	        
	        startActivityForResult(intent, 0);
		
	}
    
    
    
    
    
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    
                    String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                    

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
