package com.hcs.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hcs.activities.Coupon;
import com.hcs.activities.Favorite;
import com.hcs.activities.GeocodeJSONParser;
import com.hcs.activities.Home;
import com.hcs.activities.R;
import com.hcs.activities.SearchActivity;
import com.hcs.beans.BookingCabBean;
import com.hcs.beans.HomeBean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.http.HttpConnectionManager;
import com.hcs.progressbar.ProgressHUD;
import com.hcs.services.GPSTracker;

public class HomeFragment extends Fragment implements OnClickListener,
		ApplicationConstants {
	private GoogleMap map;
	private LinearLayout hatchBack, sedan, suv, auto, more, confirmLinear,
			rideEstimate, coupons, rateCard, cabDetail, mLayoutTop,
			MdriverDetail, mTopSearch;
	private RelativeLayout confirm, cancel,rateRelative;
	private RelativeLayout pickNow, pickLater, mCancelRide;
	private EditText locationEdittext;
	private GPSTracker gpsTracker;
	private SharedPreferences sharedPreferences;
	private String cabId = null;
	private ImageView hatchBackImage, sedanImage, suvImage, autoImage,
			otherImage, drawerActionButton, mBack, myhomeLocation, mFavourite;
	private boolean fisttimeclick = true, sedancheck = true, suvCheck = true,
			autoCheck = true, otherCheck = true;
	private Calendar calendar;
	private boolean ispicknow = false;
	ProgressBar gettingAddressPB;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		gpsTracker = new GPSTracker(getContext());
		hatchBack = (LinearLayout) rootView.findViewById(R.id.hatchback);
		sedan = (LinearLayout) rootView.findViewById(R.id.sedan);
		suv = (LinearLayout) rootView.findViewById(R.id.suv);
		auto = (LinearLayout) rootView.findViewById(R.id.auto);
		more = (LinearLayout) rootView.findViewById(R.id.more);
		rateRelative = (RelativeLayout) rootView.findViewById(R.id.rateRelative);
		confirmLinear = (LinearLayout) rootView
				.findViewById(R.id.cabBookConfirm);
		confirm = (RelativeLayout) rootView.findViewById(R.id.confirm);
		cancel = (RelativeLayout) rootView.findViewById(R.id.cancel);
		rideEstimate = (LinearLayout) rootView.findViewById(R.id.rideEstimae);
		coupons = (LinearLayout) rootView.findViewById(R.id.coupons);
		rateCard = (LinearLayout) rootView.findViewById(R.id.rateCard);
		cabDetail = (LinearLayout) rootView.findViewById(R.id.cabDetail);
		mLayoutTop = (LinearLayout) rootView.findViewById(R.id.layoutTop);
		MdriverDetail = (LinearLayout) rootView.findViewById(R.id.driverDetail);
		mTopSearch = (LinearLayout) rootView.findViewById(R.id.topSearch);
		pickNow = (RelativeLayout) rootView.findViewById(R.id.pickNow);
		mCancelRide = (RelativeLayout) rootView.findViewById(R.id.cancelRide);
		pickLater = (RelativeLayout) rootView.findViewById(R.id.pickLater);
		locationEdittext = (EditText) rootView
				.findViewById(R.id.locationEditText);
		hatchBackImage = (ImageView) rootView.findViewById(R.id.hatchbackImage);
		sedanImage = (ImageView) rootView.findViewById(R.id.sedanImage);
		suvImage = (ImageView) rootView.findViewById(R.id.suvImage);
		autoImage = (ImageView) rootView.findViewById(R.id.autoImage);
		otherImage = (ImageView) rootView.findViewById(R.id.otherImage);
		mBack = (ImageView) rootView.findViewById(R.id.back);
		drawerActionButton = (ImageView) rootView.findViewById(R.id.imageView1);
		myhomeLocation = (ImageView) rootView.findViewById(R.id.myHomeLocation);
		gettingAddressPB = (ProgressBar) rootView
				.findViewById(R.id.progressBar1);
		mFavourite = (ImageView) rootView.findViewById(R.id.favourite);
		sharedPreferences = getActivity().getSharedPreferences("login",
				Context.MODE_PRIVATE);
        LinearLayout driverCall=(LinearLayout) rootView.findViewById(R.id.driverCall);
        LinearLayout shareDetail=(LinearLayout) rootView.findViewById(R.id.shareDetail);
        LinearLayout support=(LinearLayout) rootView.findViewById(R.id.support);
        LinearLayout rateRide=(LinearLayout) rootView.findViewById(R.id.rateRide);
        
        rateRelative.setOnClickListener(this);
        rateRide.setOnClickListener(this);
        shareDetail.setOnClickListener(this);
        support.setOnClickListener(this);
        driverCall.setOnClickListener(this);
		mFavourite.setOnClickListener(this);
		hatchBack.setOnClickListener(this);
		sedan.setOnClickListener(this);
		suv.setOnClickListener(this);
		auto.setOnClickListener(this);
		more.setOnClickListener(this);
		pickLater.setOnClickListener(this);
		pickNow.setOnClickListener(this);
		locationEdittext.setOnClickListener(this);
		drawerActionButton.setOnClickListener(this);
		myhomeLocation.setOnClickListener(this);
		confirmLinear.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		rideEstimate.setOnClickListener(this);
		coupons.setOnClickListener(this);
		rateCard.setOnClickListener(this);
		mLayoutTop.setOnClickListener(this);
		MdriverDetail.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mCancelRide.setOnClickListener(this);

		map = ((SupportMapFragment) this.getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		
		
		
		map.getUiSettings().setMyLocationButtonEnabled(false);
		 map.setMyLocationEnabled(true);

		map.setOnCameraChangeListener(new OnCameraChangeListener() {

			public void onCameraChange(CameraPosition arg0) {
				LatLng latLng = arg0.target;
				// double latitude = latLng.latitude;
				new ReverseGeocodingTask(getContext()).execute(latLng);
				map.setOnCameraChangeListener(this);
			}
		});
		map.setPadding(0, 90, 0, 0);
		GPSTracker gpsTracker = new GPSTracker(getActivity());

		new ReverseGeocodingTask(getContext()).execute(new LatLng(gpsTracker
				.getLatitude(), gpsTracker.getLongitude()));

		ArrayList<HomeBean> arrayList = new ArrayList<HomeBean>();
		HomeBean bean = new HomeBean();
		bean.setLatitude(String.valueOf(gpsTracker.getLatitude()));
		bean.setLongitude(String.valueOf(gpsTracker.getLongitude()));
		bean.setMyLocation(true);
		arrayList.add(bean);

		HomeBean bean1 = new HomeBean();
		bean1.setLatitude("28.6629");
		bean1.setLongitude("77.2100");
		bean1.setMyLocation(false);
		arrayList.add(bean1);

		HomeBean bean2 = new HomeBean();
		bean2.setLatitude("28.6139");
		bean2.setLongitude("77.2090");
		bean2.setMyLocation(false);
		arrayList.add(bean2);

		


	//	drawMarker(arrayList);

		// Inflate the layout for this fragment
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				gpsTracker.getLatitude(), gpsTracker.getLongitude()), 16.0f));
		return rootView;
	}

	public void bookCab(String cabId, String formattedDate) {

		BookingCabBean bean = new BookingCabBean();
		bean.setCabId(cabId);
		bean.setRegId(sharedPreferences.getString(REG_ID, ""));
		bean.setUserLat(String.valueOf(gpsTracker.getLatitude()));
		bean.setUserLong(String.valueOf(gpsTracker.getLongitude()));
		bean.setBookingDate(formattedDate);
		BookCabAsynk asynk = new BookCabAsynk();
		asynk.execute(bean);

	}

	public class BookCabAsynk extends AsyncTask<Object, Void, Object> implements
			OnCancelListener {
		// private static final Logger LOGGER =
		// Logger.getLogger(HTTPAsyncServiceTask.class);

		private String progressMsg = "Please wait..";
		private ProgressHUD mProgressHUD;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressHUD = ProgressHUD.show(getActivity(), progressMsg, true,
					false, this);
			// activity.displayManager.showProgressBar(activity, progressMsg);
		}

		@Override
		protected Object doInBackground(Object... params) {
			Object responseObject = null;
			try {
				BookingCabBean object = (BookingCabBean) params[0];

				HashMap<String, String> postDataParams = new HashMap<String, String>();

				JSONObject Detail = new JSONObject();
				try {
					Detail.put(REG_ID, object.getRegId());
					Detail.put(USER_LAT, object.getUserLat());
					Detail.put(USER_LONG, object.getUserLong());
					Detail.put(CAB_ID, object.getCabId());
					Detail.put(BOOKING_DATE, object.getBookingDate());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HttpConnectionManager httpConnManager = new HttpConnectionManager();
				responseObject = httpConnManager.performPostCall(BASE_URL
						+ BOOK_CAB + (new JSONArray().put(Detail)).toString(),
						postDataParams);
			} catch (Exception e) {
				// LOGGER.error("Error occured inside doInBackground ", e);
			}

			return responseObject;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			mProgressHUD.dismiss();

			if (result.toString().contains("Record inserted")) {
				Toast.makeText(getContext(), "Yor cab successfully booked",
						Toast.LENGTH_SHORT).show();
				// cabDetail.setVisibility(View.VISIBLE);
				confirmLinear.setVisibility(View.GONE);
				MdriverDetail.setVisibility(View.VISIBLE);
				mLayoutTop.setVisibility(View.VISIBLE);
				mTopSearch.setVisibility(View.GONE);
			}

			if (result == null || result instanceof String) {

			} else {

			}
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}

	private void drawMarker(ArrayList<HomeBean> arrayList) {
		// Creating an instance of MarkerOptions

		for (int i = 0; i < arrayList.size(); i++) {
			MarkerOptions markerOptions = new MarkerOptions();

			// Setting latitude and longitude for the marker
			markerOptions.position(new LatLng(Double.parseDouble(arrayList.get(
					i).getLatitude()), Double.parseDouble(arrayList.get(i)
					.getLongitude())));
			if (arrayList.get(i).isMyLocation()) {

				map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						Double.parseDouble(arrayList.get(i).getLatitude()),
						Double.parseDouble(arrayList.get(i).getLongitude())),
						16.0f));
			} else {
				markerOptions.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.car));
				map.addMarker(markerOptions);
			}

			// Adding marker on the Google Map

		}

	}

	public void showDilog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				R.style.AppCompatAlertDialogStyle);

		builder.setView(R.layout.fare_dilog);
		/*
		 * builder.setPositiveButton("OK", null);
		 * builder.setNegativeButton("Cancel", null);
		 */
		builder.show();

	}

	public void showCancelDilog() {

		ArrayList<String> cab = new ArrayList<String>();
		cab.add("Driver is late");
		cab.add("Driver denied duty");
		cab.add("Changed my mind");
		cab.add("Booked another cab");

		final CharSequence[] city = cab.toArray(new String[cab.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				R.style.AppCompatAlertDialogStyle);

		builder.setTitle("TELL US WHY");
		builder.setPositiveButton("OK", null);
		builder.setNegativeButton("Cancel", null);

		builder.setSingleChoiceItems(city, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// level = which;

					}
				});

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Your code when user clicked on OK
				// You can write the code to save the selected item here
				cabDetail.setVisibility(View.VISIBLE);
				// confirmLinear.setVisibility(View.GONE);
				MdriverDetail.setVisibility(View.GONE);
				mTopSearch.setVisibility(View.VISIBLE);
				mLayoutTop.setVisibility(View.GONE);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// Your code when user clicked on Cancel
						dialog.dismiss();
					}
				});
		builder.create();
		builder.show();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.hatchback:
			// do your code

			if (fisttimeclick) {
				cabId = "1";
				hatchBackImage.setImageResource(R.drawable.hatchback_selcted);
				sedanImage.setImageResource(R.drawable.sedan);
				suvImage.setImageResource(R.drawable.suv);
				autoImage.setImageResource(R.drawable.auto);
				otherImage.setImageResource(R.drawable.other);
				fisttimeclick = false;
				sedancheck = true;
				suvCheck = true;
				autoCheck = true;
				otherCheck = true;
			} else {
				showDilog();
			}

			// showDilog();
			break;

		case R.id.sedan:
			// do your code

			if (sedancheck) {
				hatchBackImage.setImageResource(R.drawable.hatchback);
				sedanImage.setImageResource(R.drawable.sedan_select);
				suvImage.setImageResource(R.drawable.suv);
				autoImage.setImageResource(R.drawable.auto);
				otherImage.setImageResource(R.drawable.other);
				cabId = "2";
				fisttimeclick = true;
				sedancheck = false;
				suvCheck = true;
				autoCheck = true;
				otherCheck = true;
			}
			{
				showDilog();
			}
			//
			break;

		case R.id.suv:
			// do your code
			if (suvCheck) {

				cabId = "3";
				hatchBackImage.setImageResource(R.drawable.hatchback);
				sedanImage.setImageResource(R.drawable.sedan);
				suvImage.setImageResource(R.drawable.suv_select);
				autoImage.setImageResource(R.drawable.auto);
				otherImage.setImageResource(R.drawable.other);
				fisttimeclick = true;
				sedancheck = true;
				suvCheck = false;
				autoCheck = true;
				otherCheck = true;
			} else {
				showDilog();
			}
			// /
			break;

		case R.id.auto:
			// do your code
			if (autoCheck) {
				cabId = "4";
				hatchBackImage.setImageResource(R.drawable.hatchback);
				sedanImage.setImageResource(R.drawable.sedan);
				suvImage.setImageResource(R.drawable.suv);
				autoImage.setImageResource(R.drawable.auto_select);
				otherImage.setImageResource(R.drawable.other);
				fisttimeclick = true;
				sedancheck = true;
				suvCheck = true;
				autoCheck = false;
				otherCheck = true;
			} else {
				showDilog();
			}

			break;

		case R.id.more:
			// do your code
			if (otherCheck) {
				cabId = "5";
				hatchBackImage.setImageResource(R.drawable.hatchback);
				sedanImage.setImageResource(R.drawable.sedan);
				suvImage.setImageResource(R.drawable.suv);
				autoImage.setImageResource(R.drawable.auto);
				otherImage.setImageResource(R.drawable.other_select);
				fisttimeclick = true;
				sedancheck = true;
				suvCheck = true;
				autoCheck = true;
				otherCheck = false;

				Fragment fragment = new EventBookingFragment();
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.container_body, fragment);
				fragmentTransaction.commit();
				((Home) getActivity()).mTitle
						.setText(getString(R.string.title_messages_Event_Booking));
				((Home) getActivity()).mToolbar.setVisibility(View.VISIBLE);
			} else {
				showDilog();
			}

			break;

		case R.id.pickLater:
			// do your code
			if (cabId == null) {
				Toast.makeText(getContext(), "Please select a cab",
						Toast.LENGTH_SHORT).show();
			} else {
				cabDetail.setVisibility(View.GONE);
				confirmLinear.setVisibility(View.VISIBLE);
				ispicknow = false;

			}

			break;
		case R.id.pickNow:
			// do your code
			if (cabId == null) {
				Toast.makeText(getContext(), "Please select a cab",
						Toast.LENGTH_SHORT).show();
			} else {

				cabDetail.setVisibility(View.GONE);
				confirmLinear.setVisibility(View.VISIBLE);
				ispicknow = true;
			}

			break;
		case R.id.locationEditText:
			// do your code
			Intent intent = new Intent(getActivity(), SearchActivity.class);
			intent.putExtra("action", "home");
			intent.putExtra("source", locationEdittext.getText().toString());
			startActivityForResult(intent, 1);
			break;

		case R.id.rideEstimae:
			// do your code
			Intent intent1 = new Intent(getActivity(), SearchActivity.class);
			intent1.putExtra("action", "ride");
			intent1.putExtra("source", locationEdittext.getText().toString());
			startActivity(intent1);
			break;

		case R.id.favourite:
			// do your code
			Intent favouriteIntent = new Intent(getActivity(), Favorite.class);

			favouriteIntent.putExtra("source", locationEdittext.getText()
					.toString());
			startActivity(favouriteIntent);
			break;

		case R.id.confirm:
			// do your code
			if (ispicknow) {
				Calendar c = Calendar.getInstance();

				SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
				String formattedDate = df.format(c.getTime());
				bookCab(cabId, formattedDate);
			} else {
				laterPic();
			}

			cabDetail.setVisibility(View.GONE);
			confirmLinear.setVisibility(View.VISIBLE);

			break;

		case R.id.cancel:
			// do your code
			cabDetail.setVisibility(View.VISIBLE);
			confirmLinear.setVisibility(View.GONE);
			break;

		case R.id.imageView1:
			// do your code
			openDrawer();
			break;

		case R.id.cancelRide:
			// do your code
			showCancelDilog();
			break;

		case R.id.back:
			// do your code
			cabDetail.setVisibility(View.VISIBLE);
			// confirmLinear.setVisibility(View.GONE);
			MdriverDetail.setVisibility(View.GONE);
			mTopSearch.setVisibility(View.VISIBLE);
			mLayoutTop.setVisibility(View.GONE);
			break;

		case R.id.myHomeLocation:
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					gpsTracker.getLatitude(), gpsTracker.getLongitude()), 16.0f));
			break;
		case R.id.rateCard:
			showDilog();
			break;

		case R.id.coupons:
			startActivity(new Intent(getActivity(),Coupon.class));
			break;

			
		case R.id.shareDetail:
			String shareBody = "I am riding in a homecab ! Track my ride here. Have you tried Homecab yet? Give it a swirl! Download the app at: https://play.google.com/store/apps/details?id=com.hci";
			
			  Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + "" ) );
	            intentsms.putExtra( "sms_body", shareBody);
	            startActivity( intentsms );
			break;
		case R.id.driverCall:

			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ "8222873873")));
			break;
			
		case R.id.support:

			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ "8222873873")));
			break;
			
		case R.id.rateRide:
         rateRelative.setVisibility(View.VISIBLE);
			break;
			
		case R.id.rateRelative:
			if(rateRelative.getVisibility()==View.VISIBLE)
			{
				  rateRelative.setVisibility(View.GONE);
			}
	       
				break;
		default:
			break;
		}

	}

	public void openDrawer() {
		((Home) getActivity()).drawerLayout
				.openDrawer(((Home) getActivity()).drawerView);
	}

	public void laterPic() {
		final View dialogView = View.inflate(getActivity(),
				R.layout.android_date_time, null);
		final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();
		
		dialogView.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});

		dialogView.findViewById(R.id.date_time_set).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						DatePicker datePicker = (DatePicker) dialogView
								.findViewById(R.id.date_picker);
						TimePicker timePicker = (TimePicker) dialogView
								.findViewById(R.id.time_picker);

						Calendar calendar = new GregorianCalendar(datePicker
								.getYear(), datePicker.getMonth(), datePicker
								.getDayOfMonth(), timePicker.getCurrentHour(),
								timePicker.getCurrentMinute());

						long time = calendar.getTimeInMillis();

						if (calendar == null) {
							Toast.makeText(getContext(),
									"Please select date time",
									Toast.LENGTH_SHORT).show();
						} else {
							SimpleDateFormat df = new SimpleDateFormat(
									"dd/MMM/yyyy  hh:mm a");
							String formattedDate = df.format(time);
							bookCab(cabId, formattedDate);
						}
						alertDialog.dismiss();
					}
				});
		alertDialog.setView(dialogView);
		alertDialog.show();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (1): {
			if (resultCode == Activity.RESULT_OK) {
				String location = data.getStringExtra("location");
				locationEdittext.setText(location);
				String url = "https://maps.googleapis.com/maps/api/geocode/json?";

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
				url = url + address + "&" + sensor;

				// Instantiating DownloadTask to get places from Google
				// Geocoding service
				// in a non-ui thread
				DownloadTask downloadTask = new DownloadTask();

				// Start downloading the geocoding places
				downloadTask.execute(url);
			}
			break;
		}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
		Context mContext;

		public ReverseGeocodingTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			gettingAddressPB.setVisibility(View.VISIBLE);
			locationEdittext.setHint("Getting address..");
			// activity.displayManager.showProgressBar(activity, progressMsg);
		}

		// Finding address using reverse geocoding
		@Override
		protected String doInBackground(LatLng... params) {
			Geocoder geocoder = new Geocoder(mContext);
			double latitude = params[0].latitude;
			double longitude = params[0].longitude;

			List<Address> addresses = null;
			String addressText = "";

			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);

				addressText = String.format(
						"%s, %s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "",address.getAddressLine(1),address.getAddressLine(2), 
						address.getCountryName());
			}

			return addressText;
		}

		@Override
		protected void onPostExecute(String addressText) {
			// Setting the title for the marker.
			// This will be displayed on taping the marker
			gettingAddressPB.setVisibility(View.INVISIBLE);
			locationEdittext.setText(addressText);

			// Placing a marker on the touched position

		}
	}

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

	/** A class, to download Places from Geocoding webservice */
	private class DownloadTask extends AsyncTask<String, Integer, String> {

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
			ParserTask parserTask = new ParserTask();

			// Start parsing the places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
	}

	/** A class to parse the Geocoding Places in non-ui thread */
	class ParserTask extends
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
					map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			}
		}
	}
}