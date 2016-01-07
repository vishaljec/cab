package com.hcs.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcs.activities.R;
import com.hcs.beans.SearchLocationBean;

public class RecentLocationAdepter extends BaseAdapter {

	public RecentLocationAdepter(Context context,
			List<SearchLocationBean> locationBeans) {
		this.context = context;
		this.locationBeans = locationBeans;
	}

	/**
	 * @Description context for inflate xml layout and create view
	 */
	private Context context;
	/**
	 * @Description list of event code
	 */
	private List<SearchLocationBean> locationBeans;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return locationBeans.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public SearchLocationBean getItem(int position) {
		return locationBeans.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int id) {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// LOGGER.info(className + "=>" + "----getView method start-----");
		try {
			View view = convertView;
			/*
			 * initialize the root view object first time.
			 */
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				view = inflater.inflate(R.layout.row_recent_location, null);
				ViewHolder holder = new ViewHolder();
				holder.adress = (TextView) view.findViewById(R.id.textView1);
				holder.state = (TextView) view.findViewById(R.id.textView2);

				view.setTag(holder);

				holder.adress.setTag(locationBeans.get(position).get_address());

			} else {
				view = convertView;
				((ViewHolder) view.getTag()).adress.setTag(locationBeans.get(
						position).get_address());

			}

			/**
			 * fill data on the list veiw.
			 */
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.adress.setText(locationBeans.get(position).get_address()
					+ "");
			return view;
		} catch (Exception e) {

		}
		// LOGGER.info(className + "=>" + "----getView method end-----");
		return convertView;
	}

	/**
	 * @author dinakarm
	 * @Date May 24, 2014
	 * @Description Holder class to hold list view data.
	 */
	private class ViewHolder {
		public TextView adress, state;

	}

}
