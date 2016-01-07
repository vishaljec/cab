package com.hcs.services;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.google.android.gms.plus.model.people.Person.Name;
import com.hcs.activities.ParentScreen;
import com.hcs.beans.FavouriteBean;
import com.hcs.beans.GetFavouriteBean;
import com.hcs.beans.LoginBean;
import com.hcs.beans.Registerbean;
import com.hcs.progressbar.ProgressHUD;

/**
 * @author Amareshy
 * @Date May 21, 2014
 * @Description This class performs web service calling task.
 */
public class HTTPAsyncServiceTask extends AsyncTask<Object, Void, Object> implements OnCancelListener{
	// private static final Logger LOGGER =
	// Logger.getLogger(HTTPAsyncServiceTask.class);
	private ParentScreen activity = null;
	private String progressMsg;
	private ProgressHUD mProgressHUD;    	
	private HCSServices hcsServices;
	private String mName="";
	public HTTPAsyncServiceTask(ParentScreen activity, String progressMsg) {
		this.activity = activity;
		this.progressMsg = progressMsg;
		hcsServices=new HCSServices();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressHUD = ProgressHUD.show(activity,progressMsg, true,false,this);
		//activity.displayManager.showProgressBar(activity, progressMsg);
	}

	@Override
	protected Object doInBackground(Object... params) {
		Object responseObject = null;
		try {
 			

			Object object = params[0];
			if (object instanceof Registerbean) {
				responseObject = hcsServices.agentRegistrationService((Registerbean) object);
 			} else if(object instanceof LoginBean)
 			{
 				responseObject = hcsServices.login((LoginBean) object);
 			} else if(object instanceof FavouriteBean)
 			{   
 				mName="addFavourite";
 				responseObject = hcsServices.addFavourite((FavouriteBean) object);
 			}
 			else if(object instanceof GetFavouriteBean)
 			{
 				mName="getFavourite";
 				responseObject = hcsServices.getFavourite((GetFavouriteBean) object);
 			}
		} catch (Exception e) {
			// LOGGER.error("Error occured inside doInBackground ", e);
		} finally {
		
		}

		return responseObject;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		mProgressHUD.dismiss();
		if (result == null || result instanceof String) {
			this.activity.onErrorReceived( result,mName);
		} else {
			this.activity.onResponseReceived(result,mName);
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		mProgressHUD.dismiss();
	}

}
