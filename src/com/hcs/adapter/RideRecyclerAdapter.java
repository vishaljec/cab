package com.hcs.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcs.activities.R;
import com.hcs.beans.RideHistoryResposeBeans;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.CustomViewHolder> {
    private List<RideHistoryResposeBeans> feedItemList;
    private Context mContext;

    public RideRecyclerAdapter(Context context, List<RideHistoryResposeBeans> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ride_history, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
    	RideHistoryResposeBeans feedItem = feedItemList.get(i);

       

        //Setting text view title
        customViewHolder.fromAddress.setText(feedItem.getFromAddress());
        customViewHolder.status.setText(feedItem.getBookingStatus());
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
    
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView fromAddress,status;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.fromAddress = (TextView) view.findViewById(R.id.fromAddress);
            this.status = (TextView) view.findViewById(R.id.status);
        }
    }
}
