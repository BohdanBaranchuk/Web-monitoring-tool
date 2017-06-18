package com.packt.analize_url.domain.repository.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.packt.analize_url.domain.*;
import com.packt.analize_url.domain.configpart.ResponseLength;
import com.packt.analize_url.domain.configpart.ResponseTime;
import com.packt.analize_url.domain.repository.*;


@Repository
public class InMemoryURLConfigRepository implements URLConfigRepository {

	private List<URLConfig> listOfURLConfigs = new ArrayList<URLConfig>();

	public InMemoryURLConfigRepository() {
		URLConfig testURL1 = new URLConfig();
		testURL1.setUrlId("19");

		try {
			testURL1.setUrl("https://en.wikipedia.org/wiki/England");
		} catch (Exception ex) {

		}
		testURL1.setMonitoringPeriod(5);
		testURL1.setResponseCode(200);
		testURL1.setResponseFindSubstring("wikipedia");

		ResponseLength responseLength = new ResponseLength();
		responseLength.setMaxLength(30000);
		responseLength.setMinLength(10000);
		testURL1.setResponseLength(responseLength);

		ResponseTime responseTime = new ResponseTime();
		responseTime.setTimeForOK(10);
		responseTime.setTimeForWarning(20);
		responseTime.setTimeForCritical(30);
		testURL1.setResposeTime(responseTime);

		URLConfig testURL2 = new URLConfig();
		testURL2.setUrlId("12");

		try {
			testURL2.setUrl("http://www.bowers-wilkins.net/car-audio/car-audio");
		} catch (Exception ex) {

		}
		testURL2.setMonitoringPeriod(15);
		testURL2.setResponseCode(200);
		testURL2.setResponseFindSubstring("wikipedia");

		ResponseLength responseLength2 = new ResponseLength();
		responseLength2.setMaxLength(30000);
		responseLength2.setMinLength(10000);
		testURL2.setResponseLength(responseLength2);

		ResponseTime responseTime2 = new ResponseTime();
		responseTime2.setTimeForOK(10);
		responseTime2.setTimeForWarning(20);
		responseTime2.setTimeForCritical(30);
		testURL2.setResposeTime(responseTime2);

		URLConfig testURL3 = new URLConfig();
		testURL3.setUrlId("14");

		try {
			testURL3.setUrl("https://www.usatoday.com/");
		} catch (Exception ex) {

		}
		testURL3.setMonitoringPeriod(20);
		testURL3.setResponseCode(200);
		testURL3.setResponseFindSubstring("wikipedia");

		ResponseLength responseLength3 = new ResponseLength();
		responseLength3.setMaxLength(30000);
		responseLength3.setMinLength(10000);
		testURL3.setResponseLength(responseLength3);

		ResponseTime responseTime3 = new ResponseTime();
		responseTime3.setTimeForOK(10);
		responseTime3.setTimeForWarning(20);
		responseTime3.setTimeForCritical(30);
		testURL3.setResposeTime(responseTime3);

		listOfURLConfigs.add(testURL1);
		listOfURLConfigs.add(testURL2);
		listOfURLConfigs.add(testURL3);
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
		listOfURLConfigs.add(urlConfig);
	}
	
	public void deleteURLConfig(String urlConfigID) {
		listOfURLConfigs.remove(getURLConfigById(urlConfigID));
	}
	
	public void updateURLConfig(URLConfig urlConfig) {
		int indexUrl = -1;
		for (URLConfig uc : listOfURLConfigs) {
			if (uc != null && uc.getUrlId() != null && uc.getUrlId().equals(urlConfig.getUrlId())) {
				indexUrl = listOfURLConfigs.indexOf(uc);
				break;
			}
		}
		listOfURLConfigs.remove(indexUrl);
		listOfURLConfigs.add(urlConfig);
	}
}
