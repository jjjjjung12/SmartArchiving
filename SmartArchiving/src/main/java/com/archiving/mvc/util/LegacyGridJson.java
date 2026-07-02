package com.archiving.mvc.util;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Builds jqGrid-style JSON from MyBatis {@code Map} rows. PostgreSQL/MyBatis often yields
 * lowercase column keys; legacy JSP grids expect uppercase keys (e.g. {@code JOB_ID}).
 */
public final class LegacyGridJson {

	private LegacyGridJson() {
	}

	/* jqGrid 형식(rows/records/page/total/result)으로 Map row 리스트를 래핑 */
	public static JSONObject wrapRows(List<Map<String, Object>> rows, String sRows, String sPage) {
		if (rows == null) {
			rows = Collections.emptyList();
		}

		JSONObject jsonobj = new JSONObject();
		JSONArray arr = new JSONArray();
		for (Map<String, Object> r : rows) {
			if (r == null) {
				continue;
			}
			JSONObject o = new JSONObject();
			for (Map.Entry<String, Object> e : r.entrySet()) {
				o.put(normalizeKey(e.getKey()), e.getValue() == null ? " " : String.valueOf(e.getValue()));
			}
			arr.add(o);
		}

		int nCount = rows.size();
		jsonobj.put("rows", arr);

		int page = safeInt(sPage, 1);
		int per = safeInt(sRows, Math.max(1, nCount));

		if (nCount > 0) {
			int total = per == 0 ? 0 : (nCount / per);
			jsonobj.put("records", nCount);
			jsonobj.put("page", page);
			jsonobj.put("total", total);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NOTFOUND");
		}
		return jsonobj;
	}

	/**
	 * Converts a list of maps (or other values) to a JSONArray; map keys are normalized for legacy UIs.
	 */
	/* Map(List) 또는 값 리스트를 JSONArray로 변환(키는 대문자로 정규화) */
	public static JSONArray toJsonArray(List<?> rows) {
		if (rows == null) {
			return new JSONArray();
		}
		JSONArray arr = new JSONArray();
		for (Object r : rows) {
			if (r instanceof Map) {
				JSONObject o = new JSONObject();
				@SuppressWarnings("unchecked")
				Map<String, Object> m = (Map<String, Object>) r;
				for (Map.Entry<String, Object> e : m.entrySet()) {
					o.put(normalizeKey(e.getKey()), e.getValue() == null ? " " : String.valueOf(e.getValue()));
				}
				arr.add(o);
			} else {
				arr.add(r);
			}
		}
		return arr;
	}

	/* 키명을 레거시 UI 규칙(대문자)으로 정규화 */
	private static String normalizeKey(String key) {
		if (key == null) {
			return null;
		}
		return key.toUpperCase(Locale.ROOT);
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private static int safeInt(String v, int def) {
		try {
			if (v == null || v.trim().isEmpty()) {
				return def;
			}
			return Integer.parseInt(v.trim());
		} catch (Exception e) {
			return def;
		}
	}
}
