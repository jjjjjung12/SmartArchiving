package com.archiving.user.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.user.dto.UserInfoDto;
import com.archiving.user.dto.UserListParamDto;
import com.archiving.user.dto.UserSaveParamDto;
import com.archiving.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {

	private final UserService userService;
	private final ObjectMapper objectMapper;

	public UserController(UserService userService, ObjectMapper objectMapper) {
		this.userService = userService;
		this.objectMapper = objectMapper;
	}

	/* 사용자 목록 조회 API(파라미터 JSON → 페이징 응답 생성) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetUserList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getUserList(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		UserListParamDto param = (paramJson == null || paramJson.isBlank())
			? new UserListParamDto()
			: objectMapper.readValue(paramJson, UserListParamDto.class);

		String userNm = param.get__user_nm();
		int rows = safeInt(param.get__rows(), 20);
		int page = safeInt(param.get__page(), 1);

		List<UserInfoDto> list = userService.getUserList(userNm);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();

		int nCount = 0;
		for (UserInfoDto u : list) {
			JSONObject datas = new JSONObject();
			datas.put("USER_ID", u.getUserId());
			datas.put("USER_NM", u.getUserNm());
			datas.put("USER_CD", u.getUserCd());
			datas.put("USE_YN", u.getUseYn());
			datas.put("PASSWORD", u.getPassword());
			datas.put("PASSWORDORG", u.getPasswordOrg());
			datas.put("USER_GRP_ID", u.getUserGrpId() == null ? " " : u.getUserGrpId());
			datas.put("USER_GRP_NM", u.getUserGrpNm());
			datas.put("TELEPHONE", u.getTelephone());
			datas.put("EMAIL", u.getEmail());
			datas.put("APPROWAITCNT", u.getApprowaitcnt());
			datas.put("STATE_NM", u.getStateNm());
			datas.put("PICTURE", u.getPicture());
			datas.put("LOGIN_DATE", u.getLoginDate());
			datas.put("BRC", u.getBrc());
			datas.put("BRNM", u.getBrnm());
			datas.put("IP_ADDRESS", u.getIpAddress());
			datas.put("EXPIRE_DATE", u.getExpireDate());
			seriesArray.add(datas);
			nCount++;
		}

		jsonobj.put("rows", seriesArray);
		jsonobj.put("records", nCount);
		jsonobj.put("page", page);
		jsonobj.put("total", rows <= 0 ? 0 : (int) Math.ceil((double) nCount / rows));
		jsonobj.put("result", nCount > 0 ? "OK" : "NOTFOUND");

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			if (v == null) return def;
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}

	/* 사용자 저장 API(C/U/D 처리, 결과를 OK/ERR로 반환) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetUser", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setUser(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		UserSaveParamDto p = (paramJson == null || paramJson.isBlank())
			? new UserSaveParamDto()
			: objectMapper.readValue(paramJson, UserSaveParamDto.class);
		JSONObject jsonobj = new JSONObject();
		try {
			userService.saveUser(p);
			jsonobj.put("result", "OK");
			jsonobj.put("genKey", 0);
		} catch (Exception e) {
			jsonobj.put("result", "ERR");
			jsonobj.put("msg", e.getMessage());
		}
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}
}

