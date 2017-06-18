package com.packt.analize_url.domain;

// save config of the program (RUN/STOP monitoring, time for automatic page refresh)
public class ProgramConfig {
	
	private boolean started;
	private int timeRefreshPage;
	
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public int getTimeRefreshPage() {
		return timeRefreshPage;
	}
	public void setTimeRefreshPage(int timeRefreshPage) {
		this.timeRefreshPage = timeRefreshPage;
	}

}
