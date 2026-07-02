package com.archiving.asset.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.asset.dto.ServerDto;
import com.archiving.asset.dto.ServerListParamDto;
import com.archiving.asset.dto.ServerSaveParamDto;
import com.archiving.asset.service.ServerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ServerController {

	private final ServerService serverService;
	private final ObjectMapper objectMapper;

	public ServerController(ServerService serverService, ObjectMapper objectMapper) {
		this.serverService = serverService;
		this.objectMapper = objectMapper;
	}

	/* 서버 콤보 조회 API(사용중 서버 목록) */
	/**
	 * Legacy combo helper ({@code common-js.js} {@code getServer}) — returns {@code { data: [{SERVER_ID, SERVER_NM}, ...] }}.
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetServerCombo", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getServerCombo(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		ServerListParamDto param = (paramJson == null || paramJson.isBlank())
			? new ServerListParamDto()
			: objectMapper.readValue(paramJson, ServerListParamDto.class);

		String sid = param.get__server_id();
		if (sid == null || sid.isBlank()) {
			sid = "*";
		}
		List<ServerDto> list = serverService.list(sid, "Y", null, null);

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (ServerDto s : list) {
			JSONObject d = new JSONObject();
			d.put("SERVER_ID", s.getServerId());
			d.put("SERVER_NM", s.getServerNm());
			data.add(d);
		}
		json.put("data", data);
		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(json.toString());
	}

	/* 서버 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetServerList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getServerList(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		ServerListParamDto param = (paramJson == null || paramJson.isBlank())
			? new ServerListParamDto()
			: objectMapper.readValue(paramJson, ServerListParamDto.class);

		int rows = safeInt(param.get__rows(), 20);
		int page = safeInt(param.get__page(), 1);

		List<ServerDto> list = serverService.list(
			param.get__server_id(),
			param.get__use_yn(),
			param.get__search_col(),
			param.get__search_val());

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		int nCount = 0;
		for (ServerDto s : list) {
			JSONObject datas = new JSONObject();
			datas.put("SERVER_ID", s.getServerId());
			datas.put("SERVER_NM", s.getServerNm());
			datas.put("USE_YN", s.getUseYn());
			datas.put("SERVER_CLASS_CD", s.getServerClassCd() == null ? " " : s.getServerClassCd());
			datas.put("SERVER_IP", s.getServerIp() == null ? " " : s.getServerIp());
			datas.put("SERVER_DESC", s.getServerDesc() == null ? " " : s.getServerDesc());
			datas.put("SERVER_CLASS_NM", s.getServerClassNm() == null ? " " : s.getServerClassNm());
			seriesArray.add(datas);
			nCount++;
		}

		jsonobj.put("rows", seriesArray);
		jsonobj.put("records", nCount);
		jsonobj.put("page", page);
		jsonobj.put("total", rows <= 0 ? 0 : (int) Math.ceil((double) nCount / rows));
		jsonobj.put("result", nCount > 0 ? "OK" : "NOTFOUND");

		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}

	/* 서버 저장 API(C/U/D 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetServer", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setServer(@RequestParam("param") String paramJson, HttpServletRequest req) throws Exception {
		ServerSaveParamDto p = objectMapper.readValue(paramJson, ServerSaveParamDto.class);
		serverService.save(p);

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "OK");
		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
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
}

