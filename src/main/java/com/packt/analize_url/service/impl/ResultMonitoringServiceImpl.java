package com.packt.analize_url.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.repository.*;
import com.packt.analize_url.domain.resultpart.ResultStatus;
import com.packt.analize_url.observ.Observer;
import com.packt.analize_url.scheduler.ScheduledTasks;
import com.packt.analize_url.service.*;

@Service
public class ResultMonitoringServiceImpl implements ResultMonitoringService, Observer{
	
	@Autowired
	private ResultMonitoringRepository resultMonitoringRepository;
	
	// save scheduled tasks
	private List<ScheduledTasks> scheduledTasks = new ArrayList<ScheduledTasks>();
	
	// read all results
	public List<MonitoringResult> getAllResults() {
		return resultMonitoringRepository.getAllResults();
	}

	// read monitoring result by id
	public MonitoringResult getMonitoringResultById(String urlId) {
		return resultMonitoringRepository.getMonitoringResultById(urlId);
	}

	// add new monitoring result
	public void addMonitoringResultRow(MonitoringResult monitoringResult) {
		resultMonitoringRepository.addMonitoringResultRow(monitoringResult);
	}
	
	// delete monitoring result with id
	public void removeMonitoringResult(String urlId) {
		MonitoringResult monitoringResult = resultMonitoringRepository.getMonitoringResultById(urlId);
		resultMonitoringRepository.removeMonitoringResult(monitoringResult);
	}
	
	// clear all monitoring result
	public void clearMonitoringResults() {
		resultMonitoringRepository.clearMonitoringResults();
	}
	
	// start monitoring process
	public void runMonitoring(List<URLConfig> urlConfigs) {
		
		for(URLConfig uconf:urlConfigs){
			ScheduledTasks scheduledTask = new ScheduledTasks(uconf);
			scheduledTask.registerObserver(this);
			scheduledTasks.add(scheduledTask);
			scheduledTask.doAction();
		}
	}
	
	// stop monitoring process
	public void stopMonitoring() {
		
		for(ScheduledTasks st : scheduledTasks){
			st.doShutdown();
		}
		scheduledTasks.clear();
	}
	
	// update task with new URL
	public void changeTaskMonitoring(URLConfig urlConfig) {
		for(ScheduledTasks st : scheduledTasks){
			if(st.getUrlConfig().getUrlId().equals(urlConfig.getUrlId()))
			{
				st.setUrlConfig(urlConfig);
				break;
			}
		}
	}
	
	// add new URL for monitoring
	public void addTaskMonitoring(URLConfig urlConfig) {
		ScheduledTasks scheduledTask = new ScheduledTasks(urlConfig);
		scheduledTask.registerObserver(this);
		scheduledTasks.add(scheduledTask);
		scheduledTask.doAction();
	}
	
	// delete URL from monitoring
	public void deleteTaskMonitoring(URLConfig urlConfig) {
		ScheduledTasks stForDel = null;
		for(ScheduledTasks st : scheduledTasks){
			if(st.getUrlConfig().getUrlId().equals(urlConfig.getUrlId()))
			{
				stForDel = st;
				st.doShutdown();
				break;
			}
		}
		
		if(stForDel != null) {
			scheduledTasks.remove(stForDel);
		}
			
	}
	
	// read response details for URL
	public Map<String,String> getDetails(String id) {
		
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		Map<String, String> details = new HashMap<String, String>();
		
		headers = this.getMonitoringResultById(id).getHeaders();
		
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			details.put(entry.getKey(),entry.getValue().toString());
		}
		
		return details;
	}

	// renew monitoring result
	public void update(MonitoringResult monitoringResult) {
		
		MonitoringResult rmr = getMonitoringResultById(monitoringResult.getUrlId());
		
		if(rmr != null) { // replace monitoring result
			removeMonitoringResult(rmr.getUrlId());
			addMonitoringResultRow(monitoringResult);
		} else {	// add new result
			addMonitoringResultRow(monitoringResult);
		}
	}

}
