package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.comm.dto.NoticeDto;

public interface NoticeMapper {
	/* 공지사항 관리 목록 조회 */
	List<NoticeDto> selectNoticeManageList();

	/* 공지사항 삭제 */
	int deleteNotice(@Param("serialNumber") String serialNumber);

	/* 공지사항 시리얼 suffix(일자별 순번) 생성용 조회 */
	String selectNextNoticeSerialSuffix(@Param("regDate") String regDate);

	/* 공지사항 등록 */
	int insertNotice(NoticeDto notice);

	/* 공지 확인여부(일자) 전체 초기화 */
	int resetNoticeDayChk();

	/* 사용자별 공지 확인여부 존재 카운트 */
	int countNoticeDay(@Param("userCd") String userCd, @Param("chkDate") String chkDate);

	/* 사용자 공지 확인여부 Y로 업데이트 */
	int updateNoticeDayChkY(@Param("userCd") String userCd);

	/* 사용자 공지 확인 이력 등록 */
	int insertNoticeDay(@Param("userCd") String userCd, @Param("chkDate") String chkDate);
}

