package com.favccxx.favauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favccxx.favauth.dao.IFavWebAppDao;
import com.favccxx.favauth.pojo.FavWebApp;
import com.favccxx.favauth.service.IFavWebAppService;

@Service("favWebAppService")
public class FabWebAppServiceImpl implements IFavWebAppService {
	
	@Autowired
	private IFavWebAppDao favWebAppDao;

	public FavWebApp createFavWebApp(FavWebApp favWebApp) {
		return favWebAppDao.createFavWebApp(favWebApp);
	}

	public List<FavWebApp> findAll() {
		return favWebAppDao.findAll();
	}

	public FavWebApp updateFavWebApp(FavWebApp favWebApp) {
		return favWebAppDao.updateFavWebApp(favWebApp);
	}

	public FavWebApp findOne(Long webId) {
		return favWebAppDao.findOne(webId);
	}

}
