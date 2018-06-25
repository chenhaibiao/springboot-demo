package com.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.Message;
import com.springboot.service.IMessageService;

@Controller 
public class HelloController { 
	
	private final Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	@Autowired 
	@Qualifier("messageService")
	private IMessageService messageService; 

	@RequestMapping("/message")
	@ResponseBody
	public String message(Model model){ 
		Message message = messageService.selectByPrimaryKey(1);
		logger.debug("message: {}", message.toString());
		return "message"; 
	} 
	@RequestMapping("/hello") 
	public ModelAndView hello(HttpServletRequest request, HttpServletResponse response){ 
		ModelAndView mav = new ModelAndView("HelloHtml");
		mav.addObject("hello", "hello springboot");
		return mav;
	}
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response){
		messageService.insert();
		Message message = messageService.selectByPrimaryKey(2);
		ModelAndView mav = new ModelAndView("HelloHtml");
		mav.addObject("nickName", message.getNickName());
		mav.addObject("id", message.getId());
		return mav;
	}
}

