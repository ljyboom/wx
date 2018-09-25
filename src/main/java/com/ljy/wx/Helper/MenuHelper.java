package com.ljy.wx.Helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljy.wx.bean.AccessTokenInfo;

/**
 * 微信自定义菜单
 */
public class MenuHelper {

    public String createMenu(String path){
        final String url="https://api.weixin.qq.com/cgi-bin/menu/create";
        NetWorkHelper netWorkHelper=new NetWorkHelper();
        System.out.println("access_token为:"+AccessTokenInfo.accessToken.getAccessToken());
        //拼接url
        String url_menu=url+"?access_token="+AccessTokenInfo.accessToken.getAccessToken();
        //构建数据
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("type","view");
        jsonObject1.put("name","搞笑");
        jsonObject1.put("url",path+"/wx/funny_view");
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("type","view");
        jsonObject2.put("name","游戏");
        jsonObject2.put("url",path+"/wx/game_view");

        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("name","更多");
        JSONArray array=new JSONArray();
        JSONObject json1=new JSONObject();
        json1.put("type","view");
        json1.put("name","小品");
        json1.put("url",path+"/wx/sketch_view");
        array.add(json1);
        JSONObject json2=new JSONObject();
        json2.put("type","view");
        json2.put("name","音乐");
        json2.put("url",path+"/wx/music_view");
        array.add(json2);
        JSONObject json3=new JSONObject();
        json3.put("type","view");
        json3.put("name","旅游");
        json3.put("url",path+"/wx/tourism_view");
        array.add(json3);
        JSONObject json4=new JSONObject();
        json4.put("type","view");
        json4.put("name","新闻");
        json4.put("url",path+"/wx/news_view");
        array.add(json4);

        jsonObject3.put("sub_button",array);


        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        json.put("button",jsonArray);
        System.out.println("请求数据:"+json.toString());
        String result=netWorkHelper.getHttpsResponse(url_menu,"POST",json.toString());

        return result;
    }

    /**
     * 删除菜单
     */
    public String deteleMenu(){
        final String url="https://api.weixin.qq.com/cgi-bin/menu/delete";
        NetWorkHelper netWorkHelper=new NetWorkHelper();
        //拼接url
        String url_menu=url+"?access_token="+AccessTokenInfo.accessToken.getAccessToken();
        return netWorkHelper.getHttpsResponse(url_menu,"GET","");
    }

}
