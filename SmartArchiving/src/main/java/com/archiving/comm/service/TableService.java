package com.archiving.comm.service;

import java.util.List;

import com.archiving.comm.dto.TableAttrDto;
import com.archiving.comm.dto.TableInfoDto;
import com.archiving.comm.dto.TableListParamDto;
import com.archiving.comm.dto.TableSaveParamDto;

public interface TableService {

	/* 테이블 정보 목록 조회(조건은 파라미터 DTO 기준) */
	List<TableInfoDto> getTableInfoList(TableListParamDto p);

	/* 테이블 컬럼(속성) 목록 조회 */
	List<TableAttrDto> getTableAttrs(TableListParamDto p);

	/* 테이블 선택 팝업용 목록 조회 */
	List<TableInfoDto> getTableInfoPop(TableListParamDto p);

	/* 테이블/컬럼 저장 처리(crud에 따라 C/U/D 및 컬럼 관련 CRUD) */
	int save(TableSaveParamDto p);
}
