package com.hcs.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hcs.adapter.FavouriteRecycleAdepter;
import com.hcs.beans.FavouriteBean;
import com.hcs.beans.GetFavouriteBean;
import com.hcs.services.HTTPAsyncServiceTask;

public class Favorite extends ParentScreen implements
		android.view.View.OnClickListener {
	private RecyclerView mFavourit;
	private ArrayList<FavouriteBean> favouriteArrayList;
	private ImageView mAddFavourite;
	private String mAddress;
	private TextView mAdd;
	private EditText name;
	private Dialog dialog;
	private FavouriteRecycleAdepter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);

		favouriteArrayList = new ArrayList<FavouriteBean>();
		mFavourit = (RecyclerView) findViewById(R.id.rv);
		mAddFavourite = (ImageView) findViewById(R.id.addFavouriteImage);
		mAdapter = new FavouriteRecycleAdepter(Favorite.this,
				favouriteArrayList);
		ImageView back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mFavourit.setLayoutManager(llm);
		mFavourit.setAdapter(mAdapter);
		mAdd = (TextView) findViewById(R.id.address);
		mAddress = getIntent().getExtras().getString("source");

		mAddFavourite.setOnClickListener(this);

		mAdd.setText(mAddress);
		
		HTTPAsyncServiceTask asyncServiceTask = new HTTPAsyncServiceTask(
				Favorite.this, "Getting favourite..");
		GetFavouriteBean favouriteBean = new GetFavouriteBean();
		favouriteBean.setUserId(getUserId());
	

		asyncServiceTask.execute(favouriteBean);
	}

	public void showDilog() {

		dialog = new Dialog(Favorite.this);
		dialog.setContentView(R.layout.row_add_favourite);
		dialog.setTitle("Add Favourite");
		name = (EditText) dialog.findViewById(R.id.name);
		final RelativeLayout ok = (RelativeLayout) dialog.findViewById(R.id.ok);
		final RelativeLayout cancel = (RelativeLayout) dialog
				.findViewById(R.id.cancel);

		ok.setOnClickListener(this);

		cancel.setOnClickListener(this);

		dialog.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.addFavouriteImage:

			showDilog();
			break;
		case R.id.ok:
			if (name.getText().toString().trim().length() <= 0) {
				Toast.makeText(Favorite.this, "Please enter favourite name",
						Toast.LENGTH_SHORT).show();
			} else {

				HTTPAsyncServiceTask asyncServiceTask = new HTTPAsyncServiceTask(
						Favorite.this, "Adding favourite");
				FavouriteBean favouriteBean = new FavouriteBean();
				favouriteBean.setUserId(getUserId());
				favouriteBean.setLocationAddress(mAddress);
				favouriteBean.setLocationName(name.getText().toString());

				asyncServiceTask.execute(favouriteBean);
				dialog.dismiss();

			}
			break;

		case R.id.cancel:
			dialog.dismiss();
			break;

		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onErrorReceived(Object object,String mName) {
		
		if(mName.equals("addFavourite")){
			FavouriteBean bean = new FavouriteBean();
			bean.setLocationAddress(mAddress);
			bean.setLocationName(name.getText().toString());
			favouriteArrayList.add(bean);
			mAdapter.notifyDataSetChanged();
			try {
				JSONArray array = new JSONArray((String) object);

				try {
					JSONObject jsonObject = array.getJSONObject(0);

				} catch (Exception e) {

				}

				if (array.length() == 0) {
					Toast.makeText(Favorite.this, "Invalid mobile number/password",
							Toast.LENGTH_SHORT).show();
				} else {

				}
			} catch (Exception e) {

			}

		}else if(mName.equals("getFavourite"))
		{
			try {
				JSONObject jsonObject=new JSONObject((String) object);
				JSONArray responseArray=jsonObject.getJSONArray("history");
				for (int i = 0; i < responseArray.length(); i++) {
					JSONObject jsonObject2=responseArray.getJSONObject(i);
					FavouriteBean bean=new FavouriteBean();
					bean.setUserId(getUserId());
					bean.setLocationAddress(jsonObject2.optString("LocationAddress"));
					bean.setLocationName(jsonObject2.optString("LocationName"));
					bean.setFavId(jsonObject2.optString("FevId"));
					favouriteArrayList.add(bean);
					mAdapter.notifyDataSetChanged();
				} 
						
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

	@Override
	public Object getUiScreen() {
		// TODO Auto-generated method stub
		return null;
	}
}
