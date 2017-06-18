package com.packt.analize_url.validation;

import java.util.List;

import com.packt.analize_url.domain.URLConfig;

public interface Validator {
	
	// validate URL before Add
	boolean validateNewURLConfigAdd(URLConfig addingUrlConfig, List<URLConfig> listOfURLConfigs);
	
	// validate URL after edit
	boolean validateNewURLConfigEdit(String id,URLConfig editUrlConfig);

}
