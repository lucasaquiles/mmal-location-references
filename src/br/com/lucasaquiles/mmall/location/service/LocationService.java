package br.com.lucasaquiles.mmall.location.service;

import br.com.lucasaquiles.mmall.location.main.MainActivity;
import android.R;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class LocationService extends Service implements LocationListener{
	
	private final Context currentContext;
	private LocationManager locationManager;
	
	private boolean isNetworkOn = false;
	private boolean hasLocation = false;
	
	private Location location = null;
	private double longitude;
	private double latitude;
	
	public LocationService(Context context) {
		this.currentContext =  context;
		getLocation();
	}
	
	public Location getLocation(){
		
		try{
			
			locationManager = (LocationManager) currentContext.getSystemService(LOCATION_SERVICE);
			
			isNetworkOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isNetworkOn){
				
			}else{
				
				this.hasLocation = true;
				
				int tempoMinimoDeUpdate = 0; //1000 * 60 * 1;
				int distanciaMinima = 0;
				
				if(isNetworkOn){
					
					locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, tempoMinimoDeUpdate, distanciaMinima, LocationService.this);
					if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
				}
			}
			
		}catch(Exception e){
			
		}
		
		return location;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		MainActivity mainActivity = (MainActivity) currentContext;
		
		TextView textView = (TextView) mainActivity.findViewById(br.com.lucasaquiles.mmall.location.main.R.id.textView1);
		
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
		
		textView.setText("lat: "+latitude+"\n"+"lon: "+longitude);
		
		Toast.makeText(currentContext, "lat: "+latitude+"\n"+"lon: "+longitude, Toast.LENGTH_LONG).show();;
	}

	@Override
	public void onProviderDisabled(String provider) {
		showSettingsAlert();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(currentContext, "Aee \\o/\n"+provider, Toast.LENGTH_LONG).show();;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public double getLatitude() {
		
		if(location != null){
            latitude = location.getLatitude();
        }
		
		return latitude;
	}
	
	public double getLongitude() {
		
		if(location != null){
            longitude = location.getLongitude();
        }
		return longitude;
	}
	
	public boolean hasLocation() {
		return this.hasLocation;
	}
	
	 public void showSettingsAlert(){
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentContext);
	      
	        alertDialog.setTitle("GPS is settings");
	  
	        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
	  
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                currentContext.startActivity(intent);
	            }
	        });
	  
	        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.cancel();
	            }
	        });
	  
	        alertDialog.show();
	}
	
	 public void stopLocationTracker(){
		 if(locationManager != null){
			 
			 locationManager.removeUpdates(LocationService.this);
			 
		 }
	 }
}
