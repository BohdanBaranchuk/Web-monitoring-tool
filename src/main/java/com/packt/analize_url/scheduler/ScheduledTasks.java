package com.packt.analize_url.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.resultpart.ResultStatus;
import com.packt.analize_url.observ.Observer;
import com.packt.analize_url.observ.Subject;

// periodic send request for every url config and notify observers
public class ScheduledTasks implements Subject{
	
	// all observers
	private ArrayList observers;
	
	// URL to request
	private URLConfig urlConfig;
	
	// the result of the request
	private MonitoringResult monitoringResultMain;

	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	
	public ScheduledTasks(URLConfig urlConfig) {
		observers = new ArrayList();
		this.urlConfig = urlConfig;
	}
	
	public URLConfig getUrlConfig() {
		return urlConfig;
	}

	public void setUrlConfig(URLConfig urlConfig) {
		this.urlConfig = urlConfig;
	}

	// add observers
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
	// remove observers
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if(i >= 0) {
			observers.remove(i);
		}
	}
	
	// notify registered observers
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(monitoringResultMain);
		}
	}
	
	// run periodic monitoring with URL id time period
 	public void doAction(){
 		int periodInSeconds = urlConfig.getMonitoringPeriod();
 		
		 service.scheduleWithFixedDelay(
				 new Runnable() {
					  public void run() {
						  	try {
						  		if(urlConfig.isMonitored()){
						  			monitoringResultMain = stepRead(urlConfig);
						  			notifyObservers();
						  			System.out.println("Request URL: " + urlConfig);
						  			System.out.println("Get new monitoring result: " + monitoringResultMain);
						  		}
						  	} catch(Exception ex) {
						  		ex.printStackTrace();
						  	}
					  }
					 }
				 , 0, periodInSeconds, TimeUnit.SECONDS);
	}
	
 	// stop periodic monitoring
	public void doShutdown() {
		service.shutdown();
	}
	
	// restart monitoring
	public void restart() {
		doShutdown();
		doAction();		
	}
	
	// one step for monitoring URL
	private MonitoringResult stepRead(URLConfig uconf) throws Exception {
		
		MonitoringResult monitoringResult = new MonitoringResult(uconf.getUrlId());
	
        URL url = new URL(uconf.getUrl());
        
        // read body
        String inputLine;
        StringBuilder inputBody = new StringBuilder();

        long starTime = System.nanoTime();
        
        HttpURLConnection uconn = (HttpURLConnection ) url.openConnection();
        
        int code = uconn.getResponseCode();
        
        if (200 <= code && code <= 299) {
        	BufferedReader in = new BufferedReader(new InputStreamReader(uconn.getInputStream()));
            
            long endTime = System.nanoTime();
                   
            // get info
            Map<String, List<String>> map = uconn.getHeaderFields();
            
        	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
        		//System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
        	}

        	String server = uconn.getHeaderField("Content-Length");
        	
            while ((inputLine = in.readLine()) != null) { 
            	inputBody.append(inputLine);
            	inputBody.append("\n");
            }
            
            in.close();
            
            double elapsedTime  = TimeUnit.NANOSECONDS.toMillis((endTime - starTime));
            
            if (uconf.getResponseFindSubstring().length()>0) {
            	monitoringResult.setFindSubstring(inputBody.indexOf(uconf.getResponseFindSubstring())>0);
            } else {
            	monitoringResult.setFindSubstring(false);
            }
            
            monitoringResult.setHeaders(map);
            monitoringResult.setResponseCode(code);
            
            if (server == null) {							// if header doesn't preset in the response
            	monitoringResult.setResponseLength(inputBody.length());
        	} else { 										// if header preset in the response
        		monitoringResult.setResponseLength(Integer.parseInt(server));
        	}
            
            monitoringResult.setResponseTime(elapsedTime);
            monitoringResult.setResultStatus(checkStatus(uconf,monitoringResult));
        
        } else {
        	BufferedReader inErr = new BufferedReader(new InputStreamReader(uconn.getErrorStream()));
        	monitoringResult.setResultStatus(ResultStatus.CRITICAL);
        }
        return monitoringResult;
	}
	
	// check conditional for one result status
	private ResultStatus checkStatus (URLConfig uconf, MonitoringResult monitoringResult) {
	
		// check response code
		if(monitoringResult.getResponseCode() != uconf.getResponseCode()) {
			return ResultStatus.CRITICAL;
		}
		
		// check response length
		if((monitoringResult.getResponseLength() <= uconf.getResponseLength().getMinLength()) || (monitoringResult.getResponseLength() >= uconf.getResponseLength().getMaxLength())) {
			return ResultStatus.CRITICAL;
		}
		
		// check find substring in the response
		if(((uconf.getResponseFindSubstring() != null) && (uconf.getResponseFindSubstring().length()>0) && !monitoringResult.isFindSubstring())) {
			return ResultStatus.CRITICAL;
		}
		
		// check response time
		if( monitoringResult.getResponseTime() >= uconf.getResposeTime().getTimeForCritical()) {
			return ResultStatus.CRITICAL;
		} else if(monitoringResult.getResponseTime() >= uconf.getResposeTime().getTimeForWarning()) {
			return ResultStatus.WARNING;
		}

		return ResultStatus.OK;
	}

	

	
}
