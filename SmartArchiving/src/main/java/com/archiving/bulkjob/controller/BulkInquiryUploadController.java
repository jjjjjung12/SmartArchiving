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

import com.archiving.util.UtilClass;

@RestController
public class BulkInquiryUploadController {
	private final UtilClass utilClass = new UtilClass();

	/* 벌크조회 요청 CSV 업로드 파싱 API(프론트 매핑용 key로 변환) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/ParseBulkInquiryJobReq", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String parseBulkInquiryJobReq(@RequestParam(value = "btnImpExcel", required = false) MultipartFile file) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray rows = new JSONArray();

		if (file == null || file.isEmpty()) {
			json.put("rows", rows);
			json.put("result", "NOTFOUND");
			return json.toString();
		}

		// Legacy behavior: CSV only (accept=".csv")
		List<String[]> parsed = parseCsv(file);
		if (parsed.isEmpty()) {
			json.put("rows", rows);
			json.put("result", "NOTFOUND");
			return json.toString();
		}

		// First line could be header; legacy accepted both with/without header.
		String[] header = parsed.get(0);
		boolean hasHeader = isHeaderRow(header);
		int startIdx = hasHeader ? 1 : 0;
		// 업로드 CSV는 헤더 유무와 상관 없이 아래 key로 내려준다(프론트 매핑 고정).
		String[] columnName = new String[] { "fromDate", "toDate", "accountNo", "userId", "custRegNo", "telNo", "userIp" };

		for (int i = startIdx; i < parsed.size(); i++) {
			String[] cols = parsed.get(i);
			JSONObject d = new JSONObject();
			for (int c = 0; c < Math.min(columnName.length, cols.length); c++) {
				d.put(columnName[c], clean(cols[c]));
			}
			// legacy also returned fileName/uFileName but they were only for server-side storage; keep empty here
			d.put("fileName", file.getOriginalFilename());
			d.put("uFileName", file.getOriginalFilename());
			rows.add(d);
		}

		json.put("rows", rows);
		json.put("result", "OK");
		return json.toString();
	}

	/* CSV 파일을 줄 단위로 파싱 */
	private List<String[]> parseCsv(MultipartFile file) throws Exception {
		List<String[]> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace("\uFEFF", ""); // BOM
				// Keep empty columns
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

	/* 첫 행이 헤더인지 판별(영문/한글 헤더 모두 지원) */
	private boolean isHeaderRow(String[] row) {
		if (row == null || row.length == 0) return false;

		// 영문 헤더
		String c0 = clean(row[0]).toLowerCase();
		if ("fromdate".equals(c0) || "start_date".equals(c0) || "startdate".equals(c0)) return true;

		// CSV를 엑셀에서 만든 경우 한글 헤더가 자주 들어옴
		String joined = String.join(" ", java.util.Arrays.stream(row).map(this::clean).toList());
		return joined.contains("조회시작") || joined.contains("조회종료") || joined.contains("영장") || joined.toLowerCase().contains("todate");
	}
}

