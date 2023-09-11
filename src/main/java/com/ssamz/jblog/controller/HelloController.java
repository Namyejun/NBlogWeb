package com.ssamz.jblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@GetMapping("/html")
	public String html() {
		System.out.println("HTML 파일이 요청됨");
		return "redirect:hello.html";
	}

	@GetMapping("/image")
	public String image() throws UnsupportedEncodingException {
		System.out.println("이미지 파일이 요청됨");
		String encodedParam = URLEncoder.encode("땅땅이", "UTF-8");
		return "redirect:image/" + encodedParam +".png";
	}
	
	@GetMapping("/jsp")
	public String jsp(Model model) {
		System.out.println("JSP 파일이 요청됨");
		model.addAttribute("username", "쌤즈");
		return "hello";
	}
	
}
