package com.archiving.auth.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface LoginMapper {

    InsaLoginRow selectInsaByEno(@Param("eno") String eno);

    UserLoginRow selectUserByUserCd(@Param("userCd") String userCd);

    String selectPendingApprovalLineUserId(@Param("userCd") String userCd);

    int updateLoginDate(@Param("userCd") String userCd, @Param("loginDate") String loginDate);

    int updateBrc(@Param("brc") String brc, @Param("brnm") String brnm, @Param("userCd") String userCd);

    class InsaLoginRow {
        private String rtrDt;
        private String brc;
        private String name;
        private String brnm;

        public String getRtrDt() { return rtrDt; }
        public void setRtrDt(String rtrDt) { this.rtrDt = rtrDt; }
        public String getBrc() { return brc; }
        public void setBrc(String brc) { this.brc = brc; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getBrnm() { return brnm; }
        public void setBrnm(String brnm) { this.brnm = brnm; }
    }

    class UserLoginRow {
        private String userId;
        private String userCd;
        private String userNm;
        private String password;
        private String approwaitcnt;
        private String picture;
        private String userGrpId;
        private String loginDate;
        private String brc;
        private String brnm;
        private String useYn;
        private String ipAddress;
        private String expireDate;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUserCd() { return userCd; }
        public void setUserCd(String userCd) { this.userCd = userCd; }
        public String getUserNm() { return userNm; }
        public void setUserNm(String userNm) { this.userNm = userNm; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getApprowaitcnt() { return approwaitcnt; }
        public void setApprowaitcnt(String approwaitcnt) { this.approwaitcnt = approwaitcnt; }
        public String getPicture() { return picture; }
        public void setPicture(String picture) { this.picture = picture; }
        public String getUserGrpId() { return userGrpId; }
        public void setUserGrpId(String userGrpId) { this.userGrpId = userGrpId; }
        public String getLoginDate() { return loginDate; }
        public void setLoginDate(String loginDate) { this.loginDate = loginDate; }
        public String getBrc() { return brc; }
        public void setBrc(String brc) { this.brc = brc; }
        public String getBrnm() { return brnm; }
        public void setBrnm(String brnm) { this.brnm = brnm; }
        public String getUseYn() { return useYn; }
        public void setUseYn(String useYn) { this.useYn = useYn; }
        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
        public String getExpireDate() { return expireDate; }
        public void setExpireDate(String expireDate) { this.expireDate = expireDate; }
    }
}
