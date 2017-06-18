package com.packt.analize_url.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.analize_url.domain.ProgramConfig;
import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.repository.*;

@Repository
public class InMemoryProgramConfigRepository implements ProgramConfigRepository{
	
	private ProgramConfig programConfig;
	
	public InMemoryProgramConfigRepository()
	{
		programConfig = new ProgramConfig();
		programConfig.setStarted(true);			// don't run automatic monitoring
		programConfig.setTimeRefreshPage(10000);	// refresh page every 10 seconds
	}
	
	public boolean isStarted() {
		return programConfig.isStarted();
	}
	
	public void setStarted(boolean started) {
		programConfig.setStarted(started);
	}
	
	public int getTimeRefreshPage() {
		return programConfig.getTimeRefreshPage();
		
	}
	
	public void setTimeRefreshPage(int timeRefreshPage) {
		programConfig.setTimeRefreshPage(timeRefreshPage);
	}

}
