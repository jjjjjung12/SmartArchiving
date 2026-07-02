package com.archiving.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.auth.service.LoginService;

@RestController
public class LoginApiController {

	private final LoginService loginService;

	public LoginApiController(LoginService loginService) {
		this.loginService = loginService;
	}

	/* 로그인 처리 API(GET) */
	@GetMapping(value = "/GetLogin", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> login(HttpServletRequest request) {
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(loginService.login(request));
	}

	/* 로그아웃 처리 API(POST) */
	@PostMapping(value = "/GetLogin", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> logout(HttpServletRequest request) {
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(loginService.logout(request));
	}
}
