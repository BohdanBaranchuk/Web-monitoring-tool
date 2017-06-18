package com.packt.analize_url.domain.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.*;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.repository.impl.InMemoryURLConfigRepository;

public class URLConfigRepositoryTest {
	
	URLConfigRepository urlConfigRepository = new InMemoryURLConfigRepository();
	
	@Test
	public void testRead() {
		Assert.assertEquals(urlConfigRepository.getAllURLConfigs().size(), 3);
		
		urlConfigRepository.addURLConfig(new URLConfig());
		Assert.assertEquals(urlConfigRepository.getAllURLConfigs().size(), 4);
		
		Assert.assertEquals(urlConfigRepository.getURLConfigById("19").getUrlId(), "19");
		urlConfigRepository.deleteURLConfig("19");
		
		Assert.assertEquals(urlConfigRepository.getAllURLConfigs().size(), 3);
		
		URLConfig urlConfig = urlConfigRepository.getURLConfigById("12");
		urlConfig.setResponseCode(302);
		
		urlConfigRepository.updateURLConfig(urlConfig);
		Assert.assertEquals(urlConfigRepository.getURLConfigById("12").getResponseCode(), 302);

		
	}
	

}
