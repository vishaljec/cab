package com.hcs.uimanager;

import org.apache.log4j.Logger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * The Display Manager implementation for the Android Client. Manages 3 types of
 * screen views: - Android Client Screen; - Native Alert Dialogs - Custom Alert
 * Dialogs (Selection Dialogs)
 * 
 * @author Amareshy
 */
public class ApplicationDisplayManager implements DisplayManager {

	/** The tag to be written into log messages */
	private static Logger LOGGER = Logger
			.getLogger(ApplicationDisplayManager.class);

	/**
	 * Default constructor
	 */
	public ApplicationDisplayManager() {
	}

	/**
	 * Hide a screen calling the Activity finish method
	 * 
	 * @param screen
	 *            the Screen to be hidden
	 * @throws Exception
	 *             if the activity related to the encounters any problem
	 * @author Amareshy
	 */
	public void hideScreen(Screen screen) throws Exception {
		Activity activity = (Activity) screen.getUiScreen();
		activity.finish();
	}

	public void showScreen(Screen screen, int screenId) throws Exception {
		showScreen((Activity) screen.getUiScreen(), screenId, null);
	}

	/**
	 * Use the screen's related activity to put the give screen in foreground.
	 * The implementation relies on the Intent mechanism which is peculiar to
	 * Android OS: screens are shown calling the startActivity() methods and
	 * passing the related intent as parameter.
	 * 
	 * @param context
	 * @param screenId
	 *            the Screen related id
	 * @throws Exception
	 *             if the activity related to the screen encounters any problem
	 * @author Amareshy
	 */
	public void showScreen(Context context, int screenId, Bundle extras) throws Exception {
		Intent intent = null;
		switch (screenId) {
	/*	case Controller.FIELD_CALLS_SCREEN_ID: {
			intent = new Intent(context, FieldCallsScreen.class);
			break;
		}
		case Controller.FIELD_CALL_SCREEN_ID: {
			intent = new Intent(context, FieldCallDetailsScreen.class);
			break;
		}
		case Controller.AD_VISIT_SCREEN_ID:
			intent = new Intent(context, AddVisitScreen.class);
			break;
		case Controller.SET_DATE_TIME_SCREEN_ID:
			intent = new Intent(context, SetDateTimeScreen.class);
			break;
		case Controller.CHOOSE_EVENT_CODE_SCREEN_ID:
			intent = new Intent(context, ChooseEventScreen.class);
			break;
		case Controller.FOLLOW_UP_SCREEN_ID:
			intent = new Intent(context, FollowUpScreen.class);
			break;
		case Controller.ADD_PHOTO_SCREEN_ID:
			intent = new Intent(context, AddPhotoScreen.class);
			break;
		case Controller.PHOTOS_SCREEN_ID:
			intent = new Intent(context, PhotosScreen.class);
			break;*/
		default:
			 LOGGER.error("Cannot show unknown screen: " + screenId);
		}
		if (intent != null) {
			if (extras != null) {
				intent.putExtras(extras);
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}

	}

	/**
	 * Hide all the screens from the top of a given screen. For example,
	 * consider a task consisting of the activities: A, B, C, D. If D calls
	 * startActivity() with an Intent that resolves to the component of activity
	 * B, then C and D will be finished and B receive the given Intent,
	 * resulting in the stack now being: A, B.
	 * 
	 * @param context
	 *            the Screen context
	 * @param activityClass
	 *            the activity class name where to stay.
	 * @throws Exception
	 *             if the activity related to the encounters any problem
	 * @author Amareshy
	 */
	@SuppressWarnings("rawtypes")
	public void hideTopScreens(Context context, Class activityClass) throws Exception {
		Intent intent = new Intent(context, activityClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	private ProgressDialog progressDialog;

	@Override
	public void showProgressBar(Screen screen, String message) {
		try {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(((Context) screen),ProgressDialog.THEME_HOLO_LIGHT);
				progressDialog.setCancelable(false);
				progressDialog.setMessage(message);
				progressDialog.show();
			}
		} catch (Exception e) {
			 LOGGER.error("Error occurred whe trying to show progress bar.",
			 e);
		}
	}

	@Override
	public void hideProgressBar() {
		try {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}

		} catch (Exception e) {
		 LOGGER.error("Error occurred whe trying to show progress bar.",
			 e);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void showAlertDialog(final Screen screen, String message, String okButtonLabel, boolean cancelable) {
		try {
			final Activity activity = (Activity) screen.getUiScreen();
			// Create the alert builder.
			AlertDialog.Builder builder = new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT);
			builder.setMessage(message).setCancelable(cancelable).setPositiveButton(okButtonLabel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
			// Create the alert builder.
			AlertDialog alert = builder.create();
			alert.setCancelable(cancelable);
			alert.show();
		} catch (Exception e) {
			LOGGER.error("Error occured inside showAlertDialog ", e);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void showAlertDialog(final Screen screen, String message, String okButtonLabel, boolean cancelable, final int screenId) {
		try {
			final Activity activity = (Activity) screen.getUiScreen();
			// Create the alert builder.
			AlertDialog.Builder builder = new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT) ;
			builder.setMessage(message).setCancelable(cancelable).setPositiveButton(okButtonLabel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					/*switch (screenId) {
					case Controller.LOGIN_SCREEN_ID:
						// (LoginScreen)screen.
						break;

					default:
						break;
					}*/
				}
			});
			// Create the alert builder.
			AlertDialog alert = builder.create();
			alert.setCancelable(cancelable);
			alert.show();
			
		} catch (Exception e) {
			LOGGER.error("Error occured inside showAlertDialog ", e);
		}
	}
}
