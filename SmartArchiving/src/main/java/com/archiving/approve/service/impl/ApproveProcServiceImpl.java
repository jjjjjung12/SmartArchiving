package com.archiving.approve.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.approve.dao.mapper.ApproveProcMapper;
import com.archiving.approve.dao.mapper.ApproveProcMapper.InsertMaskHistoryParam;
import com.archiving.approve.dao.mapper.ApproveProcMapper.InsertUserInfoParam;
import com.archiving.approve.dao.mapper.ApproveProcMapper.UpdateApprovalLineParam;
import com.archiving.approve.dao.mapper.ApproveProcMapper.UpdateApprovalReqParam;
import com.archiving.approve.dao.mapper.ApproveProcMapper.UpdateUserInfoLoginParam;
import com.archiving.approve.dto.UserApproveProcParamDto;
import com.archiving.approve.service.ApproveProcService;
import com.archiving.util.UtilClass;

@Service
public class ApproveProcServiceImpl implements ApproveProcService {
	private final ApproveProcMapper approveProcMapper;
	private final UtilClass utilClass;

	public ApproveProcServiceImpl(ApproveProcMapper approveProcMapper) {
		this.approveProcMapper = approveProcMapper;
		this.utilClass = new UtilClass();
	}

	/* 결재 처리 실행(결재라인 업데이트, 승인 시 권한/유저/마스킹/벌크 작업 반영) */
	@Override
	@Transactional
	public void process(UserApproveProcParamDto p) throws Exception {
		String userCd = utilClass.nvl_trim(p.getUser_cd());
		String approvalReqId = utilClass.nvl_trim(p.getApproval_req_id());
		String approvalDivCd = utilClass.nvl_trim(p.getApproval_div_cd());
		String approvalLineUserId = utilClass.nvl_trim(p.getApproval_line_user_id());
		String approvalLineIndexStr = utilClass.nvl_trim(p.getApproval_line_index());
		String approvalYn = utilClass.nvl_trim(p.getApproval_yn());

		int approvalLineIndex = safeInt(approvalLineIndexStr, 0);
		String firstApprovalYn = StringUtils.defaultString(
				approveProcMapper.selectApprovalYn(userCd, approvalReqId, approvalDivCd, 1));

		if ("".equals(utilClass.nvl_trim(firstApprovalYn)) && approvalLineIndex == 2) {
			throw new Exception("1차 결재승인이 아직 되질 않았습니다.");
		}
		if ("N".equals(utilClass.nvl_trim(firstApprovalYn)) && approvalLineIndex == 2) {
			throw new Exception("반려된 건입니다.");
		}

		UpdateApprovalLineParam up = new UpdateApprovalLineParam();
		up.setApprovalYn(approvalYn);
		up.setApprovalRejectDocu(utilClass.nvl_trim(p.getApproval_reject_docu()));
		up.setApprovalReqDocu(utilClass.nvl_trim(p.getApproval_req_docu()));
		up.setApprovalDate(utilClass.getDate(0, ""));
		up.setUserCd(userCd);
		up.setApprovalReqId(approvalReqId);
		up.setApprovalDivCd(approvalDivCd);
		up.setApprovalLineUserId(approvalLineUserId);
		up.setWhereApprovalLineUserId(approvalLineUserId);
		up.setApprovalLineIndex(approvalLineIndex);

		// legacy: batch request admin approval sets approver id/name to requester
		if ("03".equals(approvalDivCd) && approvalLineIndex == 3) {
			up.setApprovalLineUserId(userCd);
			up.setApprovalLineUserNm(utilClass.nvl_trim(p.getApply_user_nm()));
		}

		approveProcMapper.updateApprovalLine(up);

		if ("Y".equals(approvalYn)) {
			if (approvalLineIndex == 1) {
				approveProcMapper.setBeforeApplyYn(approvalReqId, 2);
			}
			if ("Y".equals(utilClass.nvl_trim(firstApprovalYn)) && approvalLineIndex == 2) {
				approveProcMapper.setBeforeApplyYn(approvalReqId, 3);
			}

			String secondApprovalYn = StringUtils.defaultString(
					approveProcMapper.selectApprovalYn(userCd, approvalReqId, approvalDivCd, 2));

			if ("Y".equals(utilClass.nvl_trim(secondApprovalYn)) && approvalLineIndex == 3) {
				String authCode = resolveAuthCode(approvalDivCd, utilClass.nvl_trim(p.getCom_cd()));
				UpdateApprovalReqParam reqUp = new UpdateApprovalReqParam();
				reqUp.setApprovalDate(utilClass.getDate(0, ""));
				reqUp.setAuth(shouldSetAuth(approvalDivCd, utilClass.nvl_trim(p.getGroup_id())) ? authCode : null);
				reqUp.setUserCd(userCd);
				reqUp.setApprovalDivCd(approvalDivCd);
				reqUp.setApprovalReqId(approvalReqId);
				approveProcMapper.updateApprovalReqApprovalDateAndAuth(reqUp);

				// 신규유저 추가/업데이트 + 그룹맵핑
				if ("01".equals(approvalDivCd) && isNewUserGroup(utilClass.nvl_trim(p.getGroup_id()))) {
					approveProcMapper.updateApprovalLineUserNm(approvalReqId, 3, utilClass.nvl_trim(p.getApply_user_nm()));

					String pwd = utilClass.encryptSHA256("123456789");
					int cnt = approveProcMapper.countUserInfo(userCd);

					if (cnt == 0) {
						InsertUserInfoParam ins = new InsertUserInfoParam();
						ins.setUserCd(userCd);
						ins.setUserNm(utilClass.nvl_trim(p.getUser_nm()));
						ins.setPassword(pwd);
						ins.setLoginDate(utilClass.getDate(0, ""));
						ins.setBrc(utilClass.nvl_trim(p.getBrc()));
						ins.setBrnm(utilClass.nvl_trim(p.getBrnm()));
						ins.setIpAddress(utilClass.nvl_trim(p.getIp_address()));
						ins.setExpireDate(utilClass.nvl_trim(p.getExpire_date()));
						approveProcMapper.insertUserInfo(ins);
					} else {
						UpdateUserInfoLoginParam uu = new UpdateUserInfoLoginParam();
						uu.setUserCd(userCd);
						uu.setLoginDate(utilClass.getDate(0, ""));
						uu.setBrc(utilClass.nvl_trim(p.getBrc()));
						uu.setBrnm(utilClass.nvl_trim(p.getBrnm()));
						uu.setIpAddress(utilClass.nvl_trim(p.getIp_address()));
						uu.setExpireDate(utilClass.nvl_trim(p.getExpire_date()));
						approveProcMapper.updateUserInfoLogin(uu);

						approveProcMapper.deleteUserMember(userCd);
					}

					approveProcMapper.insertUserMember(userCd, authCode);
				}
				// 마스킹 해체 이력
				else if ("02".equals(approvalDivCd)) {
					InsertMaskHistoryParam mh = new InsertMaskHistoryParam();
					mh.setUserCd(userCd);
					mh.setReqDate(utilClass.nvl_trim(p.getReq_date()));
					mh.setApprovalReqId(approvalReqId);
					mh.setUserNm(utilClass.nvl_trim(p.getUser_nm()));
					mh.setProgramId(utilClass.nvl_trim(p.getProgram_id()));
					mh.setProgramNm(utilClass.nvl_trim(p.getProgram_nm()));
					mh.setProcDate(utilClass.getDate(0, ""));
					approveProcMapper.insertMaskHistory(mh);
				}
				// 배치요청 작업
				else if ("03".equals(approvalDivCd)) {
					approveProcMapper.updateBulkJobApplyYn(approvalReqId);
				}
			}
		}
	}

	/* 신규 사용자 그룹(레거시) 여부 확인 */
	private boolean isNewUserGroup(String groupId) {
		return "100".equals(groupId) || "101".equals(groupId);
	}

	/* 승인 완료 시 AUTH 컬럼 업데이트 여부 판단 */
	private boolean shouldSetAuth(String approvalDivCd, String groupId) {
		return "01".equals(approvalDivCd) && isNewUserGroup(groupId);
	}

	/* 결재구분/회사코드 기준 권한코드(auth) 계산 */
	private String resolveAuthCode(String approvalDivCd, String comCd) {
		if (!"01".equals(approvalDivCd)) return null;
		if ("1".equals(comCd)) return "102";
		if ("2".equals(comCd)) return "103";
		if ("3".equals(comCd)) return "104";
		return null;
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			return Integer.parseInt(StringUtils.defaultString(v));
		} catch (Exception e) {
			return def;
		}
	}
}
