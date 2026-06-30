package com.archiving.auth.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.archiving.auth.dao.mapper.LoginMapper.UserLoginRow;

public class LoginUser implements UserDetails {

    private final UserLoginRow user;
    private final boolean insaLogin;

    public LoginUser(UserLoginRow user, boolean insaLogin) {
        this.user = user;
        this.insaLogin = insaLogin;
    }

    public boolean isInsaLogin() {
        return insaLogin;
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getUserCd() {
        return user.getUserCd();
    }

    public String getUserNm() {
        return user.getUserNm();
    }

    public String getUserGrpId() {
        return user.getUserGrpId();
    }

    public String getPicture() {
        return user.getPicture();
    }

    public String getApprowaitcnt() {
        return user.getApprowaitcnt();
    }

    public String getBrc() {
        return user.getBrc();
    }

    public String getBrnm() {
        return user.getBrnm();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String groupId = user.getUserGrpId();
        if (groupId == null || groupId.isBlank()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + groupId));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserCd();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (insaLogin) {
            return true;
        }
        return "Y".equalsIgnoreCase(user.getUseYn());
    }
}
