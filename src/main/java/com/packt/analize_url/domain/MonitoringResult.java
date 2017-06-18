package com.packt.analize_url.domain;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.packt.analize_url.domain.resultpart.*;

// for save the result of the monitoring URL with some id
public class MonitoringResult {
	
	private final String urlId;
	private ResultStatus resultStatus;
	private double responseTime;
	private int responseCode;
	private int responseLength;
	private boolean findSubstring;
	private Map<String, List<String>> headers;
	private String timeSnapshot;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public MonitoringResult (String urlId) {
		this.urlId = urlId;
		timeSnapshot = dateFormat.format(new Date()).toString();
	}

	public String getUrlId() {
		return urlId;
	}

	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public double getResponseLength() {
		return responseLength;
	}

	public void setResponseLength(int responseLength) {
		this.responseLength = responseLength;
	}

	public boolean isFindSubstring() {
		return findSubstring;
	}

	public void setFindSubstring(boolean findSubstring) {
		this.findSubstring = findSubstring;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getTimeSnapshot() {
		return timeSnapshot;
	}

	public void setTimeSnapshot(String timeSnapshot) {
		this.timeSnapshot = timeSnapshot;
	}
	
	@Override
	public String toString() {
		StringBuilder printObject = new StringBuilder();
		printObject.append("Result for URL ID: " + urlId + "\n");
		printObject.append("Time: " + timeSnapshot + "\n");
		printObject.append("Status page: " + resultStatus + "\n");
		printObject.append("Response time: " + responseTime + " mc. \n");
		printObject.append("Response code: " + responseCode + "\n");
		printObject.append("Response length: " + responseLength + " bytes \n");
		printObject.append("Find substring: " + findSubstring + "\n");
		
		return printObject.toString();
	}


}
