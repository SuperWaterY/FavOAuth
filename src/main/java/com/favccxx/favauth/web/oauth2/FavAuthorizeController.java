package com.favccxx.favauth.web.oauth2;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.favauth.constants.FavOAuth2Constants;
import com.favccxx.favauth.service.IFavAuthService;

@Controller
@RequestMapping("oauth2")
public class FavAuthorizeController {
	
	@Autowired
	IFavAuthService favAuthService;
	
	@RequestMapping("favauthorize")
	public ModelAndView favAuthorize(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException{
		ModelAndView mav = new ModelAndView();
//		String username, String webKey, String scope, String state,String display
		//鏋勫缓OAuth璇锋眰
		OAuthAuthzRequest oAuthzRequest = new OAuthAuthzRequest(request);
		//鑾峰彇OAuth瀹㈡埛绔疘d
		String clientId = oAuthzRequest.getClientId();
		//鏍￠獙瀹㈡埛绔疘d鏄惁姝ｇ‘
		/**
		 * 测试 add by yzzhi 20160309
		 * */
		System.out.println("=====>clientId====>" + clientId);
		/* add end**/
		if(!favAuthService.checkClientId(clientId)){
			OAuthResponse oAuthResponse = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.INVALID_CLIENT)
					.setErrorDescription("鏃犳晥鐨勫鎴风Id")
					.buildJSONMessage();
			mav.addObject(FavOAuth2Constants.OAUTH_AUTHORIZE_FAILED_KEY, "鏃犳晥鐨勫鎴风Id");
			mav.setViewName("forward:/oauth2/authorizefailed");
			return mav;
//			return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
		}
		
		String username = "admin";
		
		//鐢熸垚鎺堟潈鐮�		
		String authCode = null;
		String responseType = oAuthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
		//ResponseType浠呮敮鎸丆ODE鍜孴OKEN
		if(responseType.equals(ResponseType.CODE.toString())){
			OAuthIssuerImpl oAuthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			authCode = oAuthIssuerImpl.authorizationCode();
			favAuthService.addAuthCode(authCode, username);
		}
		
		//鏋勫缓OAuth鍝嶅簲
		OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
		
		//璁剧疆鎺堟潈鐮�		
		builder.setCode(authCode);
		
		//鑾峰彇瀹㈡埛绔噸瀹氬悜鍦板潃
		String redirectURI = oAuthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
		
		//鏋勫缓鍝嶅簲
		OAuthResponse response = builder.location(redirectURI).buildBodyMessage();
		//鏍规嵁OAuthResponse杩斿洖ResponseEntity鍝嶅簲
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setLocation(new URI(response.getLocationUri()));
//			return new ResponseEntity<>(headers, HttpStatus.valueOf(response.getResponseStatus()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		mav.addObject(OAuth.OAUTH_CODE, authCode);
		mav.setViewName("redirect:"+redirectURI);
		return mav;
	}
	
	
	

}
