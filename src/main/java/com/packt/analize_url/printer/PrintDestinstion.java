package com.packt.analize_url.printer;

import java.util.List;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.URLConfig;

// print monitoring info
public interface PrintDestinstion {
	
	void printMonitoringInfo(List <URLConfig> confs,List<MonitoringResult> res);

}
