package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.comm.dto.CodeDetailDto;
import com.archiving.comm.dto.CodeGroupDto;
import com.archiving.comm.dto.ServerCacheRowDto;

public interface CodeMapper {
	/* 코드상세 목록 조회(그룹/상세코드 조건) */
	List<CodeDetailDto> selectCodeDetails(@Param("groupCd") String groupCd, @Param("detailCd") String detailCd);

	/* 코드그룹 목록 조회 */
	List<CodeGroupDto> selectCodeGroups(@Param("groupNm") String groupNm, @Param("useYn") String useYn);

	/* 그룹코드 기준 코드상세 목록 조회 */
	List<CodeDetailDto> selectCodeDetailsByGroup(@Param("groupCd") String groupCd, @Param("useYn") String useYn);

	/* 코드그룹 신규 등록 */
	int insertCodeGroup(@Param("groupCd") String groupCd, @Param("groupNm") String groupNm, @Param("useYn") String useYn);

	/* 코드그룹 수정 */
	int updateCodeGroup(@Param("groupCd") String groupCd, @Param("groupNm") String groupNm, @Param("useYn") String useYn);

	/* 코드그룹 삭제 */
	int deleteCodeGroup(@Param("groupCd") String groupCd);

	/* 그룹코드 기준 코드상세 전체 삭제 */
	int deleteCodeDetailsByGroup(@Param("groupCd") String groupCd);

	/* 코드상세 신규 등록 */
	int insertCodeDetail(@Param("groupCd") String groupCd, @Param("detailCd") String detailCd, @Param("detailNm") String detailNm, @Param("useYn") String useYn);

	/* 코드상세 수정 */
	int updateCodeDetail(@Param("groupCd") String groupCd, @Param("detailCd") String detailCd, @Param("detailNm") String detailNm, @Param("useYn") String useYn);

	/* 코드상세 삭제 */
	int deleteCodeDetail(@Param("groupCd") String groupCd, @Param("detailCd") String detailCd);

	/* 전체 코드상세 정렬 조회(그룹/상세코드 순) */
	List<CodeDetailDto> selectAllCodeDetailsOrdered();

	/* 캐시용 활성 서버 목록 조회 */
	List<ServerCacheRowDto> selectActiveServersForCache();
}

