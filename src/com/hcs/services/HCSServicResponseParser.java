package com.hcs.services;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author amareshy
 * @Date May 21, 2014
 * @Description Common response parser class for parsing all the service
 *              response.
 */
public class HCSServicResponseParser {
	private static Logger LOGGER = Logger
			.getLogger(HCSServicResponseParser.class);
	private static HCSServicResponseParser instance;

	/**
	 * @user amareshy
	 * @Date May 23, 2014
	 * @Description Private Constructor, so that no one can create object from
	 *              out side.
	 */
	private HCSServicResponseParser() {

	}

	/**
	 * @author dinakarm
	 * @Date May 23, 2014
	 * @param responseObject
	 * @return LoginResponseBean
	 * @Description Parse login response data obtained from server.
	 */
	/*public LoginResponseBean parseagentAuthenticationResponse(JSONObject responseObject) {
		LoginResponseBean loginResponseBean = null;
		try {
			if (responseObject != null) {
				loginResponseBean = new LoginResponseBean();
				loginResponseBean.setFirstName(responseObject.optString("FNAME"));
				loginResponseBean.setAgentPk(responseObject.optString("AGENT_PK"));
				loginResponseBean.setActivityTypePk(responseObject.optString("ACTIVITY_TYPE_PK"));
				loginResponseBean.setFollowUpActivityTypePk(responseObject.optString("FOLLOW_UP_ACTIVITY_TYPE_PK"));
				loginResponseBean.setActivityContactTypePk(responseObject.optString("ACTIVITY_CONTACT_TYPE_PK"));


			}
		} catch (Exception e) {
			LOGGER.error("Error occurred in parseLoginResponse() method", e);
		}

		return loginResponseBean;
	}

	*/
	

	/**
	 * @author amareshy
	 * @Date May 23, 2014
	 * @return JMSServicResponseParser
	 * @Description Get Single instance of the class.
	 */
	public static HCSServicResponseParser getInstance() {
		if (instance == null) {
			synchronized (HCSServicResponseParser.class) {
				if (instance == null) {
					instance = new HCSServicResponseParser();
				}
			}
		}
		return instance;
	}


}
