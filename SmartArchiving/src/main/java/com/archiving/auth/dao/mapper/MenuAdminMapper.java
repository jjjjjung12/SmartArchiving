package com.archiving.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MenuAdminMapper {
	/* 메뉴 ID 시퀀스 다음값 조회 */
	Long nextMenuId();

	/* 메뉴 신규 등록 */
	int insertMenu(@Param("menuId") Long menuId,
		@Param("menuCd") String menuCd,
		@Param("menuNm") String menuNm,
		@Param("menuUrl") String menuUrl,
		@Param("menuDesc") String menuDesc,
		@Param("menuOrder") Integer menuOrder,
		@Param("useYn") String useYn,
		@Param("iconClassId") String iconClassId);

	/* 메뉴 정보 수정 */
	int updateMenu(@Param("menuId") String menuId,
		@Param("menuCd") String menuCd,
		@Param("menuNm") String menuNm,
		@Param("menuUrl") String menuUrl,
		@Param("menuDesc") String menuDesc,
		@Param("menuOrder") Integer menuOrder,
		@Param("useYn") String useYn,
		@Param("iconClassId") String iconClassId);

	/* 메뉴 1건 삭제 */
	int deleteMenu(@Param("menuId") String menuId);

	/* 메뉴 여러 건 일괄 삭제 */
	int deleteMenus(@Param("menuIds") List<String> menuIds);
}

