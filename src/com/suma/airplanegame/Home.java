package com.suma.airplanegame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Home extends Activity {
	
	public static int winCount = 0;
	public static int highScore = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences sharedPref = getSharedPreferences("HIGHSCORE", 0);
		highScore = sharedPref.getInt("SCORE", 0);
		
		startActivity(new Intent("com.suma.airplanegame.MAINMENU"));
	}

}
