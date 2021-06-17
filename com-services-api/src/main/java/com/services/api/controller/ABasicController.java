package com.services.api.controller;

import com.services.api.constant.ServiceConstant;
import com.services.api.intercepter.MyAuthentication;
import com.services.api.jwt.UserJwt;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ABasicController {

    public long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return authentication.getJwtUser().getAccountId().longValue();
    }

    public UserJwt getSessionFromToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return authentication.getJwtUser();
    }

    public boolean isAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), ServiceConstant.USER_KIND_ADMIN);
    }

    public boolean isAgency() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), ServiceConstant.USER_KIND_AGENCY);
    }

    // public Organize getCurrentOrg(){
    // SecurityContext securityContext = SecurityContextHolder.getContext();
    // MyAuthentication authentication =
    // (MyAuthentication)securityContext.getAuthentication();
    // Organize org = new Organize();
    // org.setId(authentication.getJwtUser().getOrgId());
    // return org;
    // }

    public boolean isSuperAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication) securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), ServiceConstant.USER_KIND_ADMIN)
                && authentication.getJwtUser().getIsSuperAdmin();
    }
}
