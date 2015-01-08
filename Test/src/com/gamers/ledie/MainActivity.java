package com.gamers.ledie;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	BackgroundSound mBackgroundSound = new BackgroundSound();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.activity_main);
	}
	public void sendMessage(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Map1.class);
		mBackgroundSound.cancel(true);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class BackgroundSound extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.dream);
			player.setLooping(true); // Set looping 
		    player.setVolume(100,100); 
		    player.start();
		    return null;
		 }
		   }
	public void onResume() {
		super.onResume();
		//mBackgroundSound = new BackgroundSound();
		mBackgroundSound.doInBackground();
		}
	public void onPause() {
		super.onPause();
		mBackgroundSound.cancel(true);
		}
	
}


