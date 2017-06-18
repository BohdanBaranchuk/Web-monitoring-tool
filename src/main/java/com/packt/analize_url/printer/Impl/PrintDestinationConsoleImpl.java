package com.packt.analize_url.printer.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.packt.analize_url.domain.MonitoringResult;
import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.printer.PrintDestinstion;

// print monitoring info into the console
@Service
public class PrintDestinationConsoleImpl implements PrintDestinstion{
	
	public void printMonitoringInfo(List <URLConfig> confs, List<MonitoringResult> res) {
		System.out.println("Configed URLs: \n");
		for(URLConfig uf: confs) {
			System.out.println(" - - - ");
			System.out.println(uf);
		}
		System.out.println("\n");
		
		System.out.println("Monitoring results: \n");
		for(MonitoringResult mr: res) {
			System.out.println(" # # # ");
			System.out.println(mr);
		}
		
		System.out.println("\n");
	}
}
