package com.packt.analize_url.validation.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.validation.Validator;

@Service
public class ValidatorURLConfig implements Validator{
	
	public boolean validateNewURLConfigEdit(String id,URLConfig editUrlConfig) {
		
		if(!editUrlConfig.getUrlId().equals(id)) 
			return false;
		
		if(!baseValidation(editUrlConfig))
			return false;
	
		return true;
	}

	public boolean validateNewURLConfigAdd(URLConfig addingUrlConfig, List<URLConfig> listOfURLConfigs) {
		
		for( URLConfig uc: listOfURLConfigs) {
			if(addingUrlConfig.getUrlId().equals(uc.getUrlId())) {
				return false;
			}
		}
		
		if(!baseValidation(addingUrlConfig))
			return false;
		
		return true;
	}
	
	// validation algorithm
	private boolean baseValidation(URLConfig urlConfig) {
		if(urlConfig.getMonitoringPeriod() < 1)
			return false;
		
		if( (urlConfig.getResponseCode() < 100) || (urlConfig.getResponseCode() >= 600))
			return false;
		
		if ((urlConfig.getResponseLength().getMaxLength() <= 0) || (urlConfig.getResponseLength().getMinLength() <= 0))
			return false;
		if (urlConfig.getResponseLength().getMaxLength() <= urlConfig.getResponseLength().getMinLength() )
			return false;
		
		if(!((urlConfig.getResposeTime().getTimeForCritical() > urlConfig.getResposeTime().getTimeForWarning()) && (urlConfig.getResposeTime().getTimeForWarning() > urlConfig.getResposeTime().getTimeForOK())))
			return false;
		
		return true;
	}

}
