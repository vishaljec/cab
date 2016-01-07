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

public class AllRides extends Fragment {
	ArrayList<RideHistoryResposeBeans> arrayList;
	RideRecyclerAdapter adapter;
	private RecyclerView mRecyclerView;
	private LinearLayout mnotFound;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.all_ride, container, false);

		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
		
		mnotFound = (LinearLayout) rootView.findViewById(R.id.notFound);
		adapter = new RideRecyclerAdapter(getContext(), arrayList);
		Log.d("size1", arrayList.size() + "");
		if(arrayList.size()<=0)
		{
			mnotFound.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}else
		{
			mnotFound.setVisibility(View.GONE);
			mRecyclerView.setVisibility(View.VISIBLE);
		}
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
