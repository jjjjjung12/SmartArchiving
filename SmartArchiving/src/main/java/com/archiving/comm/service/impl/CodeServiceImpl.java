package com.archiving.comm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.comm.dao.mapper.CodeMapper;
import com.archiving.comm.dto.CodeDetailDto;
import com.archiving.comm.dto.CodeGroupDto;
import com.archiving.comm.dto.CodeManagerParamDto;
import com.archiving.comm.dto.ServerCacheRowDto;
import com.archiving.comm.service.CodeService;
import com.archiving.util.CodeClass;

@Service
public class CodeServiceImpl implements CodeService {
	private final CodeMapper codeMapper;

	public CodeServiceImpl(CodeMapper codeMapper) {
		this.codeMapper = codeMapper;
	}

	/* 애플리케이션 기동 시 레거시 코드 캐시 로딩 */
	@PostConstruct
	public void loadLegacyCodeCachesOnStartup() {
		refreshLegacyCodeCaches();
	}

	/**
	 * Refreshes static maps used by legacy JSP helpers ({@link CodeClass}).
	 */
	/* 레거시 코드/서버 캐시(CodeClass) 갱신 */
	@Override
	public void refreshLegacyCodeCaches() {
		List<CodeDetailDto> rows = codeMapper.selectAllCodeDetailsOrdered();
		CodeClass.getHashMap().clear();

		ArrayList<CodeClass.CodeDetail> current = new ArrayList<>();
		String prevGroup = null;
		int nPos = 0;

		for (CodeDetailDto row : rows) {
			String g = StringUtils.upperCase(row.getGroupCd());
			if (nPos != 0 && prevGroup != null && !StringUtils.equals(prevGroup, g)) {
				CodeClass.getHashMap().put(prevGroup, current);
				current = new ArrayList<>();
			}

			CodeClass.CodeDetail cd = new CodeClass.CodeDetail();
			cd.setGrpCD(g);
			cd.setCode(row.getDetailCd());
			cd.setCodeNm(row.getDetailNm());
			current.add(cd);

			prevGroup = g;
			nPos++;
		}
		if (!current.isEmpty() && prevGroup != null) {
			CodeClass.getHashMap().put(prevGroup, current);
		}

		CodeClass.getHashMapServer().clear();
		for (ServerCacheRowDto s : codeMapper.selectActiveServersForCache()) {
			if (s.getServerId() != null) {
				CodeClass.getHashMapServer().put(s.getServerId(), s.getServerNm());
			}
		}
	}

	/* 코드상세 목록 조회(그룹/상세코드 조건) */
	@Override
	public List<CodeDetailDto> listDetails(String groupCd, String detailCd) {
		return codeMapper.selectCodeDetails(groupCd, detailCd);
	}

	/* 코드그룹 목록 조회 */
	@Override
	public List<CodeGroupDto> listGroups(String groupNm, String useYn) {
		return codeMapper.selectCodeGroups(groupNm, useYn);
	}

	/* 그룹코드 기준 코드상세 목록 조회 */
	@Override
	public List<CodeDetailDto> listDetailsByGroup(String groupCd, String useYn) {
		return codeMapper.selectCodeDetailsByGroup(groupCd, useYn);
	}

	/* 코드관리 CRUD 적용 후 캐시 갱신 */
	@Override
	@Transactional
	public void apply(CodeManagerParamDto p) {
		if (p == null || p.get__crud() == null) {
			throw new IllegalArgumentException("crud is required");
		}

		switch (p.get__crud()) {
			case "C":
				codeMapper.insertCodeGroup(p.get__group_cd(), p.get__group_nm(), p.get__use_yn());
				break;
			case "U":
				codeMapper.updateCodeGroup(p.get__group_cd(), p.get__group_nm(), p.get__use_yn());
				break;
			case "D":
				codeMapper.deleteCodeGroup(p.get__group_cd());
				codeMapper.deleteCodeDetailsByGroup(p.get__group_cd());
				break;
			case "CD":
				codeMapper.insertCodeDetail(p.get__group_cd(), p.get__detail_cd(), p.get__detail_nm(), p.get__use_yn());
				break;
			case "UD":
				codeMapper.updateCodeDetail(p.get__group_cd(), p.get__detail_cd(), p.get__detail_nm(), p.get__use_yn());
				break;
			case "DD":
				codeMapper.deleteCodeDetail(p.get__group_cd(), p.get__detail_cd());
				break;
			default:
				throw new IllegalArgumentException("invalid crud: " + p.get__crud());
		}

		refreshLegacyCodeCaches();
	}
}
