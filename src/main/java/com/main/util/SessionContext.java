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

		/*testAPI's credentials*/
		String clientID = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret="ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		
		/*pranish.stha03*/
//		String clientID =  "AYnL5CYn1bAKIIT-DmMX3Tr38cM4ep8nljvcB-k_ByEDL_PMmfnr1lgwNyX1DQZOe7U7jZ6Itkzw0Tx9";
//		String clientSecret="EKhNwH5qlyXrom72HEb9VAI5bxx0g8RRTEhmTmal8C5c3huBiHxA_JpczuCBOSBYyA57GFk-Parnxhl_";
		
//		String clientID = "AYnFTc-GiQj7woam2EdCMZvasffdjMbK8X7Wa0X1F5LxFxBriAQrlljnXXMIdDWXshV4xkqP7pDsxZEF";
//		String clientSecret = "EI0iIanE4-bqjDPPRhe_cpKz5W1-qRgX7kl995wiAvkG_zLhLMIhyZiCvVCuWJW4E3iaDj3r2xB8zsiZ";
	
		/*EK bana's credentials. From panday sir*/
//		String clientID = "AVcQ9L_ZzbpCdI_ROmffSxhifImE_OSimvYyGiwjp3VsXI91_AkCth5oGRD9cg9dPbQ7tQPpJQk4PtNG";
//		String clientSecret="EGbOD1cXGAoNhgpnhT4iih8VuP6O2WxbmwITdX3tRe0eZd8Bc3QVQgaOoIXFlp9DLapoj2EwaW2VQklN";
		
		/*agreement_test app's credentials*/
//		String clientID = "AW6OeztOUtEiwLkgjzfjDZQG06wqQdHRGbJDbfbqPvhL32UZk5c9xGEST37O7xiFN2PaIj660H2sTGkB";
//		String clientSecret = "EHLYy6sG7VsUYkXGkA2SAk8kqz67mwSWB8h6Ir_0X46P3Q7Kyui8qlach3wCxV1-a_FVjwacPZr6be-P";
		//Plan ID :- 23 | P-9NX2466406107210LWQRI3IY
		
		
		/*Braintree*/
//		String clientID = "AUs7Rbr2uJhjBwD0hJPWwCpQLiNurOZxjA3ZRzErAXZUgc-Gh51U3RDDb3mJ6VAxt7pmrSuB9Wy4xgz1";
//		String clientSecret="EFeaXkAmZaXEsNoNC4m03UcRhdm3BGeeo-Mt2X_6phg9LHc_B95xuq7QVHjceo9vxRZvuZkGmUypoRIG";
		
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
