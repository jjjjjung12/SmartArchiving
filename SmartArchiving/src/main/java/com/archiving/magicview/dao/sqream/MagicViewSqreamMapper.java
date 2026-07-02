package com.archiving.magicview.dao.sqream;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archiving.magicview.dto.AsisIlogHeaderQuery;
import com.archiving.magicview.dto.AsisSlogHeaderQuery;
import com.archiving.magicview.dto.IlogHeaderQuery;
import com.archiving.magicview.dto.IlogIpBandQuery;
import com.archiving.magicview.dto.LayoutMetaQuery;
import com.archiving.magicview.dto.SameTranIdQuery;
import com.archiving.magicview.dto.SlogHeaderQuery;
import com.archiving.magicview.dto.ServiceReportQuery;

public interface MagicViewSqreamMapper {

	/* 동적 SQL 실행(Sqream, 주의: ${sql} 직접 실행) */
	List<Map<String, Object>> selectDynamicSql(@Param("sql") String sql);

	/* ASIS 레이아웃 메타 조회(로그레벨/메시지ID) */
	List<Map<String, Object>> selectAsisLayoutMeta(
		@Param("logLevel") String logLevel,
		@Param("messageId") String messageId);

	/* ASIS ILOG 헤더 건수 조회(조건 검색) */
	int countAsisIlogHeader(AsisIlogHeaderQuery q);

	/* ASIS ILOG 헤더 목록 조회(조건 검색) */
	List<Map<String, Object>> selectAsisIlogHeader(AsisIlogHeaderQuery q);

	/* ASIS SLOG 헤더 통계 조회 */
	List<Map<String, Object>> selectAsisSlogHeader(AsisSlogHeaderQuery q);

	/* 동일 거래ID 상세 조회(ASIS) */
	List<Map<String, Object>> selectAsisSameTranIdDetail(@Param("messageSerNo") String messageSerNo);

	/* SLOG 헤더 집계 조회(서버/기간/서비스 조건) */
	List<Map<String, Object>> selectSlogHeader(SlogHeaderQuery q);

	/* ILOG IP 대역별 헤더 조회 */
	List<Map<String, Object>> selectIlogIpBandHeader(IlogIpBandQuery q);

	/* ILOG 헤더 조회(조건 검색) */
	List<Map<String, Object>> selectIlogHeader(IlogHeaderQuery q);

	/* 동일 거래ID 데이터 조회 */
	List<Map<String, Object>> selectSameTranIdData(SameTranIdQuery q);

	/* 전문 레이아웃 메타 조회(대표전문ID 기준) */
	List<Map<String, Object>> selectLayoutMeta(LayoutMetaQuery q);

	/* 계좌 링크 포함 전체 레이아웃 조회 */
	List<Map<String, Object>> selectAllAccountLayout(LayoutMetaQuery q);

	/* 더미 응답 전문 레이아웃 조회 */
	List<Map<String, Object>> selectDummyResLayout(LayoutMetaQuery q);
	
	/* 서비스 통계 조회(채널/기간/거래구분/집계포함여부) */
	List<Map<String, Object>> selectServiceReport(ServiceReportQuery q);
}
