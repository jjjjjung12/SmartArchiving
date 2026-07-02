package com.archiving.comm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.archiving.comm.dto.NoticeChkDateParamDto;
import com.archiving.comm.dto.NoticeDto;
import com.archiving.comm.dto.NoticeManageListParamDto;
import com.archiving.comm.service.NoticeService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class NoticeController {
	private final NoticeService noticeService;
	private final JsonParamBinder jsonParamBinder;

	public NoticeController(NoticeService noticeService, JsonParamBinder jsonParamBinder) {
		this.noticeService = noticeService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 공지사항 관리 목록 조회 API(옵션: serial_number 전달 시 삭제) */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetNoticeManageList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getNoticeManageList(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		NoticeManageListParamDto p = jsonParamBinder.bind(paramJson, NoticeManageListParamDto.class);
		String serialNumber = p == null ? "" : StringUtils.defaultString(p.getSerial_number());

		JSONObject jsonobj = new JSONObject();

		// legacy behavior: if serial_number present -> delete
		if (!StringUtils.isBlank(serialNumber)) {
			noticeService.deleteBySerialNumber(serialNumber);
			jsonobj.put("result", "OK");
			return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
		}

		List<NoticeDto> list = noticeService.list();
		JSONArray seriesArray = new JSONArray();
		for (NoticeDto n : list) {
			JSONObject datas = new JSONObject();
			datas.put("SERIAL_NUMBER", n.getSerialNumber());
			datas.put("REG_USER_CD", n.getRegUserCd());
			datas.put("USER_NM", n.getUserNm());
			datas.put("SUBJECT", n.getSubject());
			datas.put("REG_DATE", n.getRegDate());
			datas.put("REG_START_DATE", n.getRegStartDate());
			datas.put("REG_END_DATE", n.getRegEndDate());
			datas.put("SUBJECT_DETAIL", n.getSubjectDetail());
			datas.put("FILE_NM", n.getFileNm());
			datas.put("FILE_URL", n.getFileUrl());
			seriesArray.add(datas);
		}

		jsonobj.put("rows", seriesArray);
		jsonobj.put("result", list.isEmpty() ? "NOTFOUND" : "OK");

		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}

	/* 공지 확인 처리 API(사용자별 확인일자 저장) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetNoticeChkDate", produces = MediaType.TEXT_PLAIN_VALUE)
	@Transactional
	public ResponseEntity<String> setNoticeChkDate(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		NoticeChkDateParamDto p = jsonParamBinder.bind(paramJson, NoticeChkDateParamDto.class);
		JSONObject jsonobj = new JSONObject();
		noticeService.markNoticeChecked(p == null ? null : p.getUser_cd());
		jsonobj.put("result", "OK");
		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}

	/* 공지 등록 API(첨부파일 업로드 포함) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetNoticeManage", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<String> setNoticeManage(
			@RequestParam("F_USER_CD") String userCd,
			@RequestParam("F_SUBJECT_POP") String subject,
			@RequestParam("F_REG_START_DATE_POP") String regStartDate,
			@RequestParam("F_REG_END_DATE_POP") String regEndDate,
			@RequestParam("F_SUBJECT_DETAIL_POP") String subjectDetail,
			@RequestParam(value = "F_DOWNLOAD_POP", required = false) MultipartFile file
	) throws Exception {
		JSONObject jsonobj = new JSONObject();
		noticeService.createNotice(userCd, subject, regStartDate, regEndDate, subjectDetail, file);
		jsonobj.put("result", "OK");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonobj.toString());
	}
}

