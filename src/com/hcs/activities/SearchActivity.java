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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hcs.adapter.RecentLocationAdepter;
import com.hcs.beans.SearchLocationBean;
import com.hcs.database.DatabaseHandler;

public class SearchActivity extends ParentScreen {

	AutoCompleteTextView atvPlaces;
	PlacesTask placesTask;

	private List<HashMap<String, String>> searchListRecord = null;

	private ListView mRecentList;
	private List<SearchLocationBean> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Bundle bundle = getIntent().getExtras();
		final String action = bundle.getString("action");
		final String source = bundle.getString("source");
		final DatabaseHandler db = new DatabaseHandler(this);

		contactList = new ArrayList<SearchLocationBean>();
		contactList = db.getAllSearch();
		mRecentList = (ListView) findViewById(R.id.listView1);

		RecentLocationAdepter adepter = new RecentLocationAdepter(
				SearchActivity.this, contactList);
		mRecentList.setAdapter(adepter);

		mRecentList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (action.equals("home")) {
					Intent resultIntent = new Intent();
					resultIntent.putExtra("location", contactList.get(position)
							.get_address());
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
				} else {
					Intent resultIntent = new Intent(SearchActivity.this,
							RideEstimation.class);
					resultIntent.putExtra("location", contactList.get(position)
							.get_address());
					resultIntent.putExtra("source", source);
					startActivity(resultIntent);
					finish();
				}
			}
		});

		atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
		atvPlaces.setThreshold(1);

		atvPlaces.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (searchListRecord != null) {
					String item = searchListRecord.get(position).get(
							"description");
					SearchLocationBean bean = new SearchLocationBean();
					bean.set_address(item);
					db.addLocation(bean);
					if (action.equals("home")) {
						Intent resultIntent = new Intent();
						resultIntent.putExtra("location", item);
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
					} else {
						Intent resultIntent = new Intent(SearchActivity.this,
								RideEstimation.class);
						resultIntent.putExtra("location", item);
						resultIntent.putExtra("source", source);
						startActivity(resultIntent);
						finish();
					}

					// Toast.makeText(getApplicationContext(),
					// "selected item "+item, 5000).show();
				}

			}
		});

		atvPlaces.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				placesTask = new PlacesTask();
				placesTask.execute(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stubsdfsffsdfssfsfsf
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});

	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void,  List<HashMap<String, String>>> {

		@Override
		protected List<HashMap<String, String>>  doInBackground(String... place) {
			// For storing data from web service
			String data = "";
			List<HashMap<String, String>> places = null;
			// Obtain browser key from https://code.google.com/apis/console
			String key = "key=AIzaSyD7Q1COvC3bS0WOazO6QKcBrCLOPDXdfzw";

			String input = "";

			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			// place type to be searched
			String types = "types=";

			// Sensor enabled
			String sensor = "sensor=false";

			// Building the parameters to the web service
			String parameters = input + "&" + types + "&" + sensor + "&" + key;

			// Output format
			String output = "json";

			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
					+ output + "?" + parameters;

			try {
				// Fetching the data from we service
				data = downloadUrl(url);
			

				PlaceJSONParser placeJsonParser = new PlaceJSONParser();
				JSONObject jObject;
					jObject = new JSONObject(data);

					// Getting the parsed data as a List construct
					places = placeJsonParser.parse(jObject);

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}
				return places;
		}
		
		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			searchListRecord = new ArrayList<HashMap<String, String>>();
			searchListRecord = result;
			String[] from = new String[] { "description" };
			int[] to = new int[] { android.R.id.text1 };

			// Creating a SimpleAdapter for the AutoCompleteTextView
			SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
					searchListRecord, android.R.layout.simple_list_item_1,
					from, to);

			// Setting the adapter
			atvPlaces.setAdapter(adapter);
			
		}

	
	}



	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}