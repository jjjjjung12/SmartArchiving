package com.archiving.bulkjob.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.bulkjob.dao.mapper.BulkInquiryJobReqMapper;
import com.archiving.bulkjob.dao.mapper.BulkInquiryJobReqMapper.InsertApprovalLineParam;
import com.archiving.bulkjob.dao.mapper.BulkInquiryJobReqMapper.InsertApprovalReqParam;
import com.archiving.bulkjob.dao.mapper.BulkInquiryJobReqMapper.InsertBulkJobReqParam;
import com.archiving.bulkjob.dto.BulkInquiryJobReqItemDto;
import com.archiving.bulkjob.dto.BulkInquiryJobReqSaveParamDto;
import com.archiving.bulkjob.service.BulkInquiryJobReqService;

@Service
public class BulkInquiryJobReqServiceImpl implements BulkInquiryJobReqService {
	private final BulkInquiryJobReqMapper mapper;
	private final JSONParser jsonParser = new JSONParser();

	public BulkInquiryJobReqServiceImpl(BulkInquiryJobReqMapper mapper) {
		this.mapper = mapper;
	}

	/* 벌크조회 요청 저장(벌크잡 요청/결재요청/결재라인 생성) */
	@Override
	@Transactional
	public void save(BulkInquiryJobReqSaveParamDto p) throws Exception {
		String reqDate = todayYmd();
		String approvalReqId = mapper.selectNextApprovalReqId(reqDate);

		List<BulkInquiryJobReqItemDto> items = parseItems(p.getBulkReqList());
		for (BulkInquiryJobReqItemDto it : items) {
			InsertBulkJobReqParam ins = new InsertBulkJobReqParam();
			ins.setApprovalReqId(approvalReqId);
			ins.setUserCd(StringUtils.defaultString(p.getUser_cd()));
			ins.setUserNm(StringUtils.defaultString(p.getUser_nm()));
			ins.setStartDate(StringUtils.defaultString(it.getSTART_DATE()));
			ins.setEndDate(StringUtils.defaultString(it.getEND_DATE()));
			ins.setAccountNo(StringUtils.defaultString(it.getACCOUNT_NO()));
			ins.setUserId(StringUtils.defaultString(it.getUSER_ID()));
			ins.setCustRegNo(StringUtils.defaultString(it.getCUST_REG_NO()));
			ins.setTelNo(StringUtils.defaultString(it.getTEL_NO()));
			ins.setUserIp(StringUtils.defaultString(it.getUSER_IP()));
			ins.setOrgFileNm(StringUtils.defaultString(it.getORG_FILE_NM()));
			ins.setUFileNm(StringUtils.defaultString(it.getU_FILE_NM()));
			mapper.insertBulkJobReq(ins);
		}

		InsertApprovalReqParam req = new InsertApprovalReqParam();
		req.setUserCd(StringUtils.defaultString(p.getUser_cd()));
		req.setApprovalDivCd(StringUtils.defaultString(p.getApply_div_cd()));
		req.setApprovalReqId(approvalReqId);
		req.setName(StringUtils.defaultString(p.getUser_nm()));
		req.setApprovalReqReason(StringUtils.defaultString(p.getApproval_req_reason()));
		req.setReqDate(reqDate);
		req.setBrc(StringUtils.defaultString(p.getBrc()));
		req.setBrnm(StringUtils.defaultString(p.getBrmm()));
		req.setOftC(StringUtils.defaultString(p.getOft_c()));
		req.setOft(StringUtils.defaultString(p.getOft()));
		req.setProgramId(StringUtils.defaultString(p.getPage_name()));
		mapper.insertApprovalReq(req);

		// approval line: 1/2/3
		insertLine(approvalReqId, p, 1, p.getFirst_user_id(), cutUserNm(p.getFirst_user_nm()), "Y");
		insertLine(approvalReqId, p, 2, p.getSecond_user_id(), cutUserNm(p.getSecond_user_nm()), "N");
		insertLine(approvalReqId, p, 3, "관리자", "관리자", "N");
	}

	/* 결재라인 1건 생성 */
	private void insertLine(String approvalReqId, BulkInquiryJobReqSaveParamDto p, int idx, String userId, String userNm, String beforeApplyYn) {
		InsertApprovalLineParam line = new InsertApprovalLineParam();
		line.setUserCd(StringUtils.defaultString(p.getUser_cd()));
		line.setName(StringUtils.defaultString(p.getUser_nm()));
		line.setApprovalDivCd(StringUtils.defaultString(p.getApply_div_cd()));
		line.setApprovalReqId(approvalReqId);
		line.setApprovalLineIndex(idx);
		line.setApprovalLineUserId(StringUtils.defaultString(userId));
		line.setApprovalLineUserNm(StringUtils.defaultString(userNm));
		line.setBeforeApplyYn(beforeApplyYn);
		mapper.insertApprovalLine(line);
	}

	/* 결재자명에서 레거시 표기(/) 제거 */
	private String cutUserNm(String v) {
		if (v == null) return "";
		int idx = v.indexOf("/");
		return idx > 0 ? v.substring(0, idx) : v;
	}

	/* 오늘 날짜(yyyyMMdd) 반환(서울 시간대) */
	private String todayYmd() {
		return LocalDate.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.BASIC_ISO_DATE);
	}

	/* 요청 목록(JSON 배열 문자열)을 DTO 리스트로 파싱 */
	private List<BulkInquiryJobReqItemDto> parseItems(String json) throws Exception {
		List<BulkInquiryJobReqItemDto> out = new ArrayList<>();
		if (StringUtils.isBlank(json)) return out;

		Object parsed = jsonParser.parse(json);
		if (!(parsed instanceof JSONArray)) return out;
		JSONArray arr = (JSONArray) parsed;
		for (Object o : arr) {
			if (!(o instanceof JSONObject)) continue;
			JSONObject r = (JSONObject) o;
			BulkInquiryJobReqItemDto it = new BulkInquiryJobReqItemDto();
			it.setSTART_DATE(StringUtils.defaultString((String) r.get("START_DATE")));
			it.setEND_DATE(StringUtils.defaultString((String) r.get("END_DATE")));
			it.setACCOUNT_NO(StringUtils.defaultString((String) r.get("ACCOUNT_NO")));
			it.setUSER_ID(StringUtils.defaultString((String) r.get("USER_ID")));
			it.setCUST_REG_NO(StringUtils.defaultString((String) r.get("CUST_REG_NO")));
			it.setTEL_NO(StringUtils.defaultString((String) r.get("TEL_NO")));
			it.setUSER_IP(StringUtils.defaultString((String) r.get("USER_IP")));
			it.setORG_FILE_NM(StringUtils.defaultString((String) r.get("ORG_FILE_NM")));
			it.setU_FILE_NM(StringUtils.defaultString((String) r.get("U_FILE_NM")));
			out.add(it);
		}
		return out;
	}
}
