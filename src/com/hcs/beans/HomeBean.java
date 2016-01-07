package com.hcs.beans;

public class HomeBean {

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the isMyLocation
	 */
	public boolean isMyLocation() {
		return isMyLocation;
	}
	/**
	 * @param isMyLocation the isMyLocation to set
	 */
	public void setMyLocation(boolean isMyLocation) {
		this.isMyLocation = isMyLocation;
	}
	String latitude;
	String longitude;
	boolean isMyLocation;
}
