package com.packt.analize_url.domain.repository;

import java.util.List;

import com.packt.analize_url.domain.*;

public interface ResultMonitoringRepository {

	List <MonitoringResult> getAllResults();
	
	MonitoringResult getMonitoringResultById(String urlId);
	
	void addMonitoringResultRow(MonitoringResult monitoringResult);
	
	void clearMonitoringResults();
	
	void removeMonitoringResult(MonitoringResult monitoringResult);
	
	
}
