package com.archiving.comm.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.archiving.comm.dao.mapper.NoticeMapper;
import com.archiving.comm.dto.NoticeDto;
import com.archiving.comm.service.NoticeService;
import com.archiving.util.UtilClass;

@Service
public class NoticeServiceImpl implements NoticeService {
	private final NoticeMapper noticeMapper;
	private final Environment env;
	private final UtilClass utilClass = new UtilClass();

	public NoticeServiceImpl(NoticeMapper noticeMapper, Environment env) {
		this.noticeMapper = noticeMapper;
		this.env = env;
	}

	/* 공지사항 관리 목록 조회 */
	@Override
	public List<NoticeDto> list() {
		return noticeMapper.selectNoticeManageList();
	}

	/* 공지사항 삭제(시리얼 번호 기준) */
	@Override
	@Transactional
	public void deleteBySerialNumber(String serialNumber) {
		noticeMapper.deleteNotice(serialNumber);
	}

	/* 공지 확인 처리(당일 확인 여부 저장/업데이트) */
	@Override
	@Transactional
	public void markNoticeChecked(String userCd) {
		String chkDate = utilClass.getDate(0, "");
		int cnt = noticeMapper.countNoticeDay(userCd, chkDate);
		if (cnt > 0) {
			noticeMapper.updateNoticeDayChkY(userCd);
		} else {
			noticeMapper.insertNoticeDay(userCd, chkDate);
		}
	}

	/* 공지사항 등록(첨부파일 업로드 후 공지 테이블 저장 및 확인여부 초기화) */
	@Override
	@Transactional
	public void createNotice(String userCd,
							 String subject,
							 String regStartDate,
							 String regEndDate,
							 String subjectDetail,
							 MultipartFile file) throws Exception {
		String regDate = utilClass.getDate(0, ".");
		String serialDateKey = utilClass.getDate(0, "");

		String fileUrl = "";
		String fileName = "";
		if (file != null && !file.isEmpty()) {
			String upDir = env.getProperty("app.upload.dir-notice");
			if (StringUtils.isBlank(upDir)) {
				throw new IllegalStateException("app.upload.dir-notice is not configured");
			}
			File dir = new File(upDir);
			if (!dir.exists() && !dir.mkdirs()) {
				throw new IllegalStateException("Failed to create upload directory: " + upDir);
			}

			fileName = file.getOriginalFilename();
			if (fileName == null) fileName = "upload.bin";
			File dest = new File(dir, fileName);
			file.transferTo(dest);

			String contextPath = StringUtils.defaultString(env.getProperty("server.servlet.context-path"), "");
			fileUrl = contextPath + "/notice";
		}

		NoticeDto notice = new NoticeDto();
		notice.setRegUserCd(userCd);
		notice.setSubject(subject);
		notice.setRegDate(regDate);
		notice.setRegStartDate(regStartDate);
		notice.setRegEndDate(regEndDate);
		notice.setSubjectDetail(subjectDetail);
		notice.setFileNm(fileName);
		notice.setFileUrl(fileUrl);

		for (int attempt = 0; attempt < 5; attempt++) {
			String suffix = noticeMapper.selectNextNoticeSerialSuffix(serialDateKey);
			notice.setSerialNumber(serialDateKey + suffix);
			try {
				noticeMapper.insertNotice(notice);
				noticeMapper.resetNoticeDayChk();
				return;
			} catch (DuplicateKeyException ex) {
				if (attempt >= 4) {
					throw ex;
				}
			}
		}
	}
}
