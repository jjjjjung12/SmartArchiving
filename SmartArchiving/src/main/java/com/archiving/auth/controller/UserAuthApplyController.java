package com.archiving.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.auth.dto.UserAuthApplyParamDto;
import com.archiving.auth.dto.UserAuthApplySubmitDto;
import com.archiving.auth.service.UserAuthApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserAuthApplyController {

	private final UserAuthApplyService service;
	private final ObjectMapper objectMapper;

	public UserAuthApplyController(UserAuthApplyService service, ObjectMapper objectMapper) {
		this.service = service;
		this.objectMapper = objectMapper;
	}

	/* 사용자 권한 신청 화면 초기 데이터 조회 API(신청ID/만료일/IP 등) */
	@GetMapping(value = "/GetUserAuthApply", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getUserAuthApply(@RequestParam(value = "param", required = false) String paramJson,
		HttpServletRequest request) throws Exception {

		UserAuthApplyParamDto p = (paramJson == null || paramJson.isBlank())
			? new UserAuthApplyParamDto()
			: objectMapper.readValue(paramJson, UserAuthApplyParamDto.class);

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(service.getApplyInfo(p.getUser_cd(), request));
	}

	/* 사용자 권한 신청 제출 API(결재요청/결재라인 등록) */
	@PostMapping(value = "/SetUserAuthApply", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setUserAuthApply(@RequestParam("param") String paramJson) throws Exception {
		UserAuthApplySubmitDto dto = objectMapper.readValue(paramJson, UserAuthApplySubmitDto.class);
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(service.submit(dto));
	}
}

