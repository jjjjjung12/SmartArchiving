package com.archiving.user.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.user.dto.UserGroupListParamDto;
import com.archiving.user.dto.UserGroupMemberItemDto;
import com.archiving.user.dto.UserGroupRowDto;
import com.archiving.user.dto.UserGroupSaveParamDto;
import com.archiving.user.service.UserGroupService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserGroupController {

	private final UserGroupService userGroupService;
	private final ObjectMapper objectMapper;

	public UserGroupController(UserGroupService userGroupService, ObjectMapper objectMapper) {
		this.userGroupService = userGroupService;
		this.objectMapper = objectMapper;
	}

	/* 사용자 그룹/멤버 목록 조회 API(__gb=A/B/C) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetUserGroup", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getUserGroup(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		UserGroupListParamDto param = (paramJson == null || paramJson.isBlank())
			? new UserGroupListParamDto()
			: objectMapper.readValue(paramJson, UserGroupListParamDto.class);

		String rowsStr = StringUtils.defaultString(param.get__rows(), "20");
		String pageStr = StringUtils.defaultString(param.get__page(), "1");
		int rows = safeInt(rowsStr, 20);
		int page = safeInt(pageStr, 1);

		List<UserGroupRowDto> list = userGroupService.list(param);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();

		int nCount = 0;
		for (UserGroupRowDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("USER_ID", blankToSpace(r.getUserId()));
			datas.put("USER_NM", blankToSpace(r.getUserNm()));
			datas.put("USER_GRP_ID", blankToSpace(r.getUserGrpId()));
			datas.put("USER_GRP_NM", blankToSpace(r.getUserGrpNm()));
			datas.put("DESCRIPTION", blankToSpace(r.getDescription()));
			datas.put("USE_YN", blankToSpace(r.getUseYn()));
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

	/* 사용자 그룹 멤버 일괄 저장(기존 멤버 삭제 후 재등록) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetUserGroupMember", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setUserGroupMember(
			@RequestParam("user_grp_id") String userGrpId,
			@RequestParam(value = "param", required = false) String paramJson) throws Exception {

		List<UserGroupMemberItemDto> members = null;
		if (paramJson != null && !paramJson.isBlank()) {
			members = objectMapper.readValue(paramJson, new TypeReference<List<UserGroupMemberItemDto>>() { });
		}

		userGroupService.replaceGroupMembers(userGrpId, members);

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "OK");
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 빈 문자열을 공백으로 치환(레거시 그리드 대응) */
	private static String blankToSpace(String v) {
		return StringUtils.isBlank(v) ? " " : v;
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private static int safeInt(String v, int def) {
		try {
			if (v == null) {
				return def;
			}
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}

	/* 사용자 그룹 저장 API(C/U 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetUserGroup", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setUserGroup(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		UserGroupSaveParamDto p = (paramJson == null || paramJson.isBlank())
			? new UserGroupSaveParamDto()
			: objectMapper.readValue(paramJson, UserGroupSaveParamDto.class);
		userGroupService.saveUserGroup(p);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "OK");
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}
}
