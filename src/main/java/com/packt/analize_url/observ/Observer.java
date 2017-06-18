package com.packt.analize_url.observ;

import com.packt.analize_url.domain.MonitoringResult;

// listen events from the Subject
public interface Observer {
	public void update(MonitoringResult monitoringResult);
}
