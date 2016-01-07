package com.hcs.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hcs.services.GPSTracker;

public class RideEstimation extends FragmentActivity implements OnClickListener {
private TextView mRate ,mSource,mDestination;
private GoogleMap map;
private GPSTracker gpsTracker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_ride_estimation);
		String rs=getResources().getString(R.string.Rs);
		
		 Bundle bundle = getIntent().getExtras();
	        String location = bundle.getString("location");
	        final String source = bundle.getString("source");
		
		mRate=(TextView) findViewById(R.id.rate);
		mSource=(TextView) findViewById(R.id.textView1);
		mDestination=(TextView) findViewById(R.id.textView2);
		findViewById(R.id.back).setOnClickListener(this);
		mSource.setText(source);
		mDestination.setText(location);
		mRate.setText(rs+" 150"+"-"+rs+"175");
		  if (map == null) {
		
			  
			  map = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	           
	        }
		  
		
			String url1 = "https://maps.googleapis.com/maps/api/geocode/json?";

			try {
				// encoding special characters like space in the user input
				// place
				location = URLEncoder.encode(location, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String address = "address=" + location;

			String sensor = "sensor=false";

			// url , from where the geocoding data is fetched
			url1 = url1 + address + "&" + sensor;

			// Instantiating DownloadTask to get places from Google
			// Geocoding service
			// in a non-ui thread
		

			// Start downloading the geocoding places
			new DownloadTask1().execute(url1);
		   gpsTracker = new GPSTracker(this);
		
          
          map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude()),
					10.0f));
	}
	
	  private String getDirectionsUrl(LatLng origin,LatLng dest){
		  
	        // Origin of route
	        String str_origin = "origin="+origin.latitude+","+origin.longitude;
	 
	        // Destination of route
	        String str_dest = "destination="+dest.latitude+","+dest.longitude;
	 
	        // Sensor enabled
	        String sensor = "sensor=false";
	 
	        // Building the parameters to the web service
	        String parameters = str_origin+"&"+str_dest+"&"+sensor;
	 
	        // Output format
	        String output = "json";
	 
	        // Building the url to the web service
	        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
	 
	        return url;
	    }
	 
	    /** A method to download json data from url */
	    private String downloadUrl(String strUrl) throws IOException{
	        String data = "";
	        InputStream iStream = null;
	        HttpURLConnection urlConnection = null;
	        try{
	            URL url = new URL(strUrl);
	 
	            // Creating an http connection to communicate with url
	            urlConnection = (HttpURLConnection) url.openConnection();
	 
	            // Connecting to url
	            urlConnection.connect();
	 
	            // Reading data from url
	            iStream = urlConnection.getInputStream();
	 
	            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
	 
	            StringBuffer sb  = new StringBuffer();
	 
	            String line = "";
	            while( ( line = br.readLine())  != null){
	                sb.append(line);
	            }
	 
	            data = sb.toString();
	 
	            br.close();
	 
	        }catch(Exception e){
	            Log.d("Exception while downloading url", e.toString());
	        }finally{
	            iStream.close();
	            urlConnection.disconnect();
	        }
	        return data;
	    }
	 
	    // Fetches data from url passed
	    private class DownloadTask extends AsyncTask<String, Void, String>{
	 
	        // Downloading data in non-ui thread
	        @Override
	        protected String doInBackground(String... url) {
	 
	            // For storing data from web service
	            String data = "";
	 
	            try{
	                // Fetching the data from web service
	                data = downloadUrl(url[0]);
	            }catch(Exception e){
	                Log.d("Background Task",e.toString());
	            }
	            return data;
	        }
	 
	        // Executes in UI thread, after the execution of
	        // doInBackground()
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	 
	            ParserTask parserTask = new ParserTask();
	 
	            // Invokes the thread for parsing the JSON data
	            parserTask.execute(result);
	        }
	    }
	 
	    /** A class to parse the Google Places in JSON format */
	    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	 
	        // Parsing the data in non-ui thread
	        @Override
	        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
	 
	            JSONObject jObject;
	            List<List<HashMap<String, String>>> routes = null;
	 
	            try{
	                jObject = new JSONObject(jsonData[0]);
	                DirectionsJSONParser parser = new DirectionsJSONParser();
	 
	                // Starts parsing data
	                routes = parser.parse(jObject);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	            return routes;
	        }
	 
	        // Executes in UI thread, after the parsing process
	        @Override
	        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
	            ArrayList<LatLng> points = null;
	            PolylineOptions lineOptions = null;
	            MarkerOptions markerOptions = new MarkerOptions();
	            String distance = "";
	            String duration = "";
	 
	            if(result.size()<1){
	                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
	                return;
	            }
	 
	            // Traversing through all the routes
	            for(int i=0;i<result.size();i++){
	                points = new ArrayList<LatLng>();
	                lineOptions = new PolylineOptions();
	 
	                // Fetching i-th route
	                List<HashMap<String, String>> path = result.get(i);
	 
	                // Fetching all the points in i-th route
	                for(int j=0;j<path.size();j++){
	                    HashMap<String,String> point = path.get(j);
	 
	                    if(j==0){    // Get distance from the list
	                        distance = (String)point.get("distance");
	                        continue;
	                    }else if(j==1){ // Get duration from the list
	                        duration = (String)point.get("duration");
	                        continue;
	                    }
	 
	                    double lat = Double.parseDouble(point.get("lat"));
	                    double lng = Double.parseDouble(point.get("lng"));
	                    LatLng position = new LatLng(lat, lng);
	 
	                    points.add(position);
	                }
	 
	                // Adding all the points in the route to LineOptions
	                lineOptions.addAll(points);
	                lineOptions.width(10);
	                lineOptions.color(Color.BLUE);
	            }
	 
	           // tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
	 
	            // Drawing polyline in the Google Map for the i-th route
	            map.addPolyline(lineOptions);
	        }
	    }
	    
	    /** A class, to download Places from Geocoding webservice */
		private class DownloadTask1 extends AsyncTask<String, Integer, String> {

			String data = null;

			// Invoked by execute() method of this object
			@Override
			protected String doInBackground(String... url) {
				try {
					data = downloadUrl(url[0]);
				} catch (Exception e) {
					Log.d("Background Task", e.toString());
				}
				return data;
			}

			// Executed after the complete execution of doInBackground() method
			@Override
			protected void onPostExecute(String result) {

				// Instantiating ParserTask which parses the json data from
				// Geocoding webservice
				// in a non-ui thread
				ParserTask1 parserTask = new ParserTask1();

				// Start parsing the places in JSON format
				// Invokes the "doInBackground()" method of the class ParseTask
				parserTask.execute(result);
			}
		}

		/** A class to parse the Geocoding Places in non-ui thread */
		class ParserTask1 extends
				AsyncTask<String, Integer, List<HashMap<String, String>>> {

			JSONObject jObject;

			// Invoked by execute() method of this object
			@Override
			protected List<HashMap<String, String>> doInBackground(
					String... jsonData) {

				List<HashMap<String, String>> places = null;
				GeocodeJSONParser parser = new GeocodeJSONParser();

				try {
					jObject = new JSONObject(jsonData[0]);

					/** Getting the parsed data as a an ArrayList */
					places = parser.parse(jObject);

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}
				return places;
			}

			// Executed after the complete execution of doInBackground() method
			@Override
			protected void onPostExecute(List<HashMap<String, String>> list) {

				// Clears all the existing markers
				// ma.clear();

				for (int i = 0; i < list.size(); i++) {

					// Creating a marker
					MarkerOptions markerOptions = new MarkerOptions();

					// Getting a place from the places list
					HashMap<String, String> hmPlace = list.get(i);

					// Getting latitude of the place
					double lat = Double.parseDouble(hmPlace.get("lat"));

					// Getting longitude of the place
					double lng = Double.parseDouble(hmPlace.get("lng"));

					// Getting name
					String name = hmPlace.get("formatted_address");

					LatLng latLng = new LatLng(lat, lng);

					// Setting the position for the marker
					markerOptions.position(latLng);

					// Setting the title for the marker
					markerOptions.title(name);

					// Placing a marker on the touched position
					map.addMarker(markerOptions);

					
					// Locate the first location
					if (i == 0)
					
					  
			        

			        
					new DownloadTask().execute(getDirectionsUrl( new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude()), latLng));
			   
						map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
				}
			}
		}
		public void Ok(View v)
		{
			finish();
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			
			default:
				break;
			}	
		}
}
