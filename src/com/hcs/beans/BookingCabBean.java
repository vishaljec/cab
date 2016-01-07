package com.hcs.beans;

public class BookingCabBean {

public String RegId;
public String bookingDate;
/**
 * @return the bookingDate
 */
public String getBookingDate() {
	return bookingDate;
}
/**
 * @param bookingDate the bookingDate to set
 */
public void setBookingDate(String bookingDate) {
	this.bookingDate = bookingDate;
}
public String getRegId() {
	return RegId;
}
public void setRegId(String regId) {
	RegId = regId;
}
public String getUserLat() {
	return UserLat;
}
public void setUserLat(String userLat) {
	UserLat = userLat;
}
public String getUserLong() {
	return UserLong;
}
public void setUserLong(String userLong) {
	UserLong = userLong;
}
public String getCabId() {
	return CabId;
}
public void setCabId(String cabId) {
	CabId = cabId;
}
public String  UserLat;
public String UserLong;
public String  CabId;

}
