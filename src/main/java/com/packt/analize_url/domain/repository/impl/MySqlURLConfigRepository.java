package com.packt.analize_url.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.configpart.ResponseLength;
import com.packt.analize_url.domain.configpart.ResponseTime;
import com.packt.analize_url.domain.repository.URLConfigRepository;
import com.packt.analize_url.hibernate.dao.URLConfigDAO;
import com.packt.analize_url.hibernate.dao.URLConfigDAOImpl;

@Primary
@Repository
public class MySqlURLConfigRepository implements URLConfigRepository{

	private List<URLConfig> listOfURLConfigs = new ArrayList<URLConfig>();
	
	URLConfigDAO urlConfigDAO;

	public MySqlURLConfigRepository() {
		
		urlConfigDAO = new URLConfigDAOImpl();
		urlConfigDAO.openCurrentSessionWithTransaction();
		
		fillList();
	}

	public List<URLConfig> getAllURLConfigs() {
		return listOfURLConfigs;
	}

	public URLConfig getURLConfigById(String urlConfigID) {
		URLConfig urlConfigById = null;
		for (URLConfig urlConfig : listOfURLConfigs) {
			if (urlConfig != null && urlConfig.getUrlId() != null && urlConfig.getUrlId().equals(urlConfigID)) {
				urlConfigById = urlConfig;
				break;
			}
		}
		if (urlConfigById == null) {
			throw new IllegalArgumentException("No urlConfig found with the urlConfig id: " + urlConfigID);
		}
		return urlConfigById;
	}

	public void addURLConfig(URLConfig urlConfig) {
		
		boolean error = false;
		try {
			urlConfigDAO.addURLConfig(urlConfig);
			urlConfigDAO.commitCurrentSession();
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
			urlConfigDAO.rollback();
		}
		if(!error)
			fillList();
		
	}
	
	public void deleteURLConfig(String urlConfigID) {

		boolean error = false;
		try {
			urlConfigDAO.deleteURLConfigById(urlConfigID);
			urlConfigDAO.commitCurrentSession();
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
			urlConfigDAO.rollback();
		}
		if(!error)
			fillList();
	}
	
	public void updateURLConfig(URLConfig urlConfig) {
		int indexUrl = -1;
		for (URLConfig uc : listOfURLConfigs) {
			if (uc != null && uc.getUrlId() != null && uc.getUrlId().equals(urlConfig.getUrlId())) {
				indexUrl = listOfURLConfigs.indexOf(uc);
				break;
			}
		}
		
		boolean error = false;
		try {
			urlConfigDAO.saveOrUpdateURLConfig(urlConfig);
			urlConfigDAO.commitCurrentSession();
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
			urlConfigDAO.rollback();
		}
		if(!error)
			fillList();
	}
	
	// read configs of the URLs from the database and save result in the local copy
	private void fillList() {
		
		List<URLConfig> newlistOfURLConfigs = new ArrayList<URLConfig>();
		boolean error = false;
		try {
			for(URLConfig readURLConf:urlConfigDAO.getAllURLConfigs_criteria()) 
			{
				newlistOfURLConfigs.add(readURLConf);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
		}
		
		if(!error) {
			listOfURLConfigs = newlistOfURLConfigs;
		}
	}
	
	// close connection with DB before program closed
	@PreDestroy
	private void closeConnections() {
		try {
			urlConfigDAO.closeCurrentSessionWithTransaction();
		} catch(Exception ex) {
			System.exit(1);
		}
	}
	
	

}
