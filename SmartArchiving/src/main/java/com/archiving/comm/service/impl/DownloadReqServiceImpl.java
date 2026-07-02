package com.archiving.comm.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.comm.dao.mapper.DownloadReqMapper;
import com.archiving.comm.dao.mapper.DownloadReqMapper.InsaBranchRow;
import com.archiving.comm.dao.mapper.DownloadReqMapper.InsertDownloadReqParam;
import com.archiving.comm.dto.DownloadReqCheckParamDto;
import com.archiving.comm.dto.DownloadReqSaveParamDto;
import com.archiving.comm.service.DownloadReqService;
import com.archiving.util.UtilClass;

@Service
public class DownloadReqServiceImpl implements DownloadReqService {
	private final DownloadReqMapper downloadReqMapper;
	private final UtilClass utilClass;

	public DownloadReqServiceImpl(DownloadReqMapper downloadReqMapper) {
		this.downloadReqMapper = downloadReqMapper;
		this.utilClass = new UtilClass();
	}

	/* 당일 다운로드 요청 여부 확인(미요청 시 지점정보 반환) */
	@Override
	public Result checkToday(DownloadReqCheckParamDto p) {
		String userCd = utilClass.nvl_trim(p == null ? null : p.getUser_cd());
		String regDate = utilClass.getDate(0, "");

		int cnt = downloadReqMapper.countTodayReq(regDate, userCd);
		if (cnt > 0) {
			return Result.okFound();
		}

		InsaBranchRow br = downloadReqMapper.selectInsaBranch(userCd);
		return Result.notFound(br == null ? "" : StringUtils.defaultString(br.getBrc()),
				br == null ? "" : StringUtils.defaultString(br.getBrnm()));
	}

	/* 다운로드 요청 등록 */
	@Override
	@Transactional
	public void save(DownloadReqSaveParamDto p) {
		InsertDownloadReqParam ins = new InsertDownloadReqParam();
		ins.setUserCd(StringUtils.defaultString(p.getUser_cd()));
		ins.setUserNm(StringUtils.defaultString(p.getUser_nm()));
		ins.setReqDiv(StringUtils.defaultString(p.getDown_cd()));
		ins.setReqNm(StringUtils.defaultString(p.getReq_nm()));
		ins.setReqNum(StringUtils.defaultString(p.getReq_num()));
		ins.setReqReason(StringUtils.defaultString(p.getReq_reason()));
		ins.setProgramId(StringUtils.defaultString(p.getPage_name()));
		ins.setRegDate(StringUtils.defaultString(p.getReg_date()));
		ins.setProgramWhere(StringUtils.defaultString(p.getWhere()));

		downloadReqMapper.insertDownloadReq(ins);
	}
}
