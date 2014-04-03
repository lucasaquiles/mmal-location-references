package br.com.lucasaquiles.mmall.location.main;

import br.com.lucasaquiles.mmall.location.service.LocationService;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		LocationService locationService = new LocationService(this);
		
		if(locationService.hasLocation()){
			
			double latitude = locationService.getLatitude();
			double longitude = locationService.getLongitude();
			
			textView = (TextView) findViewById(R.id.textView1);
			
			textView.setText("lat: "+latitude+"\n"+"lon: "+longitude);
			
		}else{
			
			locationService.showSettingsAlert();
		}
	}
}
