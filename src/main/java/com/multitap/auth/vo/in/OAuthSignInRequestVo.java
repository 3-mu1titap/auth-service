package com.multitap.auth.vo.in;

import lombok.Getter;

@Getter
public class OAuthSignInRequestVo {

    private String provider;
    private String providerId;
    private String providerEmail;

}