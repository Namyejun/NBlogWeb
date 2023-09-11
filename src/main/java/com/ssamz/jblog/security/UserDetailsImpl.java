package com.ssamz.jblog.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ssamz.jblog.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails, OAuth2User {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// 구글에서 조회한 사용자 정보를 담을 컬렉션
	private Map<String, Object> attributes;
	
	// 일반 생성자
	public UserDetailsImpl(User user) {
		this.user = user;
	}
	
	// OAuth 로그인 시 사용할 생성자
	public UserDetailsImpl(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	// 계정이 가지고 있는 권한 목록 저장하여 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 목록
		Collection<GrantedAuthority> roleList = new ArrayList<>();
		
		// 권한 목록 설정
		roleList.add(() -> {return "ROLE_" + user.getRole();});
		return roleList;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	// 계정 만료?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	// 계정 잠김?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	// 비번 만료?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 계정 활성화?
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 구글에서 조회한 사용자 정보가 저장된 컬렉션 반환
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	// 이름은 사용하지 않는 정보이므로 null을 반환한다.
	@Override
	public String getName() {
		return null;
	}

}
