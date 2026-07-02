package com.archiving.comm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.archiving.comm.dto.NoticeDto;

public interface NoticeService {

	/* 공지사항 관리 목록 조회 */
	List<NoticeDto> list();

	/* 공지사항 삭제(시리얼 번호 기준) */
	void deleteBySerialNumber(String serialNumber);

	/* 공지 확인 처리(당일 확인 여부 저장/업데이트) */
	void markNoticeChecked(String userCd);

	/* 공지사항 등록(첨부파일 업로드 후 공지 테이블 저장 및 확인여부 초기화) */
	void createNotice(String userCd,
					  String subject,
					  String regStartDate,
					  String regEndDate,
					  String subjectDetail,
					  MultipartFile file) throws Exception;
}
