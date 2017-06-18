package com.packt.analize_url.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.packt.analize_url.domain.configpart.*;

public class URLConfigTest {
	
	URLConfig urlConfig;
	
	@Before
	public void setup() {
		urlConfig = new URLConfig();
	}
	
	@Test
	public void readWriteURLConfig() {

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

		Assert.assertEquals(urlConfig.getUrlId(), "89");
		Assert.assertEquals(urlConfig.isMonitored(), true);
		Assert.assertEquals(urlConfig.getMonitoringPeriod(), 200);
		Assert.assertEquals(urlConfig.getResponseCode(), 201);
		Assert.assertEquals(urlConfig.getResponseFindSubstring(), "toFind");
		Assert.assertEquals(urlConfig.getResponseLength().getMaxLength(), 300000);
		Assert.assertEquals(urlConfig.getResponseLength().getMinLength(), 267000);
		Assert.assertEquals(urlConfig.getResposeTime().getTimeForOK(), 120);
		Assert.assertEquals(urlConfig.getResposeTime().getTimeForWarning(), 140);
		Assert.assertEquals(urlConfig.getResposeTime().getTimeForCritical(), 180);
		Assert.assertEquals(urlConfig.getUrl(), "https://mvnrepository.com/artifact/junit/junit/4.12");

	}

}
