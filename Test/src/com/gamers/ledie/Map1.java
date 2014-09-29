package com.gamers.ledie;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.FrameLayout.LayoutParams;
import android.util.Log;

public class Map1 extends ActionBarActivity {
	private final static String LOG_TAG = "HELP ME";
	  FrameLayout f1;
	  FrameLayout f2;
	  int blobSize, blobX, blobY;
	  ImageView i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_map1);
		f1 = new FrameLayout(this);
		f2 = new FrameLayout(this);
		  // Instantiate an ImageView and define its properties
		  ImageView i = new ImageView(this);
		  i.setImageResource(R.drawable.qwer);
		  i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
		 // i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
		  //LayoutParams.WRAP_CONTENT));

		  // Add the ImageView to the layout and set the layout as the content view
		  f1.addView(i);
		  LayoutParams params = new FrameLayout.LayoutParams(2560, 1440);
		  this.addContentView(f1, params);
		  // Instantiate an ImageView and define its properties
		  i = new ImageView(this);
		  i.setImageResource(R.drawable.blobcrap);
		  i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions

		  // Add the ImageView to the layout as a frame view (addContentView vs. setContentView)
		  f2.addView(i);
		  blobSize = 170;
		  blobY = 0;
		  blobX = 0;
		  params = new FrameLayout.LayoutParams(blobSize, blobSize);
		  this.addContentView(f2, params);
		  }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map1, menu);
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
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int x = (int)e.getRawX();
		int y = (int)e.getRawY();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int rightBounds = size.x;
		int bottomBounds = size.y;
		int rightStart = (rightBounds*2)/3;
		int left = rightBounds/3;
		int top = bottomBounds/2;
		Log.d(LOG_TAG, "X position: " + x);
		Log.d(LOG_TAG, "Y position: " + y);
		Log.d(LOG_TAG, "Screen x: " + rightBounds);
		Log.d(LOG_TAG, "Screen y: " + bottomBounds);
		Log.d(LOG_TAG, "right: " + rightStart);
		if(x<left){//left
			blobX = blobX-10;
			blobMove();
		}
		if(rightStart<x){//right
			blobX = blobX+10;
			blobMove();
		}
		if(x>left&&rightStart>x&&y>top){//down
			blobY = blobY+10;
			blobMove();
		}
		if(x>left&&rightStart>x&&y<top){//up
			blobY = blobY-10;
			blobMove();
		}
		//blobSize = blobSize +25;
		//blobX = blobX+10;
		//blobY = blobY+10;
		
		return true;
	}
	public boolean blobMove(){
		f2.removeAllViews();
		f2 = new FrameLayout(this);
		i = new ImageView(this);  
		i.setImageResource(R.drawable.blobcrap);
		//i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
		  // Add the ImageView to the layout as a frame view (addContentView vs. setContentView)
		f2.addView(i);
		LayoutParams params = new FrameLayout.LayoutParams(blobSize, blobSize);
		params.leftMargin=blobX;
		params.topMargin=blobY;
		this.addContentView(f2, params);
	return true;	
	}
}
