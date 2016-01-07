package com.hcs.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class RideHistoryResposeBeans  implements Parcelable {

	String UserId;
	String UserLat;
	String UserLong;
	String CabType;
	String CabName;
	String BookingStatus;
	String fromAddress;
	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}
	/**
	 * @param fromAddress the fromAddress to set
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return UserId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		UserId = userId;
	}
	/**
	 * @return the userLat
	 */
	public String getUserLat() {
		return UserLat;
	}
	/**
	 * @param userLat the userLat to set
	 */
	public void setUserLat(String userLat) {
		UserLat = userLat;
	}
	/**
	 * @return the userLong
	 */
	public String getUserLong() {
		return UserLong;
	}
	/**
	 * @param userLong the userLong to set
	 */
	public void setUserLong(String userLong) {
		UserLong = userLong;
	}
	/**
	 * @return the cabType
	 */
	public String getCabType() {
		return CabType;
	}
	/**
	 * @param cabType the cabType to set
	 */
	public void setCabType(String cabType) {
		CabType = cabType;
	}
	/**
	 * @return the cabName
	 */
	public String getCabName() {
		return CabName;
	}
	/**
	 * @param cabName the cabName to set
	 */
	public void setCabName(String cabName) {
		CabName = cabName;
	}
	/**
	 * @return the bookingStatus
	 */
	public String getBookingStatus() {
		return BookingStatus;
	}
	/**
	 * @param bookingStatus the bookingStatus to set
	 */
	public void setBookingStatus(String bookingStatus) {
		BookingStatus = bookingStatus;
	}

	  public RideHistoryResposeBeans() {
       
    }
	
	  public RideHistoryResposeBeans(Parcel in) {
          super(); 
          readFromParcel(in);
      }

      public static final Parcelable.Creator<RideHistoryResposeBeans> CREATOR = new Parcelable.Creator<RideHistoryResposeBeans>() {
          public RideHistoryResposeBeans createFromParcel(Parcel in) {
              return new RideHistoryResposeBeans(in);
          }

          public RideHistoryResposeBeans[] newArray(int size) {

              return new RideHistoryResposeBeans[size];
          }

      };

      public void readFromParcel(Parcel in) {
    	  UserId = in.readString();
    	  UserLat = in.readString();
    	  UserLong = in.readString();
    	  BookingStatus = in.readString();
    	  CabName = in.readString();
    	  fromAddress = in.readString();
     	  CabType = in.readString();
       

      }
     
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		  dest.writeString(UserId);
		  dest.writeString(UserLat);
		  dest.writeString(UserLong);
		  dest.writeString(BookingStatus);
		  dest.writeString(CabName);
		  dest.writeString(fromAddress);
		  dest.writeString(CabType);
	}
}
