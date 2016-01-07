package com.hcs.activities;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hcs.constants.ApplicationConstants;
import com.hcs.uimanager.Screen;

/**
 * @author Amareshy
 * @Date May 21, 2014
 * @Description The activity class represents the view which works as the master
 *              view of all the activities view.
 */
@SuppressLint("NewApi")
public abstract class ParentScreen extends AppCompatActivity implements Screen,ApplicationConstants {
//	private static final Logger LOGGER = Logger.getLogger(ParentScreen.class);
	/**
	 * @Description button for header in left
	 */
	private Button leftButton;
	/**
	 * @Description test for left header button
	 */
	private Button leftButtonText;
	/**
	 * @Description right button for header
	 */
	private Button rightButton;
	/**
	 * @Description text view for right button text
	 */
	private TextView titleTextView;
	/**
	 * @Description status while this app is running or not
	 */
	protected static boolean isAppRunning;

	/**
	 * @Description application controller
	 */
	/*public Controller controller = ApplicationController.getInstance();
	*//**
	 * display manager for whole application
	 *//*
	public DisplayManager displayManager = controller.getDisplayManager();
	*//**
	 * @Description shared preference of application  
	 *//*
	public ApplicationPreference preferences = ApplicationPreference.getInstance(this);
	private LoginRequestBean loginRequestBean;*/
	/**
	 * @Description initialize logger object  
	 */
	private static Logger LOGGER = Logger
			.getLogger(ParentScreen.class);
	/**
	 * @Description get class name  
	 */
	private String className = this.getClass().getSimpleName();
	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param object
	 * @Returns void
	 * @Description Calls when successful response from server.
	 */
	public void onResponseReceived(Object object,String name) {

	}

	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param errDescripton
	 * @Returns void
	 * @Description Calls when error response from server.
	 */
	public void onErrorReceived(Object errDescripton,String name) {
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		LOGGER.info(className + "=>" + "----onCreate method start-----");
		// Application Header controls
	
		LOGGER.info(className + "=>" + "----onCreate method end-----");
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		LOGGER.info(className + "=>" + "----onResume method start-----");
		isAppRunning = true;
		super.onResume();
		LOGGER.info(className + "=>" + "----onResume method end-----");
	}

	/***************************************** Application Header Controls ********************************************/

	/**
	 * @author dinakarm
	 * @Date May 22, 2014
	 * @param leftButtonText
	 * @param titleText
	 * @param rightButtonText
	 * @Returns void
	 * @Description Common header for all the screen. Set header text.
	 */
	protected void setHeaderTextResources(String leftButtonText, String titleText, String rightButtonText) {
		LOGGER.info(className + "=>" + "----setHeaderTextResources method start-----");
		if (titleText != null && titleText.length() > 0) {
			titleTextView.setText(titleText);
		}
		if (leftButtonText != null && leftButtonText.length() > 0) {
			this.leftButtonText.setText(leftButtonText);
		}
		if (rightButtonText != null && rightButtonText.length() > 0) {
			rightButton.setText(rightButtonText);
		}
		LOGGER.info(className + "=>" + "----setHeaderTextResources method end-----");
	}

	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param leftButtonImgDrawable
	 * @param rightButtonImgDrawable
	 * @Returns void
	 * @Description Common header for all the screen. Set header background
	 *              Images.
	 */
	@SuppressWarnings("deprecation")
	protected void setHeaderImageResources(Drawable leftButtonImgDrawable, Drawable rightButtonImgDrawable) {
		LOGGER.info(className + "=>" + "----setHeaderImageResources method start-----");
		if (leftButtonImgDrawable != null) {
			leftButton.setBackgroundDrawable(leftButtonImgDrawable);
		}
		if (rightButtonImgDrawable != null) {
			rightButton.setBackgroundDrawable(rightButtonImgDrawable);
		}
		LOGGER.info(className + "=>" + "----setHeaderImageResources method end-----");
	}

	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param view
	 * @Returns void
	 * @Description Perform left button click of the header.
	 */
	public void onClickLeftButton(View view) {
	}

	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param view
	 * @Returns void
	 * @Description Perform right button click of the header.
	 */
	public void onClickRightButton(View view) {

	}

	/***************************************** End of Application Header Controls ********************************************/

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		LOGGER.info(className + "=>" + "----onPause method start-----");
		isAppRunning = false;
		super.onPause();
		LOGGER.info(className + "=>" + "----onPause method end-----");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		LOGGER.info(className + "=>" + "----onDestroy method start-----");
		super.onDestroy();
		if (isFinishing() && !isAppRunning) {
			//TaskScheduler.stopScheduler((Context) getUiScreen());
		}
		LOGGER.info(className + "=>" + "----onDestroy method end-----");
	}
	
	public void showToast(Context context,int message)
	{
		Toast.makeText(context, getResources().getString(message), Toast.LENGTH_SHORT).show();
	}
	
	public String getUserId()
	{
		SharedPreferences   sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
		return sharedpreferences.getString(REG_ID, "");
	}

}
