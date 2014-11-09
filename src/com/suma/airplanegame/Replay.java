package com.suma.airplanegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Replay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_replay);
		
		TextView message = (TextView)findViewById(R.id.replay_message);
		if(GamePlay.win == true){
			message.setText("You Win!");
		} else{
			message.setText("You Crashed");
		}
		
		Button replay = (Button)findViewById(R.id.replay_button);
		Button menu = (Button)findViewById(R.id.menu_button);
		
		replay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.suma.airplanegame.GAMEPLAY"));
			}
		});
		
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.suma.airplanegame.MAINMENU"));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_replay, menu);
		return true;
	}

}
