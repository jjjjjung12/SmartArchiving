package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.comm.dto.TableAttrDto;
import com.archiving.comm.dto.TableInfoDto;
import com.archiving.comm.dto.TableSaveParamDto;

public interface TableMapper {
	/* 테이블 정보 목록 조회(서버/사용여부/테이블ID/테이블명 조건) */
	List<TableInfoDto> selectTableInfoList(@Param("serverId") String serverId,
										   @Param("useYn") String useYn,
										   @Param("tableId") String tableId,
										   @Param("tableNm") String tableNm);

	/* 테이블 컬럼(속성) 목록 조회 */
	List<TableAttrDto> selectTableAttrs(@Param("serverId") String serverId, @Param("tableId") String tableId);

	/* 테이블 선택 팝업용 목록 조회 */
	List<TableInfoDto> selectTableInfoPop(@Param("tableId") String tableId, @Param("tableNm") String tableNm);

	/* 테이블 정보 신규 등록 */
	int insertTableInfo(TableSaveParamDto p);

	/* 테이블 정보 수정 */
	int updateTableInfo(TableSaveParamDto p);

	/* 테이블 정보 사용중지(소프트 삭제) */
	int softDeleteTableInfo(@Param("tableId") String tableId);

	/* 테이블 컬럼(속성) 신규 등록 */
	int insertTableAttr(TableSaveParamDto p);

	/* 테이블 컬럼(속성) 수정 */
	int updateTableAttr(TableSaveParamDto p);

	/* 테이블 컬럼(속성) 하드 삭제 */
	int deleteTableAttrHard(@Param("tableId") String tableId, @Param("attrCd") String attrCd);

	/* 테이블 컬럼(속성) 사용중지(소프트 삭제) */
	int softDeleteTableAttr(@Param("tableId") String tableId, @Param("attrCd") String attrCd);

	/* 테이블 컬럼(속성) 존재 여부 카운트 */
	int countTableAttr(@Param("tableId") String tableId, @Param("attrCd") String attrCd);

	/* 사용 중인 테이블 컬럼(속성) 존재 여부 카운트 */
	int countActiveTableAttr(@Param("tableId") String tableId, @Param("attrCd") String attrCd);

	/* 소프트 삭제된 테이블 컬럼(속성) 복구 및 수정 */
	int reactivateTableAttr(TableSaveParamDto p);
}

