package com.archiving.asset.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.asset.dto.ServerDto;

public interface ServerMapper {
	/* 서버 목록 조회(서버ID/사용여부 조건) */
	List<ServerDto> selectServerList(@Param("serverId") String serverId,
		@Param("useYn") String useYn,
		@Param("searchCol") String searchCol,
		@Param("searchVal") String searchVal);

	/* 서버 신규 등록 */
	int insertServer(@Param("serverNm") String serverNm,
		@Param("serverClassCd") String serverClassCd,
		@Param("serverIp") String serverIp,
		@Param("serverDesc") String serverDesc);

	/* 서버 사용중지(소프트 삭제) */
	int softDeleteServer(@Param("serverId") String serverId);

	/* 서버 정보 수정 */
	int updateServer(@Param("serverId") String serverId,
		@Param("serverNm") String serverNm,
		@Param("serverClassCd") String serverClassCd,
		@Param("serverIp") String serverIp,
		@Param("serverDesc") String serverDesc,
		@Param("useYn") String useYn);
}

