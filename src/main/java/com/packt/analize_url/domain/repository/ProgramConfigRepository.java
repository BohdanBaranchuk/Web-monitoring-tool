package com.packt.analize_url.domain.repository;

public interface ProgramConfigRepository {
	
	boolean isStarted();
	
	void setStarted(boolean started);
	
	int getTimeRefreshPage();
	
	void setTimeRefreshPage(int timeRefreshPage);

}
