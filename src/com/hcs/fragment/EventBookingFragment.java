package com.hcs.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hcs.activities.R;
import com.hcs.beans.EventBookingBean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.http.HttpConnectionManager;
import com.hcs.progressbar.ProgressHUD;

public class EventBookingFragment extends Fragment implements OnClickListener,
		ApplicationConstants {

	EditText fromCityName, toCityName, departureDate, returnDate;
	CardView book;
	private Spinner cabType;
	ArrayList<String> city_name;
	private int selectedStartDay;
	private int selectedStartMonth;
	private int selectedEndDay;
	private int selectedEndMonth;
	private int selectedEndYear;
	private int selectedStartYear;
	private String cabId;
	private DatePickerDialog SecD, firstD;
	 String item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event, container,
				false);

		fromCityName = (EditText) rootView.findViewById(R.id.fromCityName);
		toCityName = (EditText) rootView.findViewById(R.id.toCityName);
		departureDate = (EditText) rootView.findViewById(R.id.departureDate);
		returnDate = (EditText) rootView.findViewById(R.id.returnDate);
		cabType = (Spinner) rootView.findViewById(R.id.cabType);
		book = (CardView) rootView.findViewById(R.id.card_view);

		fromCityName.setOnClickListener(this);
		toCityName.setOnClickListener(this);
		departureDate.setOnClickListener(this);
		returnDate.setOnClickListener(this);
		
		book.setOnClickListener(this);
		city_name = new ArrayList<String>();

		final Calendar c = Calendar.getInstance();
		selectedEndDay = selectedStartDay = c.get(Calendar.DAY_OF_MONTH);
		selectedEndMonth = selectedStartMonth = c.get(Calendar.MONTH);
		selectedEndYear = selectedStartYear = c.get(Calendar.YEAR);
		SecD = new DatePickerDialog(getActivity(), new SecondDate(),
				selectedEndYear, selectedEndMonth, selectedEndDay);

		firstD = new DatePickerDialog(getActivity(), new FirstDate(),
				selectedStartYear, selectedStartMonth, selectedStartDay);
	    
		try {

			JSONArray m_jArry = new JSONArray(loadJSONFromAsset());

			for (int i = 0; i < m_jArry.length(); i++) {
				JSONObject jo_inside = m_jArry.getJSONObject(i);

				String cityName = jo_inside.getString("name");
				city_name.add(cityName);

			}

			Collections.sort(city_name, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return s1.compareToIgnoreCase(s2);
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayList<String> cab = new ArrayList<String>();
		cab.add("Select cab type");
		cab.add("Hatchback");
		cab.add("Seadn");
		cab.add("SUV");
		cab.add("Auto");
		cab.add("More");
		
		 // Creating adapter for spinner
	      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cab);
	      
	      // Drop down layout style - list view with radio button
	      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      
	      // attaching data adapter to spinner
	      cabType.setAdapter(dataAdapter);
	      cabType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 item = parent.getItemAtPosition(position).toString();
				cabId = String.valueOf(position+1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	      
	   
		// Inflate the layout for this fragment
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.fromCityName:
			// do your code
			showDilog(fromCityName);

			break;

		case R.id.toCityName:
			showDilog(toCityName);
			break;
		
		
			
		case R.id.departureDate:

			firstD.show();
			break;

		case R.id.returnDate:
			SecD.show();
			break;

		case R.id.card_view:
			bookNow();
			break;

		default:
			break;
		}
	}

	public void bookNow() {
		if (fromCityName.getText().length() <= 0) {
			showToast(getActivity(), R.string.error_city);
		}

		else if (toCityName.getText().length() <= 0) {
			showToast(getActivity(), R.string.error_city);
		} else if (departureDate.getText().length() <= 0) {
			showToast(getActivity(), R.string.error_dep_date);
		} else if (returnDate.getText().length() <= 0) {
			showToast(getActivity(), R.string.error_ret_date);
		} else if (item.equals("Select cab type")) {
			showToast(getActivity(), R.string.error_cab_type);
		} else {
			BookCabAsynk asyncServiceTask = new BookCabAsynk();
			EventBookingBean eventBookingBean = new EventBookingBean();
			eventBookingBean.setFromCity(fromCityName.getText().toString());
			eventBookingBean.setToCity(toCityName.getText().toString());
			eventBookingBean.setDepartureTime(departureDate.getText().toString());
			eventBookingBean.setReturnTime(returnDate.getText().toString());
			eventBookingBean.setCabType(cabId);
			asyncServiceTask.execute(eventBookingBean);
		}

	}

	public void showToast(Context context, int message) {
		Toast.makeText(context, getResources().getString(message),
				Toast.LENGTH_SHORT).show();
	}

	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = getActivity().getAssets().open("cities.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	public void showDilog(final EditText cityEditText) {

		final CharSequence[] city = city_name.toArray(new String[city_name
				.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				R.style.AppCompatAlertDialogStyle);

		builder.setTitle("Select City");

		builder.setItems(city, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				String selectedText = city[item].toString(); // Selected item in
																// listview
				cityEditText.setText(selectedText);
			}
		});
		builder.show();

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
				EventBookingBean object = (EventBookingBean) params[0];

				HashMap<String, String> postDataParams = new HashMap<String, String>();
				SharedPreferences myPrefs = getActivity().getSharedPreferences(
						"login", getActivity().MODE_PRIVATE);

				JSONObject Detail = new JSONObject();

				try {
					Detail.put(USER_ID, myPrefs.getString(REG_ID, ""));
					Detail.put(SOURCE, object.getFromCity());
					Detail.put(DESTINATION, object.getToCity());
					Detail.put(CAB_TYPE_ID, object.getCabType());
					Detail.put(PICK_UP_TIME, object.getDepartureTime());
					Detail.put(DROP_TIME, object.getReturnTime());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HttpConnectionManager httpConnManager = new HttpConnectionManager();
				responseObject = httpConnManager.performPostCall(
						BASE_URL + EVENT_BOOK_CAB
								+ (new JSONArray().put(Detail)).toString(),
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
				fromCityName.setText("");
				toCityName.setText("");
				departureDate.setText("");
				returnDate .setText("");
				
			
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

	class FirstDate implements DatePickerDialog.OnDateSetListener {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// Doing thing with first Date pick Dialog
          
           
			selectedStartYear = year;
			selectedStartMonth = monthOfYear;
			selectedStartDay = dayOfMonth;

			departureDate.setText((selectedStartMonth + 1) + "/"
					+ selectedStartDay + "/" + selectedStartYear);

			// set minimum date of end datepicker
			Calendar c2 = Calendar.getInstance();
			c2.set(year, monthOfYear, dayOfMonth + 1);

			SecD.getDatePicker().setMinDate(c2.getTime().getTime());
            
		}

	}

	class SecondDate implements DatePickerDialog.OnDateSetListener {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// Doing thing with second Date Picker Dialog
			
			selectedEndYear = year;
			selectedEndMonth = monthOfYear;
			selectedEndDay = dayOfMonth;

			returnDate.setText((selectedEndMonth + 1) + "/" + selectedEndDay
					+ "/" + selectedEndYear);
		}

	}
}