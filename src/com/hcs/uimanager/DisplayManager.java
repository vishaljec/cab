package com.hcs.uimanager;

import android.content.Context;
import android.os.Bundle;

/**
 * This interface includes all basic functions of a DisplayManager
 * implementation that are currently shared between Android of DisplayManager.
 * Interface to manage the display of application screens and alert dialogs. To
 * be implemented on client side. The calls to this class instance are usually
 * made by the DialogController Class.
 * 
 * @author Amareshy
 */
public interface DisplayManager {
	/**
	 * Hide a screen pulling it to the background
	 * 
	 * @param screen
	 *            The screen to be hidden
	 * @author Amareshy
	 */
	public void hideScreen(Screen screen) throws Exception;

	/**
	 * Shows a screen putting it in foreground
	 * 
	 * @param screen
	 *            the Screen to be shown
	 * @param screenId
	 *            the screen id related to the Screen to be shown
	 * @throws Exception
	 *             if an error occurred
	 * @author Amareshy
	 */
	public void showScreen(Screen screen, int screenId) throws Exception;

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
	public void showScreen(Context context, int screenId, Bundle extras) throws Exception;

	/**
	 * Hide all the screens from the top of a given screen.
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
	public void hideTopScreens(Context context, Class activityClass) throws Exception;

	/**
	 * @param screen
	 * @param message
	 * @Description show progress dialog
	 * 
	 */
	public void showProgressBar(Screen screen, String message);

	/**
	 * 
	 * @Description hide progress dialog
	 * 
	 */
	public void hideProgressBar();

	/**
	 * @param screen
	 * @param message
	 * @param okButtonLabel
	 * @param cancelable 
	 * @param screenId
	 * @Description show alert dialog
	 * 
	 */
	public void showAlertDialog(final Screen screen, String message, String okButtonLabel, boolean cancelable, final int screenId);
	/**
	 * @param screen
	 * @param message
	 * @param okButtonLabel
	 * @param cancelable
	 * @Description show alert dialog
	 * 
	 */
	public void showAlertDialog(final Screen screen, String message, String okButtonLabel, boolean cancelable);
}
