package com.archiving.comm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.comm.dao.mapper.TableMapper;
import com.archiving.comm.dto.TableAttrDto;
import com.archiving.comm.dto.TableInfoDto;
import com.archiving.comm.dto.TableListParamDto;
import com.archiving.comm.dto.TableSaveParamDto;
import com.archiving.comm.service.TableService;

@Service
public class TableServiceImpl implements TableService {
	private final TableMapper tableMapper;

	public TableServiceImpl(TableMapper tableMapper) {
		this.tableMapper = tableMapper;
	}

	/* 테이블 정보 목록 조회(조건은 파라미터 DTO 기준) */
	@Override
	public List<TableInfoDto> getTableInfoList(TableListParamDto p) {
		return tableMapper.selectTableInfoList(
				p.get__serverId(),
				p.get__use_yn(),
				p.get__table_id(),
				p.get__table_nm()
		);
	}

	/* 테이블 컬럼(속성) 목록 조회 */
	@Override
	public List<TableAttrDto> getTableAttrs(TableListParamDto p) {
		return tableMapper.selectTableAttrs(p.get__serverId(), p.get__table_id());
	}

	/* 테이블 선택 팝업용 목록 조회 */
	@Override
	public List<TableInfoDto> getTableInfoPop(TableListParamDto p) {
		return tableMapper.selectTableInfoPop(p.get__table_id(), p.get__table_nm());
	}

	/* 테이블/컬럼 저장 처리(crud에 따라 C/U/D 및 컬럼 관련 CRUD) */
	@Override
	@Transactional
	public int save(TableSaveParamDto p) {
		normalizeSaveParams(p);

		String crud = StringUtils.defaultString(p.getCrud());
		if ("C".equals(crud)) {
			tableMapper.insertTableInfo(p);
			return 0;
		}
		if ("U".equals(crud)) {
			return tableMapper.updateTableInfo(p);
		}
		if ("D".equals(crud)) {
			return tableMapper.softDeleteTableInfo(p.getTable_id());
		}

		if ("AC".equals(crud)) {
			return saveTableAttrCreate(p);
		}
		if ("AU".equals(crud)) {
			return tableMapper.updateTableAttr(p);
		}
		if ("AD".equals(crud)) {
			return tableMapper.softDeleteTableAttr(p.getTable_id(), p.getAttr_cd());
		}
		if ("AAU".equals(crud)) {
			return saveTableAttrUpsert(p);
		}

		// RN: legacy no-op
		return 0;
	}

	/* 컬럼 신규 등록 — 동일 코드가 소프트 삭제된 경우 복구 */
	private int saveTableAttrCreate(TableSaveParamDto p) {
		int cnt = tableMapper.countTableAttr(p.getTable_id(), p.getAttr_cd());
		if (cnt == 0) {
			return tableMapper.insertTableAttr(p);
		}
		if (tableMapper.countActiveTableAttr(p.getTable_id(), p.getAttr_cd()) > 0) {
			throw new IllegalStateException("이미 등록된 컬럼 코드입니다.");
		}
		return tableMapper.reactivateTableAttr(p);
	}

	/* 컬럼 등록/수정(엑셀 등) — 존재하면 복구·수정 */
	private int saveTableAttrUpsert(TableSaveParamDto p) {
		int cnt = tableMapper.countTableAttr(p.getTable_id(), p.getAttr_cd());
		if (cnt == 0) {
			return tableMapper.insertTableAttr(p);
		}
		return tableMapper.reactivateTableAttr(p);
	}

	/* 레거시 화면 입력값을 저장용 포맷으로 정규화 */
	private void normalizeSaveParams(TableSaveParamDto p) {
		if (p == null) return;

		if ("".equals(p.getAttr_order())) p.setAttr_order(null);
		if ("".equals(p.getSort_index())) p.setSort_index(null);
		if ("".equals(p.getWhere_index())) p.setWhere_index(null);
		if ("".equals(p.getOutput_index())) p.setOutput_index(null);

		if (p.getDecimal_size() == null) p.setDecimal_size("0");
		if (p.getAttr_use_yn() == null) p.setAttr_use_yn("N");
		if (p.getAttr_null_yn() == null) p.setAttr_null_yn("N");
		if (p.getDate_type_yn() == null) p.setDate_type_yn("N");
		if (p.getTime_type_yn() == null) p.setTime_type_yn("N");

		String crud = StringUtils.defaultString(p.getCrud());
		if ("C".equals(crud) || "U".equals(crud) || "D".equals(crud)) {
			if (StringUtils.isBlank(p.getSave_preq())) p.setSave_preq("0");
			if (StringUtils.isBlank(p.getExp_preq())) p.setExp_preq("0");
		}
	}
}
