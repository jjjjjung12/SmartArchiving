package com.archiving.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.auth.dao.mapper.MenuAdminMapper;
import com.archiving.auth.dto.MenuSaveParamDto;
import com.archiving.auth.service.MenuAdminService;

@Service
public class MenuAdminServiceImpl implements MenuAdminService {

	private final MenuAdminMapper menuAdminMapper;

	public MenuAdminServiceImpl(MenuAdminMapper menuAdminMapper) {
		this.menuAdminMapper = menuAdminMapper;
	}

	/* 메뉴 마스터 저장(C/U/D/DS: 다건 삭제) */
	@Override
	@Transactional
	public void save(MenuSaveParamDto p) {
		String crud = p.getCrud();
		if ("C".equalsIgnoreCase(crud)) {
			Long id = menuAdminMapper.nextMenuId();
			menuAdminMapper.insertMenu(
				id,
				p.getMenu_cd(),
				p.getMenu_nm(),
				p.getMenu_url(),
				p.getMenu_desc(),
				toInt(p.getMenu_order(), 0),
				p.getUse_yn(),
				p.getIcon_class_id()
			);
			return;
		}

		if ("D".equalsIgnoreCase(crud)) {
			menuAdminMapper.deleteMenu(p.getMenu_id());
			return;
		}

		if ("DS".equalsIgnoreCase(crud)) {
			List<String> ids = new ArrayList<>();
			if (p.getMenu_id_list() != null) {
				for (MenuSaveParamDto.MenuIdWrapper w : p.getMenu_id_list()) {
					if (w != null && w.getMenu_id() != null) ids.add(w.getMenu_id());
				}
			}
			if (!ids.isEmpty()) menuAdminMapper.deleteMenus(ids);
			return;
		}

		// update
		menuAdminMapper.updateMenu(
			p.getMenu_id(),
			p.getMenu_cd(),
			p.getMenu_nm(),
			p.getMenu_url(),
			p.getMenu_desc(),
			toInt(p.getMenu_order(), 0),
			p.getUse_yn(),
			p.getIcon_class_id()
		);
	}

	/* 문자열을 안전하게 Integer로 변환(기본값 제공) */
	private Integer toInt(String v, int def) {
		try {
			if (v == null || v.isBlank()) return def;
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}
}
