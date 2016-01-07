package com.hcs.activities;

import com.hcs.constants.ApplicationConstants;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class Splash extends ParentScreen implements ApplicationConstants {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splas);

		checkGps();

	}

	public void checkGps() {
		LocationManager lm = (LocationManager) Splash.this
				.getSystemService(Context.LOCATION_SERVICE);
		boolean gps_enabled = false;
		boolean network_enabled = false;

		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}

		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		if (!gps_enabled && !network_enabled) {
			// notify user
			AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this,
					R.style.AppCompatAlertDialogStyle);

			builder.setTitle("Use location?");
			builder.setMessage("Use GPS, Wi-fi and mobile network for location");
			builder.setPositiveButton("OK", null);
			builder.setNegativeButton("Cancel", null);
			builder.show();

			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							// TODO Auto-generated method stub
							Intent myIntent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							Splash.this.startActivity(myIntent);
							// get gps
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface paramDialogInterface,
								int paramInt) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			builder.show();
		} else {
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when
				 * you want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					SharedPreferences myPrefs = getSharedPreferences("login",
							MODE_PRIVATE);

					if (myPrefs.getString(REG_ID, null) != null) {
						Intent i = new Intent(Splash.this, Home.class);
						startActivity(i);

						// close this activity
						finish();
					} else {
						Intent i = new Intent(Splash.this, Login.class);
						startActivity(i);

						// close this activity
						finish();
					}

				}
			}, SPLASH_TIME_OUT);
		}
	}

	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}
}
