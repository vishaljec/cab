package com.hcs.services;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hcs.activities.Favorite;
import com.hcs.beans.FavouriteBean;
import com.hcs.beans.GetFavouriteBean;
import com.hcs.beans.LoginBean;
import com.hcs.beans.Registerbean;
import com.hcs.constants.ApplicationConstants;
import com.hcs.http.HttpConnectionManager;

/**
 * @author amareshy
 * @Date May 21, 2014
 * @Description Service layer. Service class for handling input and output after
 *              interacting with APIs.
 */
public class HCSServices implements ApplicationConstants {
	private static Logger LOGGER = Logger.getLogger(HCSServices.class);

	/**
	 * @author vishal jangid
	 * @Date May 22, 2014
	 * @param baseUrl
	 * @param loginRequestBean
	 * @return JSONObject
	 * @Description Call agent login web service and return the response data.
	 */
	public String agentRegistrationService(Registerbean loginRequestBean) {
		String responseObject = null;
		try {
			HashMap<String, String> postDataParams = new HashMap<String, String>();
			/*
			 * postDataParams.put(PHONE_NUMBER,
			 * loginRequestBean.getMobileNumber()); postDataParams.put(EMAIL,
			 * loginRequestBean.getEmail());
			 * postDataParams.put(FULL_NAME,loginRequestBean.getFullName());
			 * postDataParams.put(PASSWORD,loginRequestBean.getPassword());
			 */

			JSONObject Detail = new JSONObject();
			try {
				Detail.put(FULL_NAME, loginRequestBean.getFullName());
				Detail.put(EMAIL, loginRequestBean.getEmail());
				Detail.put(PHONE_NUMBER, loginRequestBean.getMobileNumber());
				Detail.put(PASSWORD, loginRequestBean.getPassword());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpConnectionManager httpConnManager = new HttpConnectionManager();
			responseObject = httpConnManager.performPostCall(BASE_URL
					+ REGISTER + (new JSONArray().put(Detail)).toString(),
					postDataParams);
		} catch (Exception e) {
			LOGGER.error(
					"Error occurred in agent authentication service "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
		return responseObject;
	}

	public String login(LoginBean loginRequestBean) {
		String responseObject = null;
		try {
			HashMap<String, String> postDataParams = new HashMap<String, String>();
			/*
			 * postDataParams.put(PHONE_NUMBER,
			 * loginRequestBean.getMobileNumber()); postDataParams.put(EMAIL,
			 * loginRequestBean.getEmail());
			 * postDataParams.put(FULL_NAME,loginRequestBean.getFullName());
			 * postDataParams.put(PASSWORD,loginRequestBean.getPassword());
			 */

			JSONObject Detail = new JSONObject();
			try {

				Detail.put(PHONE_NUMBER, loginRequestBean.getPhonenumber());
				Detail.put(PASSWORD, loginRequestBean.getPassword());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpConnectionManager httpConnManager = new HttpConnectionManager();
			responseObject = httpConnManager.performPostCall(BASE_URL + LOGIN
					+ (new JSONArray().put(Detail)).toString(), postDataParams);
		} catch (Exception e) {
			LOGGER.error(
					"Error occurred in agent authentication service "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
		return responseObject;
	}

	public String addFavourite(FavouriteBean favorite) {
		String responseObject = null;
		try {
			HashMap<String, String> postDataParams = new HashMap<String, String>();

			JSONObject Detail = new JSONObject();
			try {

				Detail.put(USER_ID, favorite.getUserId());
				Detail.put(LOCATION_NAME, favorite.getLocationName());
				Detail.put(LOCATION_ADD, favorite.getLocationAddress());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("url",
					BASE_URL + ADD_FAVOURITE
							+ (new JSONArray().put(Detail)).toString());

			HttpConnectionManager httpConnManager = new HttpConnectionManager();
			responseObject = httpConnManager.performPostCall(BASE_URL
					+ ADD_FAVOURITE + (new JSONArray().put(Detail)).toString(),
					postDataParams);
		} catch (Exception e) {
			LOGGER.error(
					"Error occurred in agent authentication service "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
		return responseObject;
	}

	public String getFavourite(GetFavouriteBean favorite) {
		String responseObject = null;
		try {
			HashMap<String, String> postDataParams = new HashMap<String, String>();

			HttpConnectionManager httpConnManager = new HttpConnectionManager();
			responseObject = httpConnManager.performGETCall(BASE_URL
					+ FETCH_FAVOURITE + favorite.getUserId(), postDataParams);
		} catch (Exception e) {
			LOGGER.error(
					"Error occurred in agent authentication service "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
		return responseObject;
	}
	public String deleteFavourite(GetFavouriteBean favorite) {
		String responseObject = null;
		try {
			HashMap<String, String> postDataParams = new HashMap<String, String>();

			HttpConnectionManager httpConnManager = new HttpConnectionManager();
			responseObject = httpConnManager.performPostCall(BASE_URL
					+ DELETE_FAVOURITE + favorite.getUserId(), postDataParams);
		} catch (Exception e) {
			LOGGER.error(
					"Error occurred in agent authentication service "
							+ e.getMessage(), e);
			e.printStackTrace();
		}
		return responseObject;
	}

}
