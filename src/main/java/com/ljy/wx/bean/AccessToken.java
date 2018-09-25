package com.ljy.wx.bean;

/**
 * 微信接口请求凭证
 */
public class AccessToken {

    private String accessToken;//凭证

    private int expiresin;//凭证有效时间，单位：秒

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }
}
