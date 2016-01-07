package com.hcs.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hcs.beans.Registerbean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.services.HTTPAsyncServiceTask;

public class Profile extends ParentScreen implements OnClickListener,ApplicationConstants{
 private TextView emailTextView,nameTextView,mnTextView;
 SharedPreferences myPrefs ;
 private RelativeLayout save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile);
		
		RelativeLayout Change_password=(RelativeLayout)  findViewById(R.id.Change_Passwordlayout);
		emailTextView=(TextView) findViewById(R.id.textView1);
		nameTextView=(TextView) findViewById(R.id.name);
		mnTextView=(TextView) findViewById(R.id.mn);
		save=(RelativeLayout) findViewById(R.id.save);
		ImageView imageView=(ImageView) findViewById(R.id.imageView3);
		
		Change_password.setOnClickListener(this);
		save.setOnClickListener(this);
		imageView.setOnClickListener(this);
		
		 myPrefs = getSharedPreferences("login",
				MODE_PRIVATE);
		emailTextView.setText(myPrefs.getString(EMAIL, ""));
		nameTextView.setText(myPrefs.getString(FULL_NAME,""));
		mnTextView.setText(myPrefs.getString(PHONE_NUMBER,""));
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	    switch (v.getId()) {

	 
	    case R.id.Change_Passwordlayout:
	        // do your code
	    	Intent intent=new Intent(Profile.this,Changepassword.class);
	    	startActivity(intent);
	        break;
	    case R.id.imageView3:
	        // do your code
	    	finish();
	        break;
	    case R.id.save:
	        // do your code
	    	HTTPAsyncServiceTask asyncServiceTask = new HTTPAsyncServiceTask(Profile.this, "Updating profile ");
			Registerbean params = new Registerbean();
			params.setEmail(emailTextView.getText().toString().trim());
			params.setPassword(myPrefs.getString(PASSWORD, ""));
			params.setFullName(nameTextView.getText().toString().trim());
			params.setMobileNumber(mnTextView.getText().toString().trim());
			asyncServiceTask.execute(params);
	        break;
	    default:
	        break;
	    }
	}
	
	public void logout(View v)
	{
		SharedPreferences myPrefs = getSharedPreferences("login",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = myPrefs.edit();
		editor.clear();
		editor.commit();
		Intent intent=new Intent(Profile.this,Login.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		
		
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jma.activities.ParentScreen#onResponseReceived(java.lang.Object)
	 */
	@Override
	public void onResponseReceived(Object object,String mName) {
		startActivity(new Intent(Profile.this, Home.class));
		finish();
	}

	@Override
	public void onErrorReceived(Object errDescripton,String mName) {
		// [{"Status":"201","Message":"Record already exist. Please try another record.","UserId":0}]

		try {
			JSONArray array = new JSONArray((String) errDescripton);
			JSONObject jsonObject = null;
		
			SharedPreferences.Editor editor = myPrefs.edit();
			try {
				jsonObject = array.getJSONObject(0);

			} catch (Exception e) {

			}

			if (jsonObject.getString("Message")
					.contains("Record already exist")) {
				Toast.makeText(Profile.this,
						jsonObject.getString("Message"), Toast.LENGTH_SHORT)
						.show();
			} else {
				editor.putString(REG_ID, jsonObject.getString("UserId"));
				editor.putString(FULL_NAME, nameTextView.getText().toString());
				editor.putString(EMAIL, emailTextView.getText().toString());
			//	editor.putString(PHONE_NUMBER, PhoneNumber.getText().toString());
				editor.commit();
				
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}
}
