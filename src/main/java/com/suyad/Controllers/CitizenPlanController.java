package com.suyad.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;

import com.suyad.Bindings.SearchCriteria;
import com.suyad.entity.CitizenPlan;
import com.suyad.services.CitizenPlanService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CitizenPlanController 
{
	@Autowired
	private CitizenPlanService service;
	
	@GetMapping("/")
	public String index(Model model)
	{
		System.out.println("index method processed");
		extracted(model);
		model.addAttribute("search", new SearchCriteria());
		return "index";
	}
	
	@PostMapping("/filter-data")
	public String handleSearchButton(@ModelAttribute("search")SearchCriteria criteria,Model model)
	{
		System.out.println("handleSearchButton processed....................");
		System.out.println(criteria);
		extracted(model);
		List<CitizenPlan> citizensInfo = service.searchCitizens(criteria);
		model.addAttribute("citizens", citizensInfo);
		//model.addAttribute("search", new SearchCriteria());
		return "index";
	}
	
	@GetMapping("/excel")
	public void downloadExcel(HttpServletResponse response)
	{
		response.setContentType("application/octet-stream");
		String headerKey = "content-Disposition";
		String headerValue = "attachment;filename=data.xls";
		
		response.addHeader(headerKey, headerValue);
		
		service.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void downloadPdf(HttpServletResponse response)
	{
		response.setContentType("application/pdf");
		String headerKey = "content-Disposition";
		String headerValue = "attachment;filename=data.pdf";
		
		response.addHeader(headerKey, headerValue);
		
		service.generatePdf(response);
	}

	private void extracted(Model model) 
	{
		model.addAttribute("msg", "welcome to Citizen plan Portel");
		List<String> planNames = service.getPlanNames();
		model.addAttribute("planNames", planNames);
		System.out.println("----------------------------------------------");
		List<String> planStatus = service.getPlanStatus();
		model.addAttribute("planStatus", planStatus);
	}
	
	@GetMapping("/wel")
	public String welcomePage(@RequestParam("id")Integer id,@RequestParam("name")String name,Model model)
	{
		System.out.println("Id = "+id);
		System.out.println("Name = "+name);
		return "welcome";
	}
}
