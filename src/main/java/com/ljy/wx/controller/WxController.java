package com.ljy.wx.controller;

import com.ljy.wx.Helper.MenuHelper;
import com.ljy.wx.entity.Video;
import com.ljy.wx.service.VideoService;
import com.ljy.wx.util.CommandUtil;
import com.ljy.wx.util.MessageHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WxController {

    /**
     * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
     */
    private static final String TOKEN = "gacl";
    @Autowired
    private VideoService videoService;


    /**
     * 验证token
     * @param signature 微信加密签名signature
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return
     */
   @GetMapping("vToken")
   public void validationToken(String signature,String timestamp,String nonce,String echostr,
         HttpServletResponse response) throws IOException {
        System.out.println("开始校验");
       //排序
       String sortString = CommandUtil.sort(TOKEN, timestamp, nonce);
       //加密
       String mySignature = CommandUtil.sha1(sortString);
       //校验签名
       if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
           System.out.println("签名校验通过。");
           response.getWriter().write(echostr);
       }else{
           System.out.println("签名校验失败。");

       }
   }

    /**
     * 处理微信服务器发来的消息
     */
   @PostMapping("vToken")
   public void ProcessMessage(HttpServletRequest request, HttpServletResponse response)throws Exception {
       // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
       request.setCharacterEncoding("UTF-8");
       response.setCharacterEncoding("UTF-8");
       System.out.println("请求进入");
       String result = "";
       try{
           Map<String,String> map = MessageHandlerUtil.parseXml(request);
           System.out.println("开始构造消息");
           result = MessageHandlerUtil.buildXml(map);
           System.out.println(result);
           if(result.equals("")){
               result = "未正确响应";
           }

       }catch (Exception e){
           e.printStackTrace();
           System.out.println("发生异常："+ e.getMessage());
       }
       response.getWriter().println(result);
   }

    @GetMapping("createMenu")
    @ResponseBody
   public ResponseEntity<Map<String,Object>> createMenu(String url){
       Map<String,Object> map=new HashMap<>();
       //创建菜单
        MenuHelper menuHelper=new MenuHelper();
        String result=menuHelper.createMenu(url);
        map.put("message",result);
        return ResponseEntity.ok(map);
   }

    @GetMapping("deleteMenu")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> deleteMenu(){
        Map<String,Object> map=new HashMap<>();
        //删除菜单
        MenuHelper menuHelper=new MenuHelper();
        String result=menuHelper.deteleMenu();
        map.put("message",result);
        return ResponseEntity.ok(map);
    }

    /**
     * 跳转到搞笑视频页面
     * @param request
     * @param response
     * @return
     */
   @GetMapping("funny_view")
   public ModelAndView funny(HttpServletRequest request, HttpServletResponse response){
       ModelAndView view=new ModelAndView();
       view.setViewName("funny_view");
       return view;
   }
    /**
     * 跳转到游戏视频页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("game_view")
    public ModelAndView game(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view=new ModelAndView();
        view.setViewName("game_view");
        return view;
    }

    /**
     * 跳转到小品视频页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("sketch_view")
    public ModelAndView sketch(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view=new ModelAndView();
        view.setViewName("sketch_view");
        return view;
    }

    /**
     * 跳转到音乐视频页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("music_view")
    public ModelAndView variety(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view=new ModelAndView();
        view.setViewName("music_view");
        return view;
    }

    /**
     * 跳转到旅游视频页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("tourism_view")
    public ModelAndView tourism(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view=new ModelAndView();
        view.setViewName("tourism_view");
        return view;
    }

    /**
     * 跳转到新闻视频页面
     * @param request
     * @param response
     * @return
     */
    @GetMapping("news_view")
    public ModelAndView news(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view=new ModelAndView();
        view.setViewName("news_view");
        return view;
    }


    /**
     * 查询视频
     *
     */
    @GetMapping("getVideos")
    @ResponseBody
    public ResponseEntity<List<Video>> getVideos(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int rows, @RequestParam(defaultValue = "1")int type){
       List<Video>list= videoService.getVideos(page,rows,type);
       return ResponseEntity.ok(list);
    }

}
