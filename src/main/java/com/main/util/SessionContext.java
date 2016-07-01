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

		/*Facilitator's credentials*/
		String clientID = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret="ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		
		/*Braintree*/
//		String clientID = "AUs7Rbr2uJhjBwD0hJPWwCpQLiNurOZxjA3ZRzErAXZUgc-Gh51U3RDDb3mJ6VAxt7pmrSuB9Wy4xgz1";
//		String clientSecret="EFeaXkAmZaXEsNoNC4m03UcRhdm3BGeeo-Mt2X_6phg9LHc_B95xuq7QVHjceo9vxRZvuZkGmUypoRIG";
		
		/*Ek-payment*/
//		String clientID = "Adsrt7W6gH9ohkVi4T-wZ2laykL6rAGouJG__QtwBbfqHMKtfd3r--fsYG1eUNhcP3snqTUEZbRFISga";
//		String clientSecret="EBsdqxhtk_Lfz2dzFl-HW_puAs0EDx5zQy0tGt7LvJNXzMcOazZ8pcTbLAsvE64Ds4AY5XKlij-Jcvn0";
		if(accessToken.equals("") || accessToken==""){
		try {
			accessToken = new OAuthTokenCredential(clientID, clientSecret,getSDKConfig()).getAccessToken();
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
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		
		APIContext apiContext = new APIContext(getAccessToken());
		apiContext.setConfigurationMap(sdkConfig);
		return apiContext;
	}
	
	public static int getCustomerId() {
		if(customerId == null){
			/*Just to rectify the Null pointer exception error when the application restarts*/
			customerId=1;
		}
		return customerId;
	}

	public static void setCustomerId(int customerId2) {
		if(customerId == null  ){
			customerId = customerId2;
		}
	}
	
}
