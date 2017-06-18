package com.packt.analize_url.domain;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


import com.packt.analize_url.domain.configpart.*;

// save configs of the URLs
@Entity
@Table(name = "URLConfig",uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class URLConfig {

	@Id
	@Column(name="ID", nullable=false, unique=true)
	private String urlId;

	@Column(name="URL", length=150)
	private String url;
	
	@Column(name="PERIOD")
	private int monitoringPeriod;
	
	@Embedded
	private ResponseTime resposeTime;
	
	@Column(name="CODE")
	private int responseCode;
	
	@Embedded
	private ResponseLength responseLength;
	
	@Column(name="SUBSTRING", length=100)
	private String responseFindSubstring;
	
	@Column(name="RUN")
	@Type(type="yes_no")
	private boolean monitored;

	public URLConfig() {
		monitored = true;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMonitoringPeriod() {
		return monitoringPeriod;
	}

	public void setMonitoringPeriod(int monitoringPeriod) {
		this.monitoringPeriod = monitoringPeriod;
	}

	public ResponseTime getResposeTime() {
		return resposeTime;
	}

	public void setResposeTime(ResponseTime resposeTime) {
		this.resposeTime = resposeTime;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public ResponseLength getResponseLength() {
		return responseLength;
	}

	public void setResponseLength(ResponseLength responseLength) {
		this.responseLength = responseLength;
	}

	public String getResponseFindSubstring() {
		return responseFindSubstring;
	}

	public void setResponseFindSubstring(String responseFindSubstring) {
		this.responseFindSubstring = responseFindSubstring;
	}
	
	public boolean isMonitored() {
		return monitored;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}
	
	@Override
	public String toString() {
		StringBuilder printObject = new StringBuilder();
		printObject.append("\nURL id: " + urlId + "\n");
		if (url != null)
			printObject.append("URL: " + url + "\n");
		printObject.append("The period of the monitoring: " + monitoringPeriod + " sec." + "\n");
		if(resposeTime != null) {
			printObject.append("Time for OK: " + resposeTime.getTimeForOK() + " sec. | ");
			printObject.append("Time for Warning: " + resposeTime.getTimeForWarning() + " sec. | ");
			printObject.append("Time for Critical: " + resposeTime.getTimeForCritical() + "\n");
		}
		printObject.append("Expected response code: " + responseCode + "\n");
		if(responseLength != null) {
			printObject.append("Min response length: " + responseLength.getMinLength() + " bytes"+"\n");
			printObject.append("Max response length: " + responseLength.getMaxLength() + " bytes"+"\n");
		}
		if(responseFindSubstring != null) {
			printObject.append("Substring for finding in the response: " + responseFindSubstring + "\n");
		}
		if(monitored)
			printObject.append("Is active monitored: " + "YES" + "\n");
		else 
			printObject.append("Is active monitored: " + "NO" + "\n");
		
		return printObject.toString();
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URLConfig other = (URLConfig) obj;
		if (urlId == null) {
			if (other.urlId != null)
				return false;
		} else if (!urlId.equals(other.urlId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlId == null) ? 0 : urlId.hashCode());
		return result;
	}

}
