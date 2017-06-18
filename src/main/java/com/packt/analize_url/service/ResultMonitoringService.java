package com.packt.analize_url.service;

import java.util.List;
import java.util.Map;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.resultpart.ResultStatus;

public interface ResultMonitoringService {

	// get operation results
	List<MonitoringResult> getAllResults();
	MonitoringResult getMonitoringResultById(String urlId);

	// CRUD operation with results
	void addMonitoringResultRow(MonitoringResult monitoringResult);
	void clearMonitoringResults();
	void removeMonitoringResult (String urlId);
	
	// get details for some result id
	Map<String, String> getDetails(String id);
	
	// run/stop program monitoring
	void runMonitoring(List<URLConfig> urlConfigs);
	void stopMonitoring();
	
	// operation with tasks
	void changeTaskMonitoring(URLConfig urlConfig);
	void addTaskMonitoring(URLConfig urlConfig);
	void deleteTaskMonitoring(URLConfig urlConfig);


}
