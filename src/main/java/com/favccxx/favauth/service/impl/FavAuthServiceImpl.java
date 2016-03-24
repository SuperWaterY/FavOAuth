package com.favccxx.favauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.favccxx.favauth.service.IFavAuthService;
import com.favccxx.favauth.service.IFavClientService;

@Service("favAuthService")
public class FavAuthServiceImpl implements IFavAuthService {
	
	private Cache cache;
	
	@Autowired
	private IFavClientService favClientService;
	
	@Autowired
	public FavAuthServiceImpl(CacheManager cacheManager){
		this.cache = cacheManager.getCache("code-cache");
	}

	public void addAuthCode(String authCode, String username) {
		cache.put(authCode, username);
	}

	public void addAccessToken(String accessToken, String username) {
		cache.put(accessToken, username);
	}

	public boolean checkAuthCode(String authCode) {
		return cache.get(authCode) != null;
	}

	public boolean checkAccessToken(String accessToken) {
		return cache.get(accessToken) != null;
	}

	public String getUsernameByAuthCode(String authCode) {
		return (String)cache.get(authCode).get();
	}

	public String getUsernameByAccessToken(String accessToken) {
		return (String)cache.get(accessToken).get();
	}

	public long getExpireIn() {
		 return 3600L;
	}

	public boolean checkClientId(String clientId) {
		return favClientService.findByClientId(clientId) != null;
	}

	public boolean checkClientSecret(String clientSecret) {
		return favClientService.findByClientSecret(clientSecret) != null;
	}

}
