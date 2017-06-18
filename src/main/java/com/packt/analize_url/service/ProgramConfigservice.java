package com.packt.analize_url.service;

public interface ProgramConfigservice {
	
	boolean isStarted();
	
	void setStarted(boolean started);
	
	int getTimeRefreshPage();
	
	void setTimeRefreshPage(int timeRefreshPage);

}
