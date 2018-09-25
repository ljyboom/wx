package com.ljy.wx.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljy.wx.Helper.NetWorkHelper;
import com.ljy.wx.bean.AccessToken;
import com.ljy.wx.bean.AccessTokenInfo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器，用于更新AccessToken
 */
@Component
@EnableScheduling
public class AccessTokenUpdater {

    /**
     * 微信公众号appid
     */
    private static final String appId= "wxb900a5c195a05ff3";//测试号

//    private static final String appId= "wx6b11cf1437db89b4";//正式

    /**
     * 微信公众号appsecret
     */
    private static final String appSecret= "2a6a10830c0155e624a3de71156b24be";//测试号

//     private static final String appSecret= "420917397a60560203acd3f6d2b5112f";//正式

    /**
     * 每隔2个小时自动更新AccessToken
     */
    @Scheduled(fixedDelay=1000*60*60*2)
    public void update(){
        System.out.println("开始执行");
        NetWorkHelper netHelper = new NetWorkHelper();
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
        String result = netHelper.getHttpsResponse(Url, "","");
        System.out.println("获取到的access_token="+result);
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        AccessTokenInfo.accessToken=token;
   }
}
