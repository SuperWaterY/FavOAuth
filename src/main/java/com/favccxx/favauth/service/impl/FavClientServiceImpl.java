package com.favccxx.favauth.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favccxx.favauth.dao.IFavClientDao;
import com.favccxx.favauth.pojo.FavClient;
import com.favccxx.favauth.service.IFavClientService;

@Service("favClientService")
public class FavClientServiceImpl implements IFavClientService {

	@Autowired
	private IFavClientDao favClientDao;

	public FavClient createFavClient(FavClient favClient) {
		favClient.setClientId(UUID.randomUUID().toString());
		favClient.setClientSecret(UUID.randomUUID().toString());
		return favClientDao.createFavClient(favClient);
	}

	public FavClient updateFavClient(FavClient favClient) {
		return favClientDao.updateFavClient(favClient);
	}

	public void deleteFavClient(Long clientId) {
		favClientDao.deleteFavClient(clientId);
	}

	public FavClient findOne(Long clientId) {
		return favClientDao.findOne(clientId);
	}

	public List<FavClient> findAll() {
		return favClientDao.findAll();
	}

	public FavClient findByClientId(String clientId) {
		return favClientDao.findByClientId(clientId);
	}

	public FavClient findByClientSecret(String clientSecret) {
		return favClientDao.findByClientSecret(clientSecret);
	}

}
