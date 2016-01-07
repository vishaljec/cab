package com.hcs.activities;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hcs.beans.Registerbean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.services.HTTPAsyncServiceTask;

public class Registration extends ParentScreen implements
		OnFocusChangeListener, ApplicationConstants {
	private Toolbar mToolbar;
	EditText email, password, confirmPassword, fullName, PhoneNumber;
	TextInputLayout til_email, til_password, til_confirmPassword, til_fullName,
			til_PhoneNumber;
	private SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		TextView name = (TextView) mToolbar.findViewById(R.id.edit_text_heder);
		name.setText("Create Accounnt");

		Init();
	}

	public void Init() {
		email = (EditText) findViewById(R.id.edit_text_email);
		password = (EditText) findViewById(R.id.edit_text_pass);
		confirmPassword = (EditText) findViewById(R.id.edit_text_re_pass);
		fullName = (EditText) findViewById(R.id.edit_text_full_name);

		PhoneNumber = (EditText) findViewById(R.id.edit_text_mobile_number);

		til_email = (TextInputLayout) findViewById(R.id.email_til);
		til_password = (TextInputLayout) findViewById(R.id.pass_til);
		til_confirmPassword = (TextInputLayout) findViewById(R.id.rePass_til);
		til_fullName = (TextInputLayout) findViewById(R.id.fullName_til);
		til_PhoneNumber = (TextInputLayout) findViewById(R.id.mobileNumbaer_til);
		sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
		/*
		 * email.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * 
		 * 
		 * if (hasFocus) { // If view having focus.
		 * 
		 * til_email.setErrorEnabled(false); til_email.setError(""); } else { //
		 * If view not having focus. You can validate here
		 * 
		 * if(email.getText().toString().trim().matches("")) {
		 * til_email.setErrorEnabled(true);
		 * til_email.setError(getString(R.string.error_email)); }else
		 * if(!isValidEmaillId(email.getText().toString().trim())) {
		 * til_email.setErrorEnabled(true);
		 * til_email.setError(getString(R.string.error_email_valid)); } } } });
		 * 
		 * 
		 * 
		 * password.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * 
		 * 
		 * if (hasFocus) { // If view having focus.
		 * 
		 * til_password.setErrorEnabled(false); til_password.setError(""); }
		 * else { // If view not having focus. You can validate here
		 * 
		 * if(password.getText().toString().trim().matches("")) {
		 * til_password.setErrorEnabled(true);
		 * til_password.setError(getString(R.string.error_pass)); } } } });
		 * 
		 * 
		 * 
		 * confirmPassword.setOnFocusChangeListener(new OnFocusChangeListener()
		 * {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * 
		 * 
		 * if (hasFocus) { // If view having focus.
		 * 
		 * til_confirmPassword.setErrorEnabled(false);
		 * til_confirmPassword.setError(""); } else { // If view not having
		 * focus. You can validate here
		 * 
		 * if(confirmPassword.getText().toString().trim().matches("")) {
		 * til_confirmPassword.setErrorEnabled(true);
		 * til_confirmPassword.setError(getString(R.string.error_re_pass)); } }
		 * } });
		 * 
		 * 
		 * 
		 * fullName.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * 
		 * 
		 * if (hasFocus) { // If view having focus.
		 * 
		 * til_fullName.setErrorEnabled(false); til_fullName.setError(""); }
		 * else { // If view not having focus. You can validate here
		 * 
		 * if(fullName.getText().toString().trim().matches("")) {
		 * til_fullName.setErrorEnabled(true);
		 * til_fullName.setError(getString(R.string.error_phone_number)); } } }
		 * });
		 * 
		 * 
		 * PhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * public void onFocusChange(View v, boolean hasFocus) {
		 * 
		 * 
		 * if (hasFocus) { // If view having focus.
		 * 
		 * til_PhoneNumber.setErrorEnabled(false); til_PhoneNumber.setError("");
		 * } else { // If view not having focus. You can validate here
		 * 
		 * if(PhoneNumber.getText().toString().trim().matches("")) {
		 * til_PhoneNumber.setErrorEnabled(true);
		 * til_PhoneNumber.setError(getString(R.string.error_phone_number)); } }
		 * } });
		 */
	}

	public void onRegister(View v) {
		if (email.getText().length() <= 0) {
			showToast(Registration.this, R.string.error_email);
		} else if (!isValidEmaillId(email.getText().toString())) {
			showToast(Registration.this, R.string.error_email_valid);
		}

		else if (password.getText().length() <= 0) {
			showToast(Registration.this, R.string.error_pass);
		} else if (confirmPassword.getText().length() <= 0) {
			showToast(Registration.this, R.string.error_confirm_password);
		} else if (!confirmPassword.getText().toString()
				.equals(password.getText().toString())) {
			showToast(Registration.this, R.string.error_re_pass);
		} else if (fullName.getText().length() <= 0) {
			showToast(Registration.this, R.string.error_full_name);
		} else if (PhoneNumber.getText().length() <= 0) {
			showToast(Registration.this, R.string.error_phone_number);
		} else {
			HTTPAsyncServiceTask asyncServiceTask = new HTTPAsyncServiceTask(
					Registration.this, "Creating new account");
			Registerbean params = new Registerbean();
			params.setEmail(email.getText().toString().trim());
			params.setPassword(password.getText().toString().trim());
			params.setFullName(fullName.getText().toString().trim());
			params.setMobileNumber(PhoneNumber.getText().toString().trim());
			asyncServiceTask.execute(params);
		}

	}

	private boolean isValidEmaillId(String email) {

		return Pattern
				.compile(
						"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
								+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
								+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
								+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
								+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
								+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
				.matcher(email).matches();
	}

	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jma.activities.ParentScreen#onResponseReceived(java.lang.Object)
	 */
	@Override
	public void onResponseReceived(Object object,String mName) {
		startActivity(new Intent(Registration.this, Home.class));
		finish();
	}

	@Override
	public void onErrorReceived(Object errDescripton,String mName) {
		// [{"Status":"201","Message":"Record already exist. Please try another record.","UserId":0}]

		try {
			JSONArray array = new JSONArray((String) errDescripton);
			JSONObject jsonObject = null;
		
			SharedPreferences.Editor editor = sharedpreferences.edit();
			try {
				jsonObject = array.getJSONObject(0);

			} catch (Exception e) {

			}

			if (jsonObject.getString("Message")
					.contains("Record already exist")) {
				Toast.makeText(Registration.this,
						jsonObject.getString("Message"), Toast.LENGTH_SHORT)
						.show();
			} else {
				editor.putString(REG_ID, jsonObject.getString("UserId"));
				editor.putString(FULL_NAME, fullName.getText().toString());
				editor.putString(EMAIL, email.getText().toString());
				editor.putString(PHONE_NUMBER, PhoneNumber.getText().toString());
				editor.commit();
				startActivity(new Intent(Registration.this, Home.class));
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
