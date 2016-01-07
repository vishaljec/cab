package com.hcs.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcs.activities.R;
import com.hcs.beans.NavDrawerItem;
import com.hcs.constants.ApplicationConstants;

/**
 * Created by vishal jangid on 12-03-2015.
 */
public class NavigationDrawerAdapter extends
		RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> implements
		ApplicationConstants {
	List<NavDrawerItem> data = Collections.emptyList();
	private LayoutInflater inflater;
	private Context context;

	public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

	public void delete(int position) {
		data.remove(position);
		notifyItemRemoved(position);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		NavDrawerItem current = data.get(position);
		holder.title.setText(current.getTitle());
		if (position == 0) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_home);
		} else if (position == 1)

		{
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			SharedPreferences sharedpreferences = sharedpreferences = context
					.getSharedPreferences("login", Context.MODE_PRIVATE);
			holder.name.setText(sharedpreferences.getString(FULL_NAME, ""));
			holder.mobileno.setText(sharedpreferences.getString(PHONE_NUMBER,
					""));
			holder.title.setVisibility(View.VISIBLE);
			holder.title.setText(sharedpreferences.getString(FULL_NAME, ""));
			holder.icon.setImageResource(R.drawable.profile);

		} else if (position == 2) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.payment);
		} else if (position == 3) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_correct);
		} else if (position == 4) {

			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_about);
		} else if (position == 5) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_ride_history);
		} else if (position == 6) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_event_booking);
		} else if (position == 7) {
			holder.edit.setVisibility(View.INVISIBLE);
			holder.name.setVisibility(View.INVISIBLE);
			holder.mobileno.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.icon.setImageResource(R.drawable.ic_call);
		}
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView title, edit, name, mobileno;
		ImageView icon;

		public MyViewHolder(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.title);
			edit = (TextView) itemView.findViewById(R.id.textView1);
			name = (TextView) itemView.findViewById(R.id.textView2);
			mobileno = (TextView) itemView.findViewById(R.id.textView3);
			icon = (ImageView) itemView.findViewById(R.id.imageView1);
		}
	}
}
