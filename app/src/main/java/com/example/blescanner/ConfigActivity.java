package com.example.blescanner;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
	}
	
	protected void onStart(Bundle savedInstanceState) {
		TextView participantView = (TextView) findViewById(R.id.participantView);
		participantView.setText(getString(R.string.participant_view, GlobalVars.pid));
	}
	
	/** Called when admin sets participant_id **/
	public void setID(View view) {
		Toast.makeText(ConfigActivity.this, "Participant ID Set", Toast.LENGTH_LONG);
		
		// Save to local variable String PID
		EditText idConfig = (EditText) findViewById(R.id.idConfig);
		GlobalVars.pid = idConfig.getText().toString();
		TextView participantView = (TextView) findViewById(R.id.participantView);
		participantView.setText(GlobalVars.pid);
	}
	
	/** Called when admin save button is pressed **/
	public void saveID(View view) {
		// Toast.makeText(ConfigActivity.this, "Participant ID Set", Toast.LENGTH_LONG);
		
		// Pass participant ID and Return to MainActivity
		Intent intent = new Intent( ConfigActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
}
