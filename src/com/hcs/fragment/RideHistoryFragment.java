package com.hcs.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hcs.activities.R;
import com.hcs.beans.BookingCabBean;
import com.hcs.beans.RideHistoryRequest;
import com.hcs.beans.RideHistoryResposeBeans;
import com.hcs.constants.ApplicationConstants;
import com.hcs.http.HttpConnectionManager;
import com.hcs.progressbar.ProgressHUD;

public class RideHistoryFragment extends Fragment implements
		ApplicationConstants {

	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	SharedPreferences myPrefs;
	public static ArrayList<RideHistoryResposeBeans> arrayList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ride_history,
				container, false);

		viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

		tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

		arrayList = new ArrayList<RideHistoryResposeBeans>();
		myPrefs = getActivity().getSharedPreferences("login",
				getActivity().MODE_PRIVATE);
		RideHistoryRequest params = new RideHistoryRequest();
		params.setUserId(myPrefs.getString(REG_ID, ""));
		RideHistoryAsyn asyn = new RideHistoryAsyn();
		asyn.execute(params);

		// Inflate the layout for this fragment
		return rootView;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
		adapter.addFragment(newInstance(), "ALL RIDES");
		adapter.addFragment(newInstanceUpComing(), "UPCOMING");
		adapter.addFragment(newInstanceCompleted(), "COMPLETED");
		viewPager.setAdapter(adapter);

	}

	public AllRides newInstance() {
		AllRides f = new AllRides();
		/*
		 * Bundle bdl = new Bundle(2);
		 * 
		 * bdl.putParcelableArrayList("arrayList", arrayList);
		 * 
		 * f.setArguments(bdl);
		 */
		return f;
	}

	public UpComing newInstanceUpComing() {
		UpComing f = new UpComing();
		/*
		 * Bundle bdl = new Bundle(2);
		 * 
		 * bdl.putParcelableArrayList("arrayList", arrayList);
		 * 
		 * f.setArguments(bdl);
		 */
		return f;
	}

	public Completed newInstanceCompleted() {
		Completed f = new Completed();
		/*
		 * Bundle bdl = new Bundle(2);
		 * 
		 * bdl.putParcelableArrayList("arrayList", arrayList);
		 * 
		 * f.setArguments(bdl);
		 */
		return f;
	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
		private final List<String> mFragmentTitleList = new ArrayList<String>();

		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
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

	public class RideHistoryAsyn extends AsyncTask<Object, Void, Object>
			implements OnCancelListener {
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
				RideHistoryRequest object = (RideHistoryRequest) params[0];

				HashMap<String, String> postDataParams = new HashMap<String, String>();

				JSONObject Detail = new JSONObject();
				try {
					Detail.put(USERID, object.getUserId());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String url = BASE_URL + RIDE_HISTORY + USERID + "="
						+ myPrefs.getString(REG_ID, "");

				HttpConnectionManager httpConnManager = new HttpConnectionManager();
				responseObject = httpConnManager.performGETCall(url,
						postDataParams);
				Log.d("url", url);
			} catch (Exception e) {
				// LOGGER.error("Error occured inside doInBackground ", e);
			}

			return responseObject;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			arrayList.clear();
			String string = (String) result;

			try {
				JSONObject jsonObject = new JSONObject(string);
				JSONArray responseArray = jsonObject.getJSONArray("response");
				JSONArray historyeArray = jsonObject.getJSONArray("history");
				JSONObject object = responseArray.getJSONObject(0);
				Geocoder geocoder = new Geocoder(getContext());
				String message = object.optString("Message");
				if (message.equals("Record found")) {
					arrayList.clear();
					for (int i = 0; i < historyeArray.length(); i++) {
						JSONObject jsonObject2 = historyeArray.getJSONObject(i);

						RideHistoryResposeBeans beans = new RideHistoryResposeBeans();
						beans.setBookingStatus(jsonObject2
								.optString("BookingStatus"));
						beans.setCabName(jsonObject2.optString("CabName"));
						beans.setCabType(jsonObject2.optString("CabType"));
						beans.setUserId(jsonObject2.optString("UserId"));
						beans.setUserLat(jsonObject2.optString("UserLat"));
						beans.setUserLong(jsonObject2.optString("UserLong"));

						List<Address> addresses = null;
						String addressText = "";

						try {
							addresses = geocoder.getFromLocation(Double
									.parseDouble(jsonObject2
											.optString("UserLat")), Double
									.parseDouble(jsonObject2
											.optString("UserLong")), 1);
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (addresses != null && addresses.size() > 0) {
							Address address = addresses.get(0);

							addressText = String
									.format("%s, %s, %s",
											address.getMaxAddressLineIndex() > 0 ? address
													.getAddressLine(0) : "",
											address.getLocality(), address
													.getCountryName());
						}

						beans.setFromAddress(addressText);
						arrayList.add(beans);
					}
				}

			} catch (Exception e) {

			}
			setupViewPager(viewPager);
			tabLayout.setupWithViewPager(viewPager);
			mProgressHUD.dismiss();
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub

		}
	}

}
