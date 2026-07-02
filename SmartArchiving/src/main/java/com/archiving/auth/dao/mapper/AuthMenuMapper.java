package com.archiving.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.auth.dto.MenuItemDto;

public interface AuthMenuMapper {
    /* 메뉴 전체 조회(선택 조건에 따라 사용중 메뉴만) */
    List<MenuItemDto> selectMenusForAll(@Param("selCd") String selCd);

    /* 사용자별 권한 메뉴 조회 */
    List<MenuItemDto> selectMenusForUser(@Param("userId") String userId);

    /* 관리자 메뉴 검색(메뉴명/URL/사용여부 조건) */
    List<MenuItemDto> selectMenusForAdminSearch(@Param("menuNm") String menuNm,
            @Param("url") String url,
            @Param("useYn") String useYn);
}
