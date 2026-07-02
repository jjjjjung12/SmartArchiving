package com.archiving.bulkjob.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ServiceInfoUploadController {

	/* 서비스 정보 CSV 업로드 파싱 API(행 배열 JSON 반환) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/ParseServiceInfoReq", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String parseServiceInfoReq(@RequestParam(value = "btnImpExcel", required = false) MultipartFile file) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray rows = new JSONArray();

		if (file == null || file.isEmpty()) {
			json.put("rows", rows);
			json.put("result", "NOTFOUND");
			return json.toString();
		}

		List<String[]> parsed = parseCsv(file);
		if (parsed.size() <= 1) {
			json.put("rows", rows);
			json.put("result", "NOTFOUND");
			return json.toString();
		}

		String[] header = parsed.get(0);
		String[] columnName = new String[] { "application_group_id", "service_id", "sel_gubun", "del_yn" };
		boolean hasHeader = header.length > 0 && (
				"application_group_id".equalsIgnoreCase(clean(header[0]))
				|| "채널구분".equals(clean(header[0]))
		);
		int dataStart = hasHeader ? 1 : 0;

		for (int i = dataStart; i < parsed.size(); i++) {
			String[] cols = parsed.get(i);
			JSONObject d = new JSONObject();
			for (int c = 0; c < Math.min(columnName.length, cols.length); c++) {
				// only keep first 4 fields; serviceInfo.js expects these keys
				if (c > 3) break;
				d.put(columnName[c], clean(cols[c]));
			}
			rows.add(d);
		}

		json.put("rows", rows);
		json.put("uniqFileName", file.getOriginalFilename());
		json.put("records", rows.size());
		json.put("page", 1);
		json.put("total", 1);
		json.put("result", "OK");
		return json.toString();
	}

	/* CSV 파일을 줄 단위로 파싱 */
	private List<String[]> parseCsv(MultipartFile file) throws Exception {
		List<String[]> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace("\uFEFF", "");
				String[] parts = line.replaceAll("[\\p{Cntrl}\"]", "").split(",", -1);
				lines.add(parts);
			}
		}
		return lines;
	}

	/* CSV 셀 문자열 정리(제어문자/따옴표 제거) */
	private String clean(String v) {
		return v == null ? "" : v.replaceAll("[\\p{Cntrl}\"]", "").trim();
	}
}

