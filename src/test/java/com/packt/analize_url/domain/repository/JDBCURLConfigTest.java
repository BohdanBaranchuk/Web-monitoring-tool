package com.packt.analize_url.domain.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.configpart.ResponseLength;
import com.packt.analize_url.domain.configpart.ResponseTime;
import com.packt.analize_url.domain.repository.impl.JDBCMySqlConfigRepository;
import com.packt.analize_url.domain.repository.impl.MySqlURLConfigRepository;

public class JDBCURLConfigTest {
	
	URLConfigRepository jdbcConfigRepository;
	
	@Before
	public void setup() {
		jdbcConfigRepository = new JDBCMySqlConfigRepository();
	}
	
	
	
	@Test
	public void CRUD() {
		
		Assert.assertEquals(jdbcConfigRepository.getAllURLConfigs().size(),4);
	
		URLConfig urlConfig = new URLConfig();
		
		urlConfig.setUrlId("89");
		urlConfig.setMonitored(true);
		urlConfig.setMonitoringPeriod(200);
		urlConfig.setResponseCode(201);
		urlConfig.setResponseFindSubstring("toFind");
		
		ResponseLength responseLength = new ResponseLength();
		responseLength.setMaxLength(300000);
		responseLength.setMinLength(267000);
		urlConfig.setResponseLength(responseLength);
		
		ResponseTime responseTime = new ResponseTime();
		responseTime.setTimeForOK(120);
		responseTime.setTimeForWarning(140);
		responseTime.setTimeForCritical(180);
		urlConfig.setResposeTime(responseTime);
		
		urlConfig.setUrl("https://mvnrepository.com/artifact/junit/junit/4.12");
		
		jdbcConfigRepository.addURLConfig(urlConfig);
		
		Assert.assertEquals(jdbcConfigRepository.getAllURLConfigs().size(),5);
		
		urlConfig.setMonitored(false);
		jdbcConfigRepository.updateURLConfig(urlConfig);
		Assert.assertFalse(jdbcConfigRepository.getURLConfigById(urlConfig.getUrlId()).isMonitored());
		
		jdbcConfigRepository.deleteURLConfig(urlConfig.getUrlId());
		
		Assert.assertEquals(jdbcConfigRepository.getAllURLConfigs().size(),4);
	
		
	
	}
	
	
}
