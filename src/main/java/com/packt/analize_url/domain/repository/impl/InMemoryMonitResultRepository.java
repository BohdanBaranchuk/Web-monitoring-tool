package com.packt.analize_url.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.repository.ResultMonitoringRepository;

@Repository
public class InMemoryMonitResultRepository implements ResultMonitoringRepository{

	private List<MonitoringResult> listOfMonitoringResult = new ArrayList<MonitoringResult>();

	public List<MonitoringResult> getAllResults() {
		return listOfMonitoringResult;
	}

	public MonitoringResult getMonitoringResultById(String urlId) {
		MonitoringResult monitoringResult = null;
		for (MonitoringResult mr : listOfMonitoringResult) {
			if (mr != null && mr.getUrlId() != null && mr.getUrlId().equals(urlId)) {
				monitoringResult = mr;
				break;
			}
		}
		
		return monitoringResult;
	}

	public void addMonitoringResultRow(MonitoringResult monitoringResult) {
		listOfMonitoringResult.add(monitoringResult);
	}

	public void clearMonitoringResults() {
		listOfMonitoringResult.clear();
	}
	
	public void removeMonitoringResult(MonitoringResult monitoringResult) {
		listOfMonitoringResult.remove(monitoringResult);
	}

}
