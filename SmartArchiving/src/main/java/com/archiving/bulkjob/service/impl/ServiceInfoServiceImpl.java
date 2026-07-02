package com.archiving.bulkjob.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.bulkjob.dao.sqream.ServiceInfoSqreamMapper;
import com.archiving.bulkjob.dto.ServiceInfoParamDto;
import com.archiving.bulkjob.dto.ServiceInfoRowDto;
import com.archiving.bulkjob.dto.ServiceInfoSaveParamDto;
import com.archiving.bulkjob.service.ServiceInfoService;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {
	private final ServiceInfoSqreamMapper sqreamMapper;
	private final Environment env;
	private final JSONParser jsonParser = new JSONParser();

	public ServiceInfoServiceImpl(ServiceInfoSqreamMapper sqreamMapper, Environment env) {
		this.sqreamMapper = sqreamMapper;
		this.env = env;
	}

	/* 서비스 정보 목록 조회(EFBL/DFSL 자동 선택) */
	@Override
	public List<ServiceInfoRowDto> list(ServiceInfoParamDto p) {
		String dbName = StringUtils.defaultString(env.getProperty("app.db.sqream.db-name"));
		String groupId = StringUtils.defaultString(p == null ? null : p.getF_SP_APPLICATION_GROUP_ID()).trim();
		boolean isEfbl = dbName.contains("nbefbl");
		return isEfbl ? sqreamMapper.selectEfbl(groupId) : sqreamMapper.selectDfsl(groupId);
	}

	/* 서비스 정보 저장(존재 여부에 따라 insert/update) */
	@Override
	@Transactional
	public void save(ServiceInfoSaveParamDto p) throws Exception {
		List<ServiceInfoRowDto> rows = parseRows(p == null ? null : p.getServiceInfoList());
		String upDtime = nowPublishFormat();

		String dbName = StringUtils.defaultString(env.getProperty("app.db.sqream.db-name"));
		boolean isEfbl = dbName.contains("nbefbl");

		for (ServiceInfoRowDto r : rows) {
			int cnt = isEfbl
					? sqreamMapper.countEfbl(r.getApplicationGroupId(), r.getServiceId())
					: sqreamMapper.countDfsl(r.getApplicationGroupId(), r.getServiceId());

			if (cnt > 0) {
				if (isEfbl) sqreamMapper.updateEfbl(r.getApplicationGroupId(), r.getServiceId(), r.getSelGubun(), r.getDelYn(), upDtime);
				else sqreamMapper.updateDfsl(r.getApplicationGroupId(), r.getServiceId(), r.getSelGubun(), r.getDelYn(), upDtime);
			} else {
				if (isEfbl) sqreamMapper.insertEfbl(r.getApplicationGroupId(), r.getServiceId(), r.getSelGubun(), r.getDelYn(), upDtime);
				else sqreamMapper.insertDfsl(r.getApplicationGroupId(), r.getServiceId(), r.getSelGubun(), r.getDelYn(), upDtime);
			}
		}
	}

	/* 서비스 정보 전체 교체(TRUNCATE 후 일괄 insert) */
	@Override
	@Transactional
	public void adminReplaceAll(ServiceInfoSaveParamDto p) throws Exception {
		List<ServiceInfoRowDto> rows = parseRows(p == null ? null : p.getServiceInfoList());
		String upDtime = nowPublishFormat();

		String dbName = StringUtils.defaultString(env.getProperty("app.db.sqream.db-name"));
		boolean isEfbl = dbName.contains("nbefbl");

		if (isEfbl) {
			sqreamMapper.truncateEfbl();
			if (!rows.isEmpty()) sqreamMapper.insertManyEfbl(rows, upDtime);
		} else {
			sqreamMapper.truncateDfsl();
			if (!rows.isEmpty()) sqreamMapper.insertManyDfsl(rows, upDtime);
		}
	}

	/* 서비스 정보 JSON 배열 문자열을 DTO 목록으로 파싱 */
	private List<ServiceInfoRowDto> parseRows(String json) throws Exception {
		List<ServiceInfoRowDto> out = new ArrayList<>();
		if (StringUtils.isBlank(json)) return out;
		Object parsed = jsonParser.parse(json);
		if (!(parsed instanceof JSONArray)) return out;
		JSONArray arr = (JSONArray) parsed;
		for (Object o : arr) {
			if (!(o instanceof JSONObject)) continue;
			JSONObject r = (JSONObject) o;
			ServiceInfoRowDto dto = new ServiceInfoRowDto();
			dto.setApplicationGroupId(StringUtils.defaultString((String) r.get("application_group_id")));
			dto.setServiceId(StringUtils.defaultString((String) r.get("service_id")));
			dto.setSelGubun(StringUtils.defaultString((String) r.get("sel_gubun")));
			dto.setDelYn(StringUtils.defaultString((String) r.get("del_yn")));
			out.add(dto);
		}
		return out;
	}

	/* 현재 시각을 publish 포맷(yyyyMMddHHmmss)으로 반환 */
	private String nowPublishFormat() {
		// legacy utilClass.getCurrentTime_Publish() format is unknown; keep stable-ish (yyyyMMddHHmmss)
		return LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
