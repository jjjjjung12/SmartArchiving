package com.archiving.user.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.user.dto.UserLogListParamDto;
import com.archiving.user.dto.UserLogRowDto;
import com.archiving.user.service.UserLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserLogController {

	private final UserLogService userLogService;
	private final ObjectMapper objectMapper;

	public UserLogController(UserLogService userLogService, ObjectMapper objectMapper) {
		this.userLogService = userLogService;
		this.objectMapper = objectMapper;
	}

	/* 사용자 로그 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetUserLogList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getUserLogList(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		UserLogListParamDto param = (paramJson == null || paramJson.isBlank())
			? new UserLogListParamDto()
			: objectMapper.readValue(paramJson, UserLogListParamDto.class);

		int rows = safeInt(param.get__rows(), 100);
		int page = safeInt(param.get__page(), 1);
		if (rows <= 0) {
			rows = 100;
		}
		if (page <= 0) {
			page = 1;
		}
		int offset = (page - 1) * rows;

		int totalRows = userLogService.count(param);
		List<UserLogRowDto> list = userLogService.list(param, rows, offset);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();

		for (UserLogRowDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("LOG_ID", r.getLogId() != null ? String.valueOf(r.getLogId()) : "");
			datas.put("ACTION_TIME", nvl(r.getActionTime()));
			datas.put("USER_NAME", nvl(r.getUserName()));
			datas.put("PROGRAM_NM", nvl(r.getProgramNm()));
			datas.put("ACTION_DATE", nvl(r.getActionDate()));
			datas.put("GROUP_ID", r.getGroupId() != null ? String.valueOf(r.getGroupId()) : "");
			datas.put("USER_ID", r.getUserId() != null ? String.valueOf(r.getUserId()) : "");
			datas.put("USER_CD", nvl(r.getUserCd()));
			datas.put("PROGRAM_ID", nvl(r.getProgramId()));
			datas.put("PROGRAM_WHERE", nvl(r.getProgramWhere()));
			seriesArray.add(datas);
		}

		jsonobj.put("rows", seriesArray);
		jsonobj.put("records", totalRows);
		jsonobj.put("page", page);
		jsonobj.put("total", rows <= 0 ? 0 : (int) Math.ceil((double) totalRows / rows));
		jsonobj.put("result", totalRows > 0 ? "OK" : "NOTFOUND");

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* null 문자열을 빈 문자열로 치환 */
	private static String nvl(String s) {
		return s == null ? "" : s;
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			if (v == null || v.isBlank()) {
				return def;
			}
			return Integer.parseInt(v.trim());
		} catch (Exception e) {
			return def;
		}
	}
}
