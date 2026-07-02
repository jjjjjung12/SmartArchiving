package com.archiving.magicview.support;

import java.util.Map;

public final class MagicViewRowMaps {

	private MagicViewRowMaps() {
	}

	/* Map row에서 key(대소문자 무시)로 문자열 값을 안전하게 조회 */
	public static String getStr(Map<String, Object> row, String key) {
		if (row == null || key == null) {
			return null;
		}
		for (Map.Entry<String, Object> e : row.entrySet()) {
			if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
				Object v = e.getValue();
				return v == null ? null : String.valueOf(v);
			}
		}
		return null;
	}
}
