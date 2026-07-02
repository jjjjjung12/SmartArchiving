package com.archiving.asset.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.asset.dto.AgentDto;
import com.archiving.asset.dto.AgentListParamDto;
import com.archiving.asset.dto.AgentSaveParamDto;
import com.archiving.asset.service.AgentService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class AgentController {
	private final AgentService agentService;
	private final JsonParamBinder jsonParamBinder;

	public AgentController(AgentService agentService, JsonParamBinder jsonParamBinder) {
		this.agentService = agentService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 에이전트 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetAgentList")
	public String getAgentList(@RequestParam(value = "param", required = false) String param) throws Exception {
		AgentListParamDto p = jsonParamBinder.bind(param, AgentListParamDto.class);
		String serverId = p == null ? "" : StringUtils.defaultString(p.get__server_id());
		String agentId = p == null ? "" : StringUtils.defaultString(p.get__agent_id());
		String useYn = p == null ? "" : StringUtils.defaultString(p.get__use_yn());
		String rows = p == null ? "10" : StringUtils.defaultString(p.get__rows(), "10");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		List<AgentDto> list = agentService.list(serverId, agentId, useYn);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		jsonobj.put("result", "ERROR");

		for (AgentDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("SERVER_ID", StringUtils.defaultString(r.getServerId()));
			datas.put("AGENT_ID", StringUtils.defaultString(r.getAgentId()));
			datas.put("AGENT_NM", StringUtils.defaultString(r.getAgentNm()));
			datas.put("AGENT_PORT", StringUtils.defaultString(r.getAgentPort()));
			datas.put("SERVER_NM", StringUtils.isEmpty(r.getServerNm()) ? " " : r.getServerNm());
			datas.put("ACCOUNT_CD", StringUtils.isEmpty(r.getAccountCd()) ? " " : r.getAccountCd());
			datas.put("PASSWORD", StringUtils.isEmpty(r.getPassword()) ? " " : r.getPassword());
			datas.put("PATH", StringUtils.isEmpty(r.getPath()) ? " " : r.getPath());
			datas.put("DESCRIPTION", StringUtils.isEmpty(r.getDescription()) ? " " : r.getDescription());
			datas.put("RUN_CD", StringUtils.isEmpty(r.getRunCd()) ? " " : r.getRunCd());
			datas.put("RUN_NM", StringUtils.isEmpty(r.getRunNm()) ? " " : r.getRunNm());
			datas.put("USE_YN", StringUtils.isEmpty(r.getUseYn()) ? " " : r.getUseYn());
			seriesArray.add(datas);
		}

		jsonobj.put("rows", seriesArray);

		int nCount = list.size();
		if (nCount > 0) {
			int total = nCount / Integer.parseInt(rows);
			jsonobj.put("records", nCount);
			jsonobj.put("page", Integer.parseInt(page));
			jsonobj.put("total", total);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NOTFOUND");
		}

		return jsonobj.toString();
	}

	/* 에이전트 모니터링 조회 API(원격 헬스체크 포함) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetAgentMonitorList")
	public String getAgentMonitorList(@RequestParam(value = "param", required = false) String param) throws Exception {
		AgentListParamDto p = jsonParamBinder.bind(param, AgentListParamDto.class);
		String serverId = p == null ? "" : StringUtils.defaultString(p.get__server_id());
		String agentId = p == null ? "" : StringUtils.defaultString(p.get__agent_id());
		String useYn = p == null ? "" : StringUtils.defaultString(p.get__use_yn());
		String rows = p == null ? "10" : StringUtils.defaultString(p.get__rows(), "10");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		List<AgentDto> list = agentService.list(serverId, agentId, useYn);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		jsonobj.put("result", "ERROR");

		for (AgentDto r : list) {
			String ip = StringUtils.defaultString(r.getServerIp());
			String port = StringUtils.defaultString(r.getAgentPort());

			String healthUrl = "";
			String health = "UNKNOWN";
			String healthCode = "";
			String healthBody = "";
			if (!ip.isBlank() && !port.isBlank()) {
				String actuator = "http://" + ip + ":" + port + "/actuator/health";
				String plain = "http://" + ip + ":" + port + "/health";
				HealthCheckResult r1 = checkHealth(actuator);
				if (r1.up) {
					health = "UP";
					healthUrl = actuator;
					healthCode = String.valueOf(r1.code);
					healthBody = r1.body;
				} else {
					HealthCheckResult r2 = checkHealth(plain);
					if (r2.up) {
						health = "UP";
						healthUrl = plain;
						healthCode = String.valueOf(r2.code);
						healthBody = r2.body;
					} else {
						health = "DOWN";
						healthUrl = actuator;
						healthCode = String.valueOf(r1.code);
						healthBody = r1.body;
					}
				}
			}

			JSONObject datas = new JSONObject();
			datas.put("SERVER_ID", StringUtils.defaultString(r.getServerId()));
			datas.put("SERVER_NM", StringUtils.isEmpty(r.getServerNm()) ? " " : r.getServerNm());
			datas.put("SERVER_IP", ip);
			datas.put("AGENT_ID", StringUtils.defaultString(r.getAgentId()));
			datas.put("AGENT_NM", StringUtils.defaultString(r.getAgentNm()));
			datas.put("AGENT_PORT", port);
			datas.put("RUN_CD", StringUtils.isEmpty(r.getRunCd()) ? " " : r.getRunCd());
			datas.put("RUN_NM", StringUtils.isEmpty(r.getRunNm()) ? " " : r.getRunNm());
			datas.put("USE_YN", StringUtils.isEmpty(r.getUseYn()) ? " " : r.getUseYn());
			datas.put("DESCRIPTION", StringUtils.isEmpty(r.getDescription()) ? " " : r.getDescription());

			datas.put("HEALTH", health);
			datas.put("HEALTH_URL", healthUrl);
			datas.put("HEALTH_CODE", healthCode);
			datas.put("HEALTH_BODY", healthBody);

			seriesArray.add(datas);
		}

		jsonobj.put("rows", seriesArray);

		int nCount = list.size();
		if (nCount > 0) {
			int total = nCount / Integer.parseInt(rows);
			jsonobj.put("records", nCount);
			jsonobj.put("page", Integer.parseInt(page));
			jsonobj.put("total", total);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NOTFOUND");
		}

		return jsonobj.toString();
	}

	/* 에이전트 저장 API(C/U/D 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetAgent")
	@Transactional
	public String setAgent(@RequestParam(value = "param", required = false) String param) throws Exception {
		AgentSaveParamDto p = jsonParamBinder.bind(param, AgentSaveParamDto.class);
		JSONObject jsonobj = new JSONObject();
		try {
			agentService.save(p);
			jsonobj.put("result", "OK");
		} catch (Exception e) {
			jsonobj.put("result", "ERROR");
			throw e;
		}
		return jsonobj.toString();
	}

	private static final class HealthCheckResult {
		final boolean up;
		final int code;
		final String body;

		private HealthCheckResult(boolean up, int code, String body) {
			this.up = up;
			this.code = code;
			this.body = body;
		}
	}

	private HealthCheckResult checkHealth(String url) {
		try {
			java.net.URL u = new java.net.URL(url);
			java.net.HttpURLConnection c = (java.net.HttpURLConnection) u.openConnection();
			c.setConnectTimeout(1000);
			c.setReadTimeout(1000);
			c.setRequestMethod("GET");
			int code = c.getResponseCode();

			java.io.InputStream in = null;
			try {
				in = (code >= 200 && code < 400) ? c.getInputStream() : c.getErrorStream();
			} catch (Exception ignore) {
			}
			String body = "";
			if (in != null) {
				body = readBody(in, 2000);
			}
			boolean up = code >= 200 && code < 300;
			return new HealthCheckResult(up, code, body);
		} catch (Exception e) {
			return new HealthCheckResult(false, 0, e.getMessage());
		}
	}

	private String readBody(java.io.InputStream in, int maxChars) {
		try (java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(in))) {
			StringBuilder sb = new StringBuilder(Math.min(maxChars, 1024));
			int ch;
			while ((ch = r.read()) != -1) {
				if (sb.length() >= maxChars) break;
				sb.append((char) ch);
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}
}

