package com.packt.analize_url.domain.repository;


import java.util.List;

import com.packt.analize_url.domain.*;

public interface URLConfigRepository {
	
	List <URLConfig> getAllURLConfigs();
	
	URLConfig getURLConfigById(String urlConfigID);
	
	void addURLConfig(URLConfig urlConfig);
	
	void deleteURLConfig(String urlConfigID);
	
	void updateURLConfig(URLConfig urlConfig);

}
