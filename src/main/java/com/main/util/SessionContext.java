package com.main.util;

import java.util.HashMap;
import java.util.Map;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class SessionContext {
	private static Integer customerId;
	
	/**Getting accesstoken by providing valid clientId and secret
	 * @return
	 */
	public static String getAccessToken(){
		String accessToken = "";
		Map<String, String > map = new HashMap<String, String>();
		map.put("mode", "sandbox");

		String clientID = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret="ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		if(accessToken.equals("") || accessToken==""){
		try {
			accessToken = new OAuthTokenCredential(clientID, clientSecret,map).getAccessToken();
			System.out.println("Access Token : " + accessToken);
			return accessToken;
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the OAuthentication :" + e.getMessage());
			return accessToken;
		}
		}else return accessToken;
	}

	public static Map<String, String> getSDKConfig(){
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		
		return sdkConfig;
	}
	
	public static APIContext getAPIContext(){
		APIContext apiContext = new APIContext(getAccessToken());
		apiContext.setConfigurationMap(getSDKConfig());
		return apiContext;
	}
	
	public static int getCustomerId() {
		return customerId;
	}

	public static void setCustomerId(int customerId2) {
		if(customerId == null  ){
			customerId = customerId2;
		}
	}
	
}
