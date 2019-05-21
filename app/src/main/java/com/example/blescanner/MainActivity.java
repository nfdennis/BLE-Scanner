package com.example.blescanner; //edu.ucla.ctrl.dgit.bestaid

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.recognition.packets.Nearable;
import com.estimote.coresdk.service.BeaconManager;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	private BeaconManager beaconManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// For estimote cloud integration -- needed for sdk
		EstimoteSDK.initialize(getApplicationContext(), "java-estimote-app-brw", "d9dcb784bec2405879e6e6165631fba8");
		EstimoteSDK.enableDebugLogging(true);
		
		System.out.println("------ Start Test in 'onCreate()'------");
		
		beaconManager = new BeaconManager(this);
		beaconManager.setNearableListener(new BeaconManager.NearableListener() {
			@Override
			public void onNearablesDiscovered(List<Nearable> nearables) {
				for (Nearable item : nearables) {
					System.out.println(item.identifier);
					System.out.println(item);
					
					TextView beaconView = (TextView) findViewById(R.id.beaconView);
					beaconView.setText(item.identifier);
					
					/** rssi indicator **/
					// At maximum Broadcasting Power (+4 dBm) the RSSI ranges from -26 (a few inches) to -100 (40-50 m distance).
					TextView rssiView = (TextView) findViewById(R.id.rssiView);
					rssiView.setText(Double.toString(item.rssi));
					
					ImageView rssiImage = (ImageView) findViewById(R.id.rssiImage);
					if (item.rssi > -90) {
						rssiImage.setImageResource(R.drawable.success);
					} else if (item.rssi < -90 ) {
						rssiImage.setImageResource(R.drawable.warn);
					}
					
					
					/**  last upload indicator **/
					TextView uploadView = (TextView) findViewById(R.id.uploadView);
					uploadView.setText("true");
					
					ImageView uploadImage = (ImageView) findViewById(R.id.uploadImage);
					if (GlobalVars.lastUpload == true) {
						uploadImage.setImageResource(R.drawable.success);
					} else {
						uploadImage.setImageResource(R.drawable.fail);
					}
					
					
					/** sticker battery indicator **/
					TextView batteryView = (TextView) findViewById(R.id.batteryView);
					batteryView.setText("" + item.batteryLevel);
					
					
					System.out.println("GlobalVars.pid: " + GlobalVars.pid);
					
					
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		System.out.println("------ Start Test in 'onStart()'------");
		
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				beaconManager.startNearableDiscovery();
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//beaconManager.stopNearableDiscovery(scanId);
	}
	
	/** Called when the user taps the Send button **/
	public void enterConfig(View view) {
		// invisible button to config activity
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivity(intent);
	}
	
}
