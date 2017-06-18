package com.packt.analize_url.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.analize_url.domain.repository.ProgramConfigRepository;
import com.packt.analize_url.domain.repository.ResultMonitoringRepository;
import com.packt.analize_url.service.ProgramConfigservice;

@Service
public class ProgramConfigserviceImpl implements ProgramConfigservice{
	
	@Autowired
	private ProgramConfigRepository programConfigRepository;
	
	public boolean isStarted() {
		return programConfigRepository.isStarted();
	}
	
	public void setStarted(boolean started) {
		programConfigRepository.setStarted(started);
	}
	
	public int getTimeRefreshPage() {
		return programConfigRepository.getTimeRefreshPage();
	}
	
	public void setTimeRefreshPage(int timeRefreshPage) {
		programConfigRepository.setTimeRefreshPage(timeRefreshPage);
	}

}
