package com.ssamz.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.service.KakaoLoginService;
import com.ssamz.jblog.service.UserService;

@Controller
public class KakaoLoginController {
	@Autowired
	private KakaoLoginService kakaoLoginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${kakao.default.password}")
	private String kakaoPassword;
	
	@GetMapping("/oauth/kakao")
	public String kakaoCallback(String code) {
		// 1. 인증 서버로부터 받은 CODE를 이용하여 액세스 토큰을 받는다.
		String accessToken = kakaoLoginService.getAccessToken(code);

		// 2. 액세스 토큰을 이용해 사용자 정보를 얻어온다.
		User kakaoUser = kakaoLoginService.getUserInfo(accessToken);
		
		// 3. 기존 회원이 아니면 신규 회원으로 등록한다.
		User findUser = userService.getUser(kakaoUser.getUsername());
		if (findUser.getUsername() == null) {
			userService.insertUser(kakaoUser);
		}
		
		// 4. 카카오로부터 받은 사용자 정보를 기반으로 인증을 처리한다.
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoPassword);
		
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
}
