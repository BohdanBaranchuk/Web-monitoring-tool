package com.packt.analize_url.service;

import java.util.List;

import com.packt.analize_url.domain.URLConfig;

public interface URLConfigService {
	
	// GRUD operation with URL
	void addURLConfig(URLConfig urlConfig);
	void updateURLConfig(URLConfig urlConfig);
	URLConfig readURLConfig(String urlId);
	void deleteURLConfig(String urlId);
	
	List <URLConfig> getAllURLConfigs();
	
	// change monitored state
	void pauseURLConfig(String urlId);
	
	int getActiveMonitored();
}
