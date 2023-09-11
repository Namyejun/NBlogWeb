package com.ssamz.jblog.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ssamz.jblog.domain.OAuthType;
import com.ssamz.jblog.domain.RoleType;
import com.ssamz.jblog.domain.User;

@Service
public class KakaoLoginService {
	@Value("${kakao.default.password}")
	private String kakaoPassword;
	
	public String getAccessToken(String code) {
		// HttpHeaders 생성(MIME 종류)
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 생성(4개의 필수 파라미터 설정)
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "7c869b3c5b5cabc901beb88b6db949f1");
		body.add("redirect_uri", "http://nblogweb-env.eba-viqqnudd.us-east-2.elasticbeanstalk.com/oauth/kakao");
		body.add("code", code);
		
		// HttpHeaders와 HttpBody가 설정된 HttpEntity 생성
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, header);
		
		// RestTemplate을 이용하면 브라우저 없이 HTTP 요청을 할 수 있다.
		RestTemplate restTemplate = new RestTemplate();
		
		// Http 요청 및 응답 받기
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"https://kauth.kakao.com/oauth/token", // 액세스 토큰 요청 주소
				HttpMethod.POST, // 요청 방식
				requestEntity, // 요청 헤더와 바디
				String.class // 응답받을 방식
				);
		
		// HTTP 응답 본문 반환
		String jsonData =  responseEntity.getBody();
		
		// JSON 데이터에서 액세스 토큰 추출
		Gson gsonObj = new Gson();
		Map<?, ?> data = gsonObj.fromJson(jsonData, Map.class);
		
		return (String) data.get("access_token");
	}
	
	public User getUserInfo(String accessToken) {
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + accessToken);
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(header);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, requestEntity, String.class);
		
		String userInfo =  responseEntity.getBody();
		
		Gson gsonObj = new Gson();
		Map<?, ?> data =  gsonObj.fromJson(userInfo, Map.class);
		
		Double id = (Double) data.get("id");
		String nickname = (String) ((Map<?, ?>) data.get("properties")).get("nickname");
		String email = (String) ((Map<?, ?>) data.get("kakao_account")).get("email");
		
		User user = new User();
		user.setUsername(email);
		user.setPassword(kakaoPassword);
		user.setEmail(email);
		user.setRole(RoleType.USER);
		user.setOauth(OAuthType.KAKAO);
		
		return user;
	}
}
