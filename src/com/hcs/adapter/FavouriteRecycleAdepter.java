package com.hcs.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcs.activities.R;
import com.hcs.beans.FavouriteBean;


public class FavouriteRecycleAdepter extends RecyclerView.Adapter<FavouriteRecycleAdepter.CustomViewHolder> {
    private List<FavouriteBean> feedItemList;
    private Context mContext;

    public FavouriteRecycleAdepter(Context context, List<FavouriteBean> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_favourite, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
    	FavouriteBean feedItem = feedItemList.get(i);

       

        //Setting text view title
        customViewHolder.fromAddress.setText(feedItem.getLocationName());
        customViewHolder.status.setText(feedItem.getLocationAddress());
        customViewHolder.imageView.setImageResource(R.drawable.favourites);
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
    
    public void updateList( List<FavouriteBean> feedItemList ) {
		this.feedItemList.addAll(feedItemList);
		notifyDataSetChanged();
		
	}
}
