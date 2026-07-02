package com.archiving.asset.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.asset.dto.DaemonDto;
import com.archiving.asset.dto.DaemonSaveParamDto;

public interface DaemonMapper {
	/* 데몬 목록 조회(데몬ID/사용여부 조건) */
	List<DaemonDto> selectDaemonList(@Param("daemonId") String daemonId,
		@Param("useYn") String useYn,
		@Param("searchCol") String searchCol,
		@Param("searchVal") String searchVal);

	/* 데몬 신규 등록 */
	int insertDaemon(DaemonSaveParamDto param);

	/* 데몬 정보 수정 */
	int updateDaemon(DaemonSaveParamDto param);

	/* 데몬 삭제 */
	int deleteDaemon(@Param("daemonId") String daemonId);

	/* 데몬 커맨드(명령) 업데이트 */
	int updateDaemonCommand(@Param("daemonId") String daemonId, @Param("command") String command);

	/* 데몬 명령 이력 등록 */
	int insertDaemonHist(
			@Param("daemonId") String daemonId,
			@Param("daemonNm") String daemonNm,
			@Param("userId") String userId,
			@Param("command") String command
	);
}

