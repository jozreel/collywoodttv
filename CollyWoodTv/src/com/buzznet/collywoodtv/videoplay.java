package com.buzznet.collywoodtv;

//import com.stream.R;

//import com.stream.R;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class videoplay extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {
	static private final String DEVELOPER_KEY = "AIzaSyBxYf7Pu3v0KumqZfUyeZMD_c8JhUsmJiE";
	Bundle ex;
	String vid;
	String vidid;
	String VIDEO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		ex = getIntent().getExtras();
		 if(ex!=null)
		    	vid = ex.getString("url");
		//TextView tv = (TextView)findViewById(R.id.textView1);
		//VideoView videoView = (VideoView)findViewById(R.id.videoV);
		int pos = vid.indexOf("?rel");
		if(pos!= -1)
			vidid = vid.substring(0, pos);
		else 
			vidid = vid;
		
		VIDEO = vidid;
        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.youtube_view);
     //   youTubeView.initialize(DEVELOPER_KEY, this);

    }
		
	/*	MediaController mc = new MediaController(this);
		
		 videoView.setMediaController(mc);
		 String s = "http://www.youtube.com/watch?v="+vidid;
		 //  String s = "http://www.youtube.com/watch?v=ygnEPRGX090?rel=0";
		// tv.setText(s);
		Intent intt = new Intent(Intent.ACTION_VIEW);
		 intt.setData(Uri.parse(s));
		 startActivity(intt);
		 Uri uri = Uri.parse("s");
		   
		   videoView.setVideoURI(uri);
		  
		 //  
		  videoView.requestFocus();
		   
		  videoView.start();
		  */
		
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
}
