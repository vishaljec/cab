package com.hcs.constants;

/**
 * @author Amareshy
 * @Date May 21, 2014
 * @Description All the application constants goes here.
 */
public interface ApplicationConstants {

	/**
	 * Web Service sub URLs
	 */
	public static final String BASE_URL = "http://services.thetraveloholic.com/";
	public static final String REGISTER = "Register?reg=";
	public static final String LOGIN = "Login?login=";
	public static final String BOOK_CAB = "CabBooking?bookingDetails=";
	public static final String EVENT_BOOK_CAB = "EventBooking?bookingDetails=";
	public static final String UPDATE_PROFILE = "UpdateProfile?reg=";
	public static final String RIDE_HISTORY = "getRideHistory?";
	public static final String ADD_FAVOURITE = "AddfavouritesDetails?favouritesDetails=";
	public static final String DELETE_FAVOURITE = "DeleteFev/";
	public static final String FETCH_FAVOURITE = "GetfavouritesDetails/";

	public static final int CONNECTION_TIME_OUT = 30 * 1000;
	public static final int READ_TIME_OUT = 30 * 1000;
	public static final String RESPONSE_STATUS_CODE = "responseStatusCode";
	public static final String RESPONSE_DATA = "responseData";

	/**
	 * Login request constants.
	 */
	public static final String PHONE_NUMBER = "Mobile";
	public static final String EMAIL = "Email";
	public static final String FULL_NAME = "Name";
	public static final String PASSWORD = "Password";
	public static final String SUCCESS = "success";
	public static final String MSG = "message";
	public static final String ZERO = "0";

	public static final String REG_ID = "RegId";
	public static final String USER_LAT = "UserLat";
	public static final String USER_LONG = "UserLong";
	public static final String CAB_ID = "CabId";
	public static final String BOOKING_DATE = "BookingDate";

	public static final String USER_ID = "UserId";
	public static final String SOURCE = "Source";
	public static final String DESTINATION = "Destination";
	public static final String CAB_TYPE_ID = "CabTypeId";
	public static final String PICK_UP_TIME = "PickupTime";
	public static final String DROP_TIME = "DropTime";

	public static final String USERID = "userid";
	public static final String LOCATION_NAME = "LocationName";
	public static final String LOCATION_ADD = "LocationAddress";
}
