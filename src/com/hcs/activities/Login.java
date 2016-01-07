package com.hcs.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person.Name;
import com.hcs.beans.LoginBean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.services.HTTPAsyncServiceTask;

public class Login extends ParentScreen implements ApplicationConstants {
	private Toolbar mToolbar;
	private EditText edit_text_email, edit_text_pass;
	private SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		TextView name = (TextView) mToolbar.findViewById(R.id.edit_text_heder);
		name.setText("Login");
		init();
	}

	public void init() {
		TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.email_til);
		TextView registerNow = (TextView) findViewById(R.id.textView_register);
		edit_text_email = (EditText) findViewById(R.id.edit_text_mobile);
		edit_text_pass = (EditText) findViewById(R.id.edit_text_pass);

		sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

		registerNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, Registration.class);
				startActivity(intent);
			}
		});
		/*
		 * inputLayout.setErrorEnabled(true);
		 * inputLayout.setError(getString(R.string.error_email));
		 */
	}

	public void signIn(View v) {
		if (edit_text_email.getText().length() <= 0) {
			showToast(Login.this, R.string.error_email);
		}

		else if (edit_text_pass.getText().length() <= 0) {
			showToast(Login.this, R.string.error_pass);
		} else {
			HTTPAsyncServiceTask asyncServiceTask = new HTTPAsyncServiceTask(
					Login.this, "Login");
			LoginBean params = new LoginBean();
			params.setPhonenumber(edit_text_email.getText().toString().trim());
			params.setPassword(edit_text_pass.getText().toString().trim());

			asyncServiceTask.execute(params);

		}

	}

	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onResponseReceived(Object object,String mName) {
		if (((JSONArray) object).length() == 0) {
			Toast.makeText(Login.this, "Invalid mobile number/password",
					Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent(Login.this, Home.class));
			finish();
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void onErrorReceived(Object object,String mName) {
		try {
			JSONArray array = new JSONArray((String) object);
			// [{"RegId":12,"Name":"1","Email":"1","Mobile":"1","Password":"1"}]
			SharedPreferences.Editor editor = sharedpreferences.edit();
			try {
				JSONObject jsonObject = array.getJSONObject(0);
				editor.putString(REG_ID, jsonObject.getString(REG_ID));
				editor.putString(FULL_NAME, jsonObject.getString(FULL_NAME));
				editor.putString(EMAIL, jsonObject.getString(EMAIL));
				editor.putString(PHONE_NUMBER, jsonObject.getString(PHONE_NUMBER));
				editor.commit();
			} catch (Exception e) {

			}

			if (array.length() == 0) {
				Toast.makeText(Login.this, "Invalid mobile number/password",
						Toast.LENGTH_SHORT).show();
			} else {
				 startActivity(new Intent(Login.this, Home.class));
			 finish();
			}
		} catch (Exception e) {

		}

	}
}
