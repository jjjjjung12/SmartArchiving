package com.archiving.asset.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.asset.dao.mapper.TranMaskMapper;
import com.archiving.asset.dao.sqream.TranMaskSqreamMapper;
import com.archiving.asset.dto.TranMaskDto;
import com.archiving.asset.dto.TranMaskSaveParamDto;
import com.archiving.asset.service.TranMaskService;

@Service
public class TranMaskServiceImpl implements TranMaskService {
	private final TranMaskMapper tranMaskMapper;
	private final TranMaskSqreamMapper tranMaskSqreamMapper;

	public TranMaskServiceImpl(TranMaskMapper tranMaskMapper, TranMaskSqreamMapper tranMaskSqreamMapper) {
		this.tranMaskMapper = tranMaskMapper;
		this.tranMaskSqreamMapper = tranMaskSqreamMapper;
	}

	/* 전문 마스킹 규칙 목록 조회(영문명 조건) */
	@Override
	public List<TranMaskDto> list(String tgrmNmEng) {
		return tranMaskMapper.selectTranMaskList(tgrmNmEng);
	}

	/* 전문 마스킹 규칙 저장 처리(C/U/D, Sqream 컬럼 제약 검증 포함) */
	@Override
	@Transactional
	public void save(TranMaskSaveParamDto param) {
		if (param == null || param.getCrud() == null) {
			throw new IllegalArgumentException("crud is required");
		}

		if (!"D".equals(param.getCrud())) {
			validateMaskColumnNotNullInSqream(param.getTgrm_nm_eng());
		}

		switch (param.getCrud()) {
			case "C":
				tranMaskMapper.insertTranMask(param);
				break;
			case "U":
				tranMaskMapper.updateTranMask(param);
				break;
			case "D":
				tranMaskMapper.deleteTranMask(param.getTgrm_nm_eng());
				break;
			default:
				throw new IllegalArgumentException("invalid crud: " + param.getCrud());
		}
	}

	/* (Sqream) 마스킹 패턴 존재 여부 검증 */
	private void validateMaskColumnNotNullInSqream(String tgrmNmEng) {
		int cnt = tranMaskSqreamMapper.countMaskPatternNotNullByTgrmNmEng(tgrmNmEng);
//		if (cnt < 1) {
		if (cnt > 0) {
			throw new RuntimeException(tgrmNmEng + " 컬럼은 mask가 존재합니다.");
		}
	}
}
