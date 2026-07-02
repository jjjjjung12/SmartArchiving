package com.archiving.auth.service;

import com.archiving.auth.dto.MenuSaveParamDto;

public interface MenuAdminService {

	/* 메뉴 마스터 저장(C/U/D/DS: 다건 삭제) */
	void save(MenuSaveParamDto p);
}
