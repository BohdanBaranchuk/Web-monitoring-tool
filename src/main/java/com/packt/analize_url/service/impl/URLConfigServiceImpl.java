package com.packt.analize_url.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.packt.analize_url.domain.*;
import com.packt.analize_url.domain.repository.*;
import com.packt.analize_url.service.*;

@Service
public class URLConfigServiceImpl implements URLConfigService{
	
	@Autowired
	private URLConfigRepository urlConfigRepository;
	
	
	public void addURLConfig(URLConfig urlConfig){
		urlConfigRepository.addURLConfig(urlConfig);
	}

	public URLConfig readURLConfig(String urlId){
		URLConfig urlConfig = urlConfigRepository.getURLConfigById(urlId);
		return urlConfig;
	}

	public void updateURLConfig(URLConfig urlConfig){
		urlConfigRepository.updateURLConfig(urlConfig);
	}

	public void deleteURLConfig(String urlId) {
		urlConfigRepository.deleteURLConfig(urlId);
	}
	
	public List <URLConfig> getAllURLConfigs() {
		return urlConfigRepository.getAllURLConfigs();
	}
	
	public void pauseURLConfig(String urlId) {
		URLConfig urlConfig = urlConfigRepository.getURLConfigById(urlId);
		urlConfig.setMonitored(!urlConfig.isMonitored());
		urlConfigRepository.updateURLConfig(urlConfig);
	}
	
	public int getActiveMonitored() {
		int active = 0;
		
		for(URLConfig uconf:getAllURLConfigs()){
			if(uconf.isMonitored()) {
				active++;
			}
		}

		return active;
	}

}
