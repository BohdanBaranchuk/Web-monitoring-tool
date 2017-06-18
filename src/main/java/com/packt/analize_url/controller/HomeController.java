package com.packt.analize_url.controller;

import java.io.*;
import java.net.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	// redirect to monitoring page
	@RequestMapping("/")
	public String welcome(Model model) {
		return "redirect:/monitoring";
	}
}
