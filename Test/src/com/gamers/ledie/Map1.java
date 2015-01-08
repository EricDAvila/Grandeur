package com.gamers.ledie;

import java.io.IOException;
import java.util.Calendar;

import com.gamers.ledie.MainActivity.BackgroundSound;

import android.support.v7.app.ActionBarActivity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.FrameLayout.LayoutParams;
import android.util.Log;

public class Map1 extends ActionBarActivity {
	private final static String LOG_TAG = "HELP ME";
	  //TextView f1;
	  FrameLayout f1, f2, f3;
	  int blobSize, blobX, blobY;
	  ImageView i;
	  BackgroundSound mBackgroundSound = new BackgroundSound();
	  private static MediaPlayer player;
	  int currentMap, checker;
	  boolean noMovement, isFinalInstanceOfOnTouch;
	  int dialogueNum, storyPhase;
	  int[] dialogueResourceManager;
	  boolean spiritSpriteActive, atFemale, atBridge;
	  String mapBounds;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		//setContentView(R.layout.activity_map1);
		//final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
		//int pixels = (int) (300 * scale + 0.5f);
		//f1 = new TextView(this);
	    checker = 0;
	    spiritSpriteActive=false;
	    atFemale = false;
	    atBridge = false;
	    mapBounds = "none";
	    noMovement = true;
	    dialogueNum=1;
	    storyPhase = 1;
		f1 = new FrameLayout(this);
		LayoutParams rlParams = new LayoutParams(100, 100);
		f1.setLayoutParams(rlParams);
		f2 = new FrameLayout(this);
		  // Instantiate an ImageView and define its properties
		  ImageView i = new ImageView (this);
		  i.setImageResource(R.drawable.topleft);
		  currentMap = 1;
		  i.setLayoutParams(rlParams);
		  i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		  //i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
		 // i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
		  //LayoutParams.WRAP_CONTENT));

		  // Add the ImageView to the layout and set the layout as the content view
		 // f1.addView(i);
		  //LayoutParams params = new FrameLayout.LayoutParams(2560, 1440);
		  setContentView(i);
		  //this.addContentView(f1, params);
		  // Instantiate an ImageView and define its properties
		  i = new ImageView(this);
		  i.setImageResource(R.drawable.blobcrap);
		  i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions

		  // Add the ImageView to the layout as a frame view (addContentView vs. setContentView)
		  
		  f2.addView(i);
		  blobSize = 130;
		  blobY = 400;
		  blobX = 800;
		  rlParams = new FrameLayout.LayoutParams(blobSize, blobSize);
		  rlParams.setMargins(blobX, blobY, 0, 0);
		  this.addContentView(f2, rlParams);
		  dialogueNumberLoader();
		  dialogueFetcher(1);
		  isFinalInstanceOfOnTouch=false;
		  mBackgroundSound.cancel(false);
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
		if (e.getAction()!= MotionEvent.ACTION_DOWN&&noMovement){
			isFinalInstanceOfOnTouch=true;
			return false;
		}
		if(noMovement&&isFinalInstanceOfOnTouch==true){
			dialogueFetcher(storyPhase);
			dialogueNum++;
		return true;
		}
		if(!noMovement){
			/*Calendar c = Calendar.getInstance();
			int milli = c.get(Calendar.MILLISECOND);
			int sec = c.get(Calendar.SECOND);
			milli=milli+(sec*1000);
			Log.d(LOG_TAG, "Milli: " + milli);
			if(storeTime){
				checker = milli+5;
				storeTime=false;
			}
			Log.d(LOG_TAG, "Checker: " + checker);
			if (checker>milli){
				milli = c.get(Calendar.MILLISECOND);
				return true;
			}
			storeTime=true;*/
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
		if(x<left&&mapBoundsCheck()!="left"){//left
			blobX = blobX-10;
			LineCheck();
			blobMove();
		}
		if(rightStart<x&&mapBoundsCheck()!="right"){//right
			blobX = blobX+10;
			LineCheck();
			blobMove();
		}
		if(x>left&&rightStart>x&&y>top&&mapBoundsCheck()!="down"){//down
			blobY = blobY+10;
			LineCheck();
			blobMove();
		}
		if(x>left&&rightStart>x&&y<top&&mapBoundsCheck()!="up"){//up
			blobY = blobY-10;
			LineCheck();
			blobMove();
		}
		return true;
	}
		return true;
	}

	private String mapBoundsCheck() {//creates a bounding square around map
		String bounds = "none";
		switch (currentMap){
		case 1:
			if(blobX<750&&blobY>200)
				return "left";
			if(blobY<300)
				return "up";
			break;
		case 2:
			if(blobX>1250)
				return "right";
			if(blobY<300)
				return "up";
			break;
		case 3:
			if(blobX<750)
				return "left";
			if(blobY>500)
				return "down";
			break;
		case 4:
			if(blobX>1250)
				return "right";
			if(blobY>500)
				return "down";
			break;
		}
		return bounds;
	}

	public void LineCheck(){
		switch (currentMap){
		case 1:
			if(blobY > 800){
				MapUpdate("lat");
				break;
			}
			if(blobX>1700){
				MapUpdate("long");
				break;
			}
			if(blobX>450&&blobX<500&&blobY<310&&storyPhase==5){
				noMovement=true;//spirit3 Check
				break;
			}
			break;
		case 2:
			if(blobY > 800){
				MapUpdate("lat");
				break;
			}
			if(blobX<9){
				MapUpdate("long");
				break;
			}
			if(blobX>683&&blobX<900&&blobY>405&&blobY<562&&storyPhase==4){
				noMovement=true;//spirit2 Check
				break;
			}
			break;
		case 3:
			if(blobY < 9){
				MapUpdate("lat");
				break;
			}
			if(blobX>1700){
				MapUpdate("long");
				break;
			}
			if(blobX>783&&blobX<1000&&blobY>305&&blobY<562){//female Sprite check
				atFemale = true;
				noMovement=true;
				break;
			}
			if(blobY>490&&blobX>1300&&blobX<1600){//left bridge check
				atBridge=true;
				noMovement=true;
				break;
			}
			break;
		case 4:
			if(blobY <9){
				MapUpdate("lat");
				break;
			}
			if(blobX<9){
				MapUpdate("long");
				break;
			}
			if(spiritSpriteActive&&blobX>1200&&blobX<1400&&blobY>50&blobY<150&&storyPhase==3){
				noMovement=true;//spirit1 Check
				break;
			}
			if(blobY>490&&blobX>165&&blobX<460){//right bridge check
				atBridge=true;
				noMovement=true;
				break;
			}
			break;
		}
	}
	
	public void MapUpdate(String lineCrossed){
		switch (currentMap){
		case 1:
			if(lineCrossed=="long"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.topright);
				else
					i.setImageResource(R.drawable.toprights);
				currentMap = 2;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobX = 10;
				blobMove();
			}
			if(lineCrossed=="lat"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				i.setImageResource(R.drawable.bottomleft);
				currentMap = 3;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobY = 10;
				blobMove();
			}
			break;
		case 2:
			if(lineCrossed=="long"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.topleft);
				else
					i.setImageResource(R.drawable.toplefts);
				currentMap = 1;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobX = 1690;
				blobMove();
			}
			if(lineCrossed=="lat"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.bottomright);
				else
					i.setImageResource(R.drawable.bottomrights);
				currentMap = 4;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobY = 10;
				blobMove();
			}
			break;
		case 3:
			if(lineCrossed=="long"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.bottomright);
				else
					i.setImageResource(R.drawable.bottomrights);
				currentMap = 4;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobX = 10;
				blobMove();
			}
			if(lineCrossed=="lat"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.topleft);
				else
					i.setImageResource(R.drawable.toplefts);
				currentMap = 1;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobY = 790;
				blobMove();
			}
			break;
		case 4:
			if(lineCrossed=="long"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				i.setImageResource(R.drawable.bottomleft);
				currentMap = 3;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobX = 1690;
				blobMove();
			}
			if(lineCrossed=="lat"){
				LayoutParams rlParams = new LayoutParams(100, 100);
				ImageView i = new ImageView (this);
				if(!spiritSpriteActive)
					i.setImageResource(R.drawable.topright);
				else
					i.setImageResource(R.drawable.toprights);
				currentMap = 2;
				i.setLayoutParams(rlParams);
				i.setScaleType(ImageView.ScaleType.CENTER_CROP);
				setContentView(i);
				blobY = 790;
				blobMove();
			}
			break;
		}
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
	
	public class BackgroundSound extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
		    player = MediaPlayer.create(Map1.this, R.raw.lesjours); 
		    player.setLooping(true); // Set looping 
		    player.setVolume(100,100); 
		    player.start(); 
		    if(mBackgroundSound.cancel(true)){
		    	player.stop();
		    	return null;
		    }
		    return null;
		   }
	}
	
	public void onResume() {
		super.onResume();
		mBackgroundSound.doInBackground();
		}
	
	public void onPause() {
		super.onPause();
		mBackgroundSound.cancel(true);
		}
	public boolean femaleSpriteCheck(){
		if(blobX>783&&blobX<1000&&blobY>305&&blobY<562){
			return true;
		}
		return false;
	}
	public void bridgeCheck(int storyPhase, char bridge){
		if (storyPhase==7){//story over, queue movie
			if(bridge=='l'){//play left bridge movie
				
			}
			else{//play right bridge movie
			}
			}
		else {//show bridge default dialogue, phase 8
			dialogueFetcher(8);
		}
	}
	
	public void spiritCheck (int storyPhase){
		switch (storyPhase){
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}
	public void dialogueFetcher(int storyPhase2){//Handles clearing f3, calls to diaDisplay, noMov, and inc/dec dialNum
		Log.d(LOG_TAG, "storyPhase: " + storyPhase2);
		switch (storyPhase2){
		case 1:
			if(dialogueNum==1){	
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn1);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				dialogueNum++;
				break;
			}
			if(dialogueNum ==2){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn2);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum >=3){
				f3.removeAllViews();
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				atBridge=false;
				break;
			}
			break;
		case 2:
			if(atBridge){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn14);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
				dialogueNum--;
				atBridge=false;
				break;
			}
			if(dialogueNum ==3){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn3);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==4){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn4);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);;
				break;
				}
			if(dialogueNum ==5){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn5);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==6){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn6);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==7){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn7);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==8){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn8);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==9){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn9);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==10){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn10);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==11){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn11);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==12){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn12);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				dialogueNum=14;
				break;
				}
			if(dialogueNum >=13){
				f3.removeAllViews();
				//f3.setVisibility(View.GONE);
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				spiritSpriteActive =true;
				atFemale=false;
				atBridge=false;
				break;
				}
			break;
		case 3:
			if(atBridge){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn14);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
				dialogueNum--;
				atBridge=false;
				break;
			}
			if(atFemale){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn13);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				dialogueNum--;
				storyPhase--;
			break;
			}
			if(dialogueNum ==15){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn15);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==16){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn16);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==17){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn17);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==18){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn18);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==19){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn19);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==20){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn20);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==21){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn21);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==22){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn22);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==23){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn23);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum >=24){
				f3.removeAllViews();
				//f3.setVisibility(View.GONE);
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				spiritSpriteActive =true;
				atFemale=false;
				atBridge=false;
				break;
				}
			break;
		case 4:
			if(atBridge){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn14);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
				dialogueNum--;
				atBridge=false;
				break;
			}
			if(atFemale){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn13);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
			break;
			}
			if(dialogueNum ==24){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn24);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==25){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn25);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==26){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn26);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==27){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn27);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==28){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn28);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==29){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn29);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==30){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn30);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum >=31){
				f3.removeAllViews();
				//f3.setVisibility(View.GONE);
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				spiritSpriteActive =true;
				atFemale=false;
				atBridge=false;
				break;
				}
			break;
		case 5:
			if(atBridge){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn14);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
				dialogueNum--;
				atBridge=false;
				break;
			}
			if(atFemale){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn13);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
			break;
			}
			if(dialogueNum ==31){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn31);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==32){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn32);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==33){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn33);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==34){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn34);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==35){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn35);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==36){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn36);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==37){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn37);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==38){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn38);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==39){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn39);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==40){
				f3.removeAllViews();
				//f3.setVisibility(View.GONE);
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				spiritSpriteActive =false;
				atBridge=false;
				break;
				}
			break;
		case 6:
			if(atBridge){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn14);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				storyPhase--;
				dialogueNum--;
				atBridge=false;
				break;
			}
			if(dialogueNum ==40){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn40);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==41){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn41);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==42){
				f3.removeAllViews();
				f3 = new FrameLayout(this); 
				i = new ImageView (this);
				i.setImageResource(R.drawable.dn42);
				f3.addView(i);
				LayoutParams rlParams=new LayoutParams(1000,1000);
				rlParams.setMargins(450, 300, 0, 0);
				this.addContentView(f3, rlParams);
				break;
				}
			if(dialogueNum ==43){
				f3.removeAllViews();
				//f3.setVisibility(View.GONE);
				storyPhase++;
				noMovement = false;
				dialogueNum--;
				spiritSpriteActive =false;
				break;
				}
			break;
		case 7:
			if(atBridge&&currentMap==3){
				Intent intent = new Intent(this, Bad.class);
				player.stop();
				startActivity(intent);
				//Intent toStart = new Intent(Intent.ACTION_VIEW);
				//toStart.setDataAndType(Uri.parse("file://Card/Download/bad.mp4"), "*/*");
				//startActivity(toStart);
				
				//MediaPlayer mediaPlayer = new MediaPlayer();
				//if(mediaPlayer.isPlaying()){
					//   mediaPlayer.reset();
					  //}
				
					  //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					  //mediaPlayer.setDisplay(surfaceHolder);

					  /*try {
					      mediaPlayer.setDataSource("android.resource://com.testvideo/" + R.raw.bad);

					   mediaPlayer.prepareAsync();
					  } catch (IllegalArgumentException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
					  } catch (IllegalStateException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
					  } catch (IOException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
					  }*/
					 // MediaPlayer.create(this,Uri.parse("android.resource://com.testvideo/" + R.raw.bad));
					  //mediaPlayer.start();
				//Uri.parse("android.resource://com.testvideo/" + R.raw.bad);
				break;
			}
			if(atBridge&&currentMap==4){
				Intent intent = new Intent(this, Good.class);
				player.stop();
				startActivity(intent);
				break;
			}
		}
	}
	
	public void dialogueNumberLoader(){
		dialogueResourceManager = new int []{1337, 0x7f02005a, 0x7f020065, 0x7f020071, 0x7f02007c, 0x7f02007f, 0x7f020080, 0x7f020081, 
				0x7f020082, 0x7f020083, 0x7f02005b, 0x7f02005c, 0x7f02005d, 0x7f02005e, 0x7f02005f, 0x7f020060, 0x7f020061, 0x7f020062, 
				0x7f020063, 0x7f020064, 0x7f020066, 0x7f020067, 0x7f020068, 0x7f020069, 0x7f02006a, 0x7f02006b, 0x7f02006c, 0x7f02006d,
				0x7f02006e, 0x7f02006f, 0x7f020071, 0x7f020072, 0x7f020073, 0x7f020074, 0x7f020075, 0x7f020076, 0x7f020077, 0x7f020078,
				0x7f020079, 0x7f02007a, 0x7f02007c, 0x7f02007d, 0x7f02007e};
	}
	public void dialogueDisplayer(){
		f3 = new FrameLayout(this); 
		i = new ImageView (this);
		i.setImageResource(dialogueResourceManager[dialogueNum]);
		f3.addView(i);
		LayoutParams rlParams=new LayoutParams(1000,1000);
		rlParams.setMargins(450, 300, 0, 0);
		this.addContentView(f3, rlParams);
	}
}
