package com.hcs.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hcs.activities.R;
import com.hcs.adapter.RideRecyclerAdapter;
import com.hcs.beans.RideHistoryResposeBeans;

public class Completed extends Fragment{
	ArrayList< RideHistoryResposeBeans> arrayList;
	RideRecyclerAdapter adapter;
	private RecyclerView mRecyclerView;
	private LinearLayout mnotFound;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.completed_ride,
				container, false);


		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
		mnotFound = (LinearLayout) rootView.findViewById(R.id.notFound);
		ArrayList<RideHistoryResposeBeans> arrayListUpcoming= new ArrayList<RideHistoryResposeBeans>();
		
		for(int i=0;i<arrayList.size();i++)
		{
			RideHistoryResposeBeans beans=arrayList.get(i);
			if(beans.getBookingStatus().contains("Completed")){
				arrayListUpcoming.add(beans);
			}
		}
		if(arrayListUpcoming.size()<=0)
		{
			mnotFound.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}else
		{
			mnotFound.setVisibility(View.GONE);
			mRecyclerView.setVisibility(View.VISIBLE);
		}
		adapter = new RideRecyclerAdapter(getContext(), arrayListUpcoming);
		Log.d("size1", arrayList.size() + "");

		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(llm);

		mRecyclerView.setAdapter(adapter);
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		arrayList = new ArrayList<RideHistoryResposeBeans>();
		//arrayList = (getArguments().getParcelableArrayList("arrayList"));
		  arrayList=RideHistoryFragment.arrayList;
		Log.d("size", arrayList.size() + "");

	}
}
