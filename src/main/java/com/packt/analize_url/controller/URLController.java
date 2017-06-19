package com.packt.analize_url.controller;

import java.io.File;
import java.net.URL;
import java.util.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.packt.analize_url.domain.*;
import com.packt.analize_url.domain.configpart.*;
import com.packt.analize_url.domain.repository.*;
import com.packt.analize_url.domain.resultpart.ResultStatus;
import com.packt.analize_url.hibernate.dao.*;
import com.packt.analize_url.printer.PrintDestinstion;
import com.packt.analize_url.scheduler.ScheduledTasks;
import com.packt.analize_url.service.*;
import com.packt.analize_url.service.impl.ProgramConfigserviceImpl;
import com.packt.analize_url.validation.Validator;


@Controller
public class URLController {
	

	// all URL configs
	@Autowired
	private URLConfigService urlConfigService;

	// all monitoring results
	@Autowired
	private ResultMonitoringService resultMonitoringService;
	
	// parameters of the program (run/stop and autorefresh page time)
	@Autowired
	private ProgramConfigservice programConfigserviceImpl;
	
	// print result into console
	@Autowired
	private PrintDestinstion printDestinstion;
	
	// check if new or editable URL has correct parameters 
	@Autowired
	private Validator validator;
	
	// in edit mode save URL id between get and post requests
	private String idForValidation;
	
	
	// main page
	@RequestMapping("/monitoring")
	public String listMonitoring(Model model) {
		baseModel (model);
		
		// autorefresh page time
		model.addAttribute("timeout",programConfigserviceImpl.getTimeRefreshPage());
		
		// different signal if page status critical or warning
		if(commonPageStatus() == ResultStatus.CRITICAL) {
			model.addAttribute("play",2);
		} else if (commonPageStatus() == ResultStatus.WARNING) {
			model.addAttribute("play",1);
		} else {
			model.addAttribute("play",0);
		}
		
		//printDestinstion.printMonitoringInfo(urlConfigService.getAllURLConfigs(), resultMonitoringService.getAllResults());
		
		return "main";
	}

	// exclude selected URL from the automatic monitoring
	@RequestMapping("pause/{ID}")
	public String pauseURLMonitoringById(@PathVariable("ID") String id) {
		urlConfigService.pauseURLConfig(id);
		restartRun();
		
		return "redirect:/monitoring";
	}
	
	// delete selected URL from configuration
	@RequestMapping("delete/{ID}")
	public String deleteURLMonitoringById(@PathVariable("ID") String id) {
		resultMonitoringService.deleteTaskMonitoring(urlConfigService.readURLConfig(id));
		resultMonitoringService.removeMonitoringResult(id);
		urlConfigService.deleteURLConfig(id);
		
		return "redirect:/monitoring";
	}


	// show detail page for selected URL
	@RequestMapping("/details")
	public String showDetails(@RequestParam("id") String id, Model model) {
		baseModel(model);
		model.addAttribute("monitoringURLDetails", resultMonitoringService.getDetails(id));
		model.addAttribute("monitoringId", id);
				
		return "main";
	}

	// request to show add subpage 
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewUrlConfigForm(Model model) {
		baseModel(model);
		URLConfig urlConfig = new URLConfig();
		model.addAttribute("newURLConfig", urlConfig);
		model.addAttribute("showForm", "Add");

		return "main";
	}

	// create new URL and add it
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewUrlConfigForm(@ModelAttribute("newURLConfig") URLConfig urlConfig) {
		if(validator.validateNewURLConfigAdd(urlConfig, urlConfigService.getAllURLConfigs())) {
			urlConfigService.addURLConfig(urlConfig);
			resultMonitoringService.addTaskMonitoring(urlConfig);
		}
		
		return "redirect:/monitoring";
	}
	

	// request to show edit subpage
	@RequestMapping("edit/{ID}")
	public String editUrlConfigForm(@PathVariable("ID") String id, Model model) {
		baseModel(model);
		URLConfig urlConfig = urlConfigService.readURLConfig(id);
		model.addAttribute("editURLConfig", urlConfig);
		model.addAttribute("showForm", "Edit");
		idForValidation = id;

		return "main";
	}
	
	// edit exist URL
	@RequestMapping(value = "/edit/{any}", method = RequestMethod.POST)
	public String processEditUrlConfigForm(@ModelAttribute("editURLConfig") URLConfig urlConfig) {
		if(validator.validateNewURLConfigEdit(idForValidation, urlConfig)) {
			urlConfigService.updateURLConfig(urlConfig);
			resultMonitoringService.changeTaskMonitoring(urlConfig);
		}
		
		return "redirect:/monitoring";
	}
	
	// start automatic monitoring of the URLs
	@RequestMapping("/run")
	public String runMonitoring(Model model) {
		resultMonitoringService.runMonitoring(urlConfigService.getAllURLConfigs());
		programConfigserviceImpl.setStarted(true);
		
		return "redirect:/monitoring";
	}
	
	// stop automatic monitoring of the URLs
	@RequestMapping("/stop")
	public String stopMonitoring(Model model) {
		resultMonitoringService.stopMonitoring();
		programConfigserviceImpl.setStarted(false);
		
		return "redirect:/monitoring";
	}
	
	// manual change refresh time
	@RequestMapping("refresh")
	public String changeRefreshTimeURLMonitoring(@RequestParam("time") String timeNew, Model model) {
		programConfigserviceImpl.setTimeRefreshPage(Integer.parseInt(timeNew));
		
		return "redirect:/monitoring";
	}
	
	// add basic parameters to the model
	private void baseModel (Model model) {
		model.addAttribute("prStatus", programConfigserviceImpl.isStarted());
		model.addAttribute("timeoutText", programConfigserviceImpl.getTimeRefreshPage() / 1000);
		model.addAttribute("activeMonitoredURLs", urlConfigService.getActiveMonitored());
		model.addAttribute("resultURLs", resultMonitoringService.getAllResults());
		model.addAttribute("monitoringURLs", urlConfigService.getAllURLConfigs());
	}
	
	// check common page status
	private ResultStatus commonPageStatus() {
		
		ResultStatus result = ResultStatus.OK;
		
		if(programConfigserviceImpl.isStarted())
		{
			for(URLConfig ucf : urlConfigService.getAllURLConfigs())
			{
				if(ucf.isMonitored()) {
					MonitoringResult mr = resultMonitoringService.getMonitoringResultById(ucf.getUrlId());
					if(mr != null) {
						if(mr.getResultStatus() == ResultStatus.CRITICAL) {
							return ResultStatus.CRITICAL;
						} else if(mr.getResultStatus() == ResultStatus.WARNING) {
							result = ResultStatus.WARNING;
						}
					}
				}
			}
			
		}
		
		return result;
	}

	// restart tasks
	private void restartRun() {
		if(programConfigserviceImpl.isStarted()) {
			resultMonitoringService.stopMonitoring();
			
			resultMonitoringService.runMonitoring(urlConfigService.getAllURLConfigs());
		}
	}
	
	// automatic start monitoring if needed
	@PostConstruct
	private void initIt() throws Exception {
		if(programConfigserviceImpl.isStarted()) {
			resultMonitoringService.runMonitoring(urlConfigService.getAllURLConfigs());
		}
	}
}
