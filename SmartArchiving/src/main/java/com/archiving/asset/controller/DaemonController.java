package com.archiving.asset.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.asset.dto.DaemonDto;
import com.archiving.asset.dto.DaemonListParamDto;
import com.archiving.asset.dto.DaemonSaveParamDto;
import com.archiving.asset.service.DaemonService;
import com.archiving.mvc.util.JsonParamBinder;
import com.archiving.util.UtilClass;

@RestController
public class DaemonController {
	private final DaemonService daemonService;
	private final JsonParamBinder jsonParamBinder;

	public DaemonController(DaemonService daemonService, JsonParamBinder jsonParamBinder) {
		this.daemonService = daemonService;
		this.jsonParamBinder = jsonParamBinder;
	}

	private static final Pattern PORT_PATTERN = Pattern.compile(
		"(?:--server\\.port=|-Dserver\\.port=)(\\d{2,5})"
	);

	/* 데몬 목록 조회 API(상태/프로세스 체크 포함) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetDaemonList_bak")
	public String getDaemonList_bak(@RequestParam(value = "param", required = false) String param) throws Exception {
		DaemonListParamDto p = jsonParamBinder.bind(param, DaemonListParamDto.class);
		String daemonId = p == null ? "" : StringUtils.defaultString(p.get__daemon_id());
		String useYn = p == null ? "" : StringUtils.defaultString(p.get__use_yn());
		String rows = p == null ? "10" : StringUtils.defaultString(p.get__rows(), "10");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		List<DaemonDto> list = daemonService.list(daemonId, useYn, null, null);
		UtilClass utilClass = new UtilClass();

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		jsonobj.put("result", "ERROR");

		for (DaemonDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("DAEMON_ID", StringUtils.defaultString(r.getDaemonId()));
			datas.put("DAEMON_CD", StringUtils.defaultString(r.getDaemonCd()));
			datas.put("DAEMON_NM", StringUtils.defaultString(r.getDaemonNm()));
			datas.put("DAEMON_RESTART_YN", StringUtils.defaultString(r.getDaemonRestartYn()));
			datas.put("DAEMON_USE_YN", StringUtils.defaultString(r.getDaemonUseYn()));
			datas.put("DAEMON_START_PATH", StringUtils.defaultString(r.getDaemonStartPath()));
			datas.put("DAEMON_STOP_PATH", StringUtils.defaultString(r.getDaemonStopPath()));
			datas.put("DAEMON_RESTART_PATH", StringUtils.defaultString(r.getDaemonRestartPath()));
			datas.put("DAEMON_DESC", StringUtils.isEmpty(r.getDaemonDesc()) ? " " : r.getDaemonDesc());

			datas.put("START", "0");
			datas.put("STOP", "0");

			// legacy behavior: if proc/heartbeat missing => force stat to stopped
			if (!utilClass.getProcInfo(r.getDaemonCd())) {
				datas.put("DAEMON_STAT_NM", "작동중지");
				datas.put("DAEMON_STAT_CD", "0");
			} else {
				datas.put("DAEMON_STAT_NM", StringUtils.defaultString(r.getDaemonStatNm()));
				datas.put("DAEMON_STAT_CD", StringUtils.defaultString(r.getDaemonStatCd()));
			}

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

	/* 데몬 모니터링 목록 조회 API(프로세스 상세 + 헬스체크 포함) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetDaemonList")
	public String getDaemonList(@RequestParam(value = "param", required = false) String param) throws Exception {
		DaemonListParamDto p = jsonParamBinder.bind(param, DaemonListParamDto.class);
		String daemonId = p == null ? "" : StringUtils.defaultString(p.get__daemon_id());
		String useYn = p == null ? "" : StringUtils.defaultString(p.get__use_yn());
		String searchCol = p == null ? "" : StringUtils.defaultString(p.get__search_col());
		String searchVal = p == null ? "" : StringUtils.defaultString(p.get__search_val());
		String rows = p == null ? "10" : StringUtils.defaultString(p.get__rows(), "10");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		List<DaemonDto> list = daemonService.list(daemonId, useYn, searchCol, searchVal);
		UtilClass utilClass = new UtilClass();

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		jsonobj.put("result", "ERROR");

		for (DaemonDto r : list) {
			String daemonCd = StringUtils.defaultString(r.getDaemonCd());
			ProcSnapshot proc = findJavaProcByKeyword(daemonCd);

			JSONObject datas = new JSONObject();
			datas.put("DAEMON_ID", StringUtils.defaultString(r.getDaemonId()));
			datas.put("DAEMON_CD", daemonCd);
			datas.put("DAEMON_NM", StringUtils.defaultString(r.getDaemonNm()));
			datas.put("DAEMON_USE_YN", StringUtils.defaultString(r.getDaemonUseYn()));
			datas.put("DAEMON_RESTART_YN", StringUtils.defaultString(r.getDaemonRestartYn()));
			datas.put("DAEMON_DESC", StringUtils.isEmpty(r.getDaemonDesc()) ? " " : r.getDaemonDesc());
			datas.put("DAEMON_START_PATH", StringUtils.defaultString(r.getDaemonStartPath()));
			datas.put("DAEMON_STOP_PATH", StringUtils.defaultString(r.getDaemonStopPath()));
			datas.put("DAEMON_RESTART_PATH", StringUtils.defaultString(r.getDaemonRestartPath()));
			datas.put("DAEMON_STAT_CD", StringUtils.defaultString(r.getDaemonStatCd()));

			boolean running = proc != null;
			datas.put("PROC_RUNNING", running ? "Y" : "N");
			datas.put("PROC_PID", running ? StringUtils.defaultString(proc.pid) : "");
			datas.put("PROC_CMD", running ? StringUtils.defaultString(proc.cmdline) : "");

			if (!running) {
				datas.put("HEALTH", "DOWN");
				datas.put("HEALTH_URL", "");
				datas.put("PORT", "");
				datas.put("DAEMON_STAT_NM", "작동중지");
				datas.put("DAEMON_STAT_CD", "0");
			} else {
				String port = extractPort(proc.cmdline);
				datas.put("PORT", port);
				if (StringUtils.isBlank(port)) {
					datas.put("HEALTH", "UNKNOWN");
					datas.put("HEALTH_URL", "");
				} else {
					String healthUrl = "http://localhost:" + port + "/actuator/health";
					String health = checkHealth(healthUrl) ? "UP" : "DOWN";
					// Fallback for non-actuator daemons
					if ("DOWN".equals(health)) {
						String alt = "http://localhost:" + port + "/health";
						if (checkHealth(alt)) {
							healthUrl = alt;
							health = "UP";
						}
					}
					datas.put("HEALTH", health);
					datas.put("HEALTH_URL", healthUrl);
				}

				// keep legacy stat label if proc is up
				datas.put("DAEMON_STAT_NM", StringUtils.defaultString(r.getDaemonStatNm(), "작동중"));
				datas.put("DAEMON_STAT_CD", StringUtils.defaultString(r.getDaemonStatCd(), "1"));
			}

			// legacy behavior still used by daemonInfo: expose START/STOP placeholders
			datas.put("START", "0");
			datas.put("STOP", "0");

			// Also preserve legacy proc boolean for clients that rely on it
			datas.put("PROC_EXISTS", utilClass.getProcInfo(daemonCd) ? "Y" : "N");

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

	/* 데몬 저장/명령 실행 API(START/STOP/RESTART 또는 CRUD) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetDaemon")
	@Transactional
	public String setDaemon(@RequestParam(value = "param", required = false) String param) throws Exception {
		DaemonSaveParamDto p = jsonParamBinder.bind(param, DaemonSaveParamDto.class);
		JSONObject jsonobj = new JSONObject();
		try {
			String crud = p == null ? "" : StringUtils.defaultString(p.getCrud());
			if ("START".equalsIgnoreCase(crud) || "STOP".equalsIgnoreCase(crud) || "RESTART".equalsIgnoreCase(crud)) {
				return daemonService.executeCommand(p).toString();
			}

			daemonService.save(p);
			jsonobj.put("result", "OK");
			jsonobj.put("genKey", 0);
		} catch (Exception e) {
			jsonobj.put("result", "ERROR");
			jsonobj.put("msg", e.getMessage());
			throw e;
		}
		return jsonobj.toString();
	}

	private static final class ProcSnapshot {
		final String pid;
		final String cmdline;
		ProcSnapshot(String pid, String cmdline) {
			this.pid = pid;
			this.cmdline = cmdline;
		}
	}

	private ProcSnapshot findJavaProcByKeyword(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			return null;
		}
		
		// 검증 (영문자, 숫자, 하이픈, 언더스코어, 점만 허용)
	    if (!keyword.matches("[a-zA-Z0-9\\-_\\.]+")) {
	        return null;
	    }
	    
	    try {
	        // 셸 우회 — ProcessBuilder로 직접 실행 후 Java에서 필터링
	        ProcessBuilder pb = new ProcessBuilder("ps", "-ef");
	        pb.redirectErrorStream(true);
	        Process p = pb.start();

	        try (java.io.BufferedReader reader = new java.io.BufferedReader(
	                new java.io.InputStreamReader(p.getInputStream()))) {

	            String matched = reader.lines()
	                .filter(line -> line.contains("java"))
	                .filter(line -> !line.contains("grep"))
	                .filter(line -> line.contains(keyword))  // Java에서 필터 (셸 아님)
	                .findFirst()
	                .orElse(null);

	            if (matched == null) return null;

	            String[] parts = matched.trim().split("\\s+");
	            String pid = parts.length >= 2 ? parts[1] : "";
	            return new ProcSnapshot(pid, matched.trim());
	        }

	    } catch (Exception ignore) {
	        return null;
	    }
	}

	private String extractPort(String cmdline) {
		if (StringUtils.isBlank(cmdline)) {
			return "";
		}
		Matcher m = PORT_PATTERN.matcher(cmdline);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	private boolean checkHealth(String url) {
		try {
			java.net.URL u = new java.net.URL(url);
			java.net.HttpURLConnection c = (java.net.HttpURLConnection) u.openConnection();
			c.setConnectTimeout(800);
			c.setReadTimeout(800);
			c.setRequestMethod("GET");
			int code = c.getResponseCode();
			return code >= 200 && code < 300;
		} catch (Exception e) {
			return false;
		}
	}
}

