package com.suma.airplanegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class GamePlay extends Activity implements OnTouchListener {

	mySView v;
	Bitmap balloon;
	static boolean win;
	boolean screenPress = false, gameEnd = false, balloonRise = false;
	float x, y;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		v = new mySView(this);
		v.setOnTouchListener(this);
		
		balloon = BitmapFactory.decodeResource(getResources(), R.drawable.balloon);
		
		x = 0;
		y = 0;
		
		setContentView(v);
	}

	@Override
	protected void onPause() {
		super.onPause();	
		v.pause();
	}
	@Override
	protected void onResume() {
		super.onResume();		
		v.resume();
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if(arg1.getAction() == MotionEvent.ACTION_DOWN){
			screenPress = true;
		}
		if(arg1.getAction() == MotionEvent.ACTION_UP){
			screenPress = false;
		}
		
		return true;
	}
	
	public class mySView extends SurfaceView implements Runnable{
		
		
		Thread t = null;
		SurfaceHolder holder;
		boolean isOk = false;
		

		public mySView(Context context) {
			super(context);
			holder = getHolder();
		}

		@Override
		public void run() {
			while(isOk == true){
				if(! holder.getSurface().isValid()){
					continue;
				}
				
				// Preparing Canvas //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				Canvas canvas = holder.lockCanvas();
				
				Rect sky = new Rect();
				sky.set(0, 0, canvas.getWidth(), canvas.getHeight());
				
				
				Paint blueSky = new Paint();
				blueSky.setColor(Color.CYAN);
				blueSky.setStyle(Paint.Style.FILL);
				
				canvas.drawRect(sky, blueSky);
				
				// Check for landing
				if((y > canvas.getHeight())){
					gameEnd = true;
				}
							
				if(!gameEnd){
					// Update Char Start
					if(x < canvas.getWidth()){
						x += 8;
					}
					else{
						x = 0;
					}
					if(screenPress){
						y -= 1;
					}
					else{
						y += 2.1;
					}
					// Update Char End
					
				}
				else{
					if((x >= canvas.getWidth()) && (y >= canvas.getHeight())){
						win = true;
						Home.winCount++;
						if(Home.winCount > Home.highScore){
							Home.highScore = Home.winCount;
							SharedPreferences sharedPrefs = getSharedPreferences("HIGHSCORE", 0);
						    SharedPreferences.Editor editor = sharedPrefs.edit();
						    editor.putInt("SCORE", Home.highScore);
						    editor.commit();
						}
					}
					else{
						win = false;
						Home.winCount = 0;
					}
					startActivity(new Intent("com.suma.airplanegame.REPLAY"));
				}
				
				canvas.drawBitmap(balloon, x - (balloon.getWidth()/2), y - (balloon.getHeight()/2), new Paint());
				canvas.drawText("score: " + Home.winCount, canvas.getWidth() - 55, 20, new Paint());
				
				holder.unlockCanvasAndPost(canvas);
				// Preparing Canvas End //////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			
		}
		
		public void pause() {
			isOk = false;
			
			while(true){
				try{
					t.join();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void resume() {
			isOk = true;
			t = new Thread(this);
			t.start();
		}
		
	}

}
