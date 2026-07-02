package com.archiving.mvc.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParamBinder {
	private final ObjectMapper objectMapper;

	/* param JSON 바인더(ObjectMapper 초기화: unknown field 무시) */
	public JsonParamBinder() {
		this.objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/* request param(JSON 문자열)을 DTO 클래스로 역직렬화 */
	public <T> T bind(String paramJson, Class<T> clazz) throws Exception {
		if (paramJson == null || paramJson.trim().isEmpty()) {
			return null;
		}
		return objectMapper.readValue(paramJson, clazz);
	}
}

