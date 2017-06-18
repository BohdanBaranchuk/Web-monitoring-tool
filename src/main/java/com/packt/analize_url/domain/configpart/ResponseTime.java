package com.packt.analize_url.domain.configpart;

import javax.persistence.Column;

import javax.persistence.*;

@Embeddable
public class ResponseTime {
	
	@Column(name="TIMEOK")
	private int timeForOK;
	
	@Column(name="TIMEWARN")
	private int timeForWarning;
	
	@Column(name="TIMECRITIC")
	private int timeForCritical;

	public int getTimeForOK() {
		return timeForOK;
	}

	public void setTimeForOK(int timeForOK) {
		this.timeForOK = timeForOK;
	}

	public int getTimeForWarning() {
		return timeForWarning;
	}

	public void setTimeForWarning(int timeForWarning) {
		this.timeForWarning = timeForWarning;
	}

	public int getTimeForCritical() {
		return timeForCritical;
	}

	public void setTimeForCritical(int timeForCritical) {
		this.timeForCritical = timeForCritical;
	}

}
