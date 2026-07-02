package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.comm.dto.WorkRowDto;
import com.archiving.comm.dto.WorkSaveParamDto;

public interface WorkMapper {
	/* 업무 목록 조회(서버/업무코드/사용여부 조건) */
	List<WorkRowDto> selectWorkList(@Param("serverId") String serverId,
									@Param("workCd") String workCd,
									@Param("useYn") String useYn);

	/* 업무 신규 등록 */
	int insertWork(WorkSaveParamDto p);

	/* 업무 정보 수정 */
	int updateWork(WorkSaveParamDto p);

	/* 업무 삭제(사용여부 'D' 처리) */
	int deleteWork(@Param("serverId") String serverId, @Param("workCd") String workCd);
}

