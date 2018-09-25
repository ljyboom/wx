package com.ljy.wx.task;

import com.ljy.wx.Helper.NetWorkHelper;
import com.ljy.wx.entity.Video;
import com.ljy.wx.repository.VideoRepository;
import com.ljy.wx.service.BasisService;
import com.ljy.wx.service.VideoService;
import com.ljy.wx.util.CommandUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 腾讯视频爬虫
 */
@Component
public class TXVideoTask {
   private static String root="http://ugcws.video.gtimg.com/";//视频播放根地址
   @Autowired
   private VideoRepository videoRepository;
   @Autowired
   private VideoService videoService;
   @Autowired
   private BasisService basisService;
     /**
      * 爬取腾讯视频地址
     */
     public void txvideo(){
       String url="http://vv.video.qq.com/getinfo?vids=v0027wtj96t&platform=11&charge=10&otype=json";
      NetWorkHelper netWorkHelper=new NetWorkHelper();
      String result=netWorkHelper.loadHtml(url,"utf-8");
      JSONObject json = JSONObject.fromObject(result.substring(result.indexOf("{"),result.length()-1));

      System.out.println(json);
     }

    public void txvideo1(){
       String url="http://vv.video.qq.com/getkey?format=2" +
          "&otype=json&vt=150&vid=v0027wtj96t&ran=0\\%2E9477521511726081\\\\&charge=0&filename=v0027wtj96t.mp4&platform=11";
       NetWorkHelper netWorkHelper=new NetWorkHelper();
       String result=netWorkHelper.loadHtml(url,"utf-8");
       JSONObject json = JSONObject.fromObject(result.substring(result.indexOf("{"),result.length()-1));

       System.out.println(json);
     }

    /**
     * 每天凌晨2点爬取一次腾讯搞笑视频
     */
//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "0 0 2 * * *")
     public void getVideo(){
         //腾讯最近热播搞笑视频
         int num=0;
         //查询所有视频id
         List<String> ids=videoRepository.findAllByVideoId();
         String arr[]={
                 //小品
                 "https://v.qq.com/x/list/fun?icelebrity=-1&itype=1&sort=5&iaspect=-1&icolumn=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=-1&icelebrity=-1&sort=40&itype=1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=-1&sort=48&icelebrity=-1&icolumn=-1&itype=1&offset=",
                 "https://v.qq.com/x/list/fun?itype=2&icelebrity=-1&icolumn=-1&sort=5&iaspect=-1&offset=",
                 "https://v.qq.com/x/list/fun?itype=2&iaspect=-1&icolumn=-1&sort=40&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=-1&icolumn=-1&sort=5&icelebrity=-1&itype=3&offset=",
                 "https://v.qq.com/x/list/fun?icelebrity=-1&icolumn=-1&iaspect=-1&itype=3&sort=40&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=-1&sort=48&icelebrity=-1&itype=3&offset=",
                 //搞笑
                 "https://v.qq.com/x/list/fun?icolumn=1&iaspect=-1&icelebrity=-1&sort=5&itype=-1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=-1&icelebrity=-1&icolumn=3&itype=-1&sort=5&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=1&icelebrity=-1&sort=5&itype=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=-1&icelebrity=-1&sort=5&itype=5&offset=",
                 "https://v.qq.com/x/list/fun?itype=3649&iaspect=-1&sort=5&icelebrity=-1&icolumn=-1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=5&icolumn=-1&icelebrity=-1&itype=-1&sort=5&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&iaspect=-1&icelebrity=-1&sort=5&icolumn=5&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&iaspect=-1&sort=40&icolumn=5&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&icelebrity=-1&iaspect=-1&icolumn=5&sort=48&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=8&itype=-1&iaspect=-1&sort=5&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?sort=40&icolumn=8&itype=-1&iaspect=-1&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=-1&sort=48&itype=-1&icolumn=8&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?sort=5&icelebrity=-1&itype=-1&icolumn=1&iaspect=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&icelebrity=-1&itype=-1&sort=5&iaspect=3589&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=3589&sort=40&itype=-1&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?sort=40&iaspect=2&icelebrity=-1&itype=-1&icolumn=-1&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&icolumn=-1&icelebrity=-1&iaspect=2&sort=5&offset=",
                 "https://v.qq.com/x/list/fun?sort=5&iaspect=3588&icelebrity=-1&itype=-1&icolumn=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&iaspect=3588&itype=-1&sort=40&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&icelebrity=-1&iaspect=10&sort=5&itype=-1&offset=",
                 "https://v.qq.com/x/list/fun?iaspect=-1&sort=5&itype=8&icolumn=-1&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?icolumn=-1&icelebrity=-1&itype=-1&sort=5&iaspect=-1&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&sort=40&iaspect=-1&icolumn=-1&icelebrity=-1&offset=",
                 "https://v.qq.com/x/list/fun?itype=-1&icelebrity=-1&sort=48&icolumn=-1&iaspect=-1&offset=",
                 };
         for (int j=0;j<arr.length;j++) {
             for (int i = 0; i < 34; i++) {
                 NetWorkHelper netWorkHelper = new NetWorkHelper();
                 String url = arr[j] + num;
                 System.out.println("请求地址:"+url);
                 String result = netWorkHelper.loadHtml(url,"utf-8");
                 Pattern pattern = Pattern.compile("<li class=\"list_item\"(.+?)</li>");
                 Matcher matcher = pattern.matcher(result);
                 List<Video> list = new ArrayList<>();
                 while (matcher.find()) {
                     //获取视频id
                     Pattern pattern1 = Pattern.compile("__wind=(.+?)>");
                     Matcher matcher1 = pattern1.matcher(matcher.group());
                     String vid = null;
                     if (matcher1.find()) {
                         vid = matcher1.group().substring(matcher1.group().indexOf("vid=") + 4, matcher1.group().indexOf(">") - 1);
                         System.out.println("视频id:" + vid);
                     }
                     //判断视频是否已存在，存在则忽略
                     if (ids.contains(vid)) continue;
                     ids.add(vid);
                     //获取图片
                     Pattern pattern2 = Pattern.compile("r-lazyload=(.+?)alt");
                     Matcher matcher2 = pattern2.matcher(matcher.group());
                     String img = null;
                     if (matcher2.find()) {
                         img = "http://" + matcher2.group().substring(matcher2.group().indexOf("=") + 4, matcher2.group().indexOf("alt") - 2);
                         System.out.println("图片:" + img);
                     }
                     //获取标题
                     Pattern pattern3 = Pattern.compile("alt=(.+?)r-imgerr=\"h\"");
                     Matcher matcher3 = pattern3.matcher(matcher.group());
                     String title = null;
                     if (matcher3.find()) {
                         title = matcher3.group().substring(matcher3.group().indexOf("=") + 2, matcher3.group().indexOf("r-imgerr=\"h\"") - 2);
                         System.out.println("标题:" + title);
                     }
                     Video video = new Video();
                     video.setVideoId(vid);
                     video.setImg(img);
                     video.setTitle(title);
                     video.setUrl("");
                     video.setCreateTime(new Date());
                     if (j<8){
                         //小品
                         video.setType(3);
                     }else{
                         //搞笑
                         video.setType(1);
                     }
                     video.setSource("tx");
                     video.setUid(CommandUtil.generateShortUuid());
                     list.add(video);
                 }
                 //批量添加
                 basisService.batchInsert(list);
                 list.clear();
                 if (num>990){
                     num=0;
                     break;
                 }
                 num = num + 30;
             }
         }
         System.out.println("===============爬取完毕=========================");
     }


    /**
     * 每天凌晨1点，爬取一次腾讯游戏视频
     */
//    @Test
    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "0/30 * * * * *")
    public void getGameVideo(){
        String root1="http://s.video.qq.com/load_poster_list_info?otype=json&num=100" +
                "&plat=2&pver=0&query=%E7%BB%9D%E5%9C%B0%E6%B1%82%E7%94%9F" +
                "&qid=KxEnEjmsdQbsGKpoBfGBmZ9kSZH6uFgh3oYKR4IfYSVCvUYvloi4tQ&intention_id=1";//绝地求生
        String root2="http://s.video.qq.com/load_poster_list_info?otype=json&num=100" +
                "&plat=2&pver=0&query=%E8%8B%B1%E9%9B%84%E8%81%94%E7%9B%9F" +
                "&qid=KxEnEjmsdQbsGKpoBfGBmZ9kSZH6uFgh3oYKR4IfYSVCvUYvloi4tQ&intention_id=1";//英雄联盟
        String arr[]={root1,root2};
        for(int f=0;f<arr.length;f++) {
            NetWorkHelper netWorkHelper = new NetWorkHelper();
            String result = netWorkHelper.loadHtml(arr[f],"utf-8");
            String data = result.substring(result.indexOf("QZOutputJson=") + 13, result.length() - 1);
            JSONObject json = JSONObject.fromObject(data);
            JSONObject jsonObject = (JSONObject) json.get("PosterListMod");
            JSONArray jsonArray = (JSONArray) jsonObject.get("posterList");
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject jo = (JSONObject) it.next();
                String attentId = (String) jo.get("attentId");
                String url = (String) jo.get("url");
                //拼接地址
                String att_url = "http://s.video.qq.com/get_playsource?id=" + attentId + "&plat=2&type=4" +
                        "&data_type=2&video_type=6&range=1-1360&plname=qq&otype=json&num_mod_cnt=20&callback=_jsonp_3_c1ca";
                String dp = netWorkHelper.loadHtml(att_url,"utf-8");
                String db = dp.substring(dp.indexOf("_jsonp_3_c1ca(") + 14, dp.length() - 1);
//            System.out.println(dp);
                JSONObject object = JSONObject.fromObject(db);
                if (!object.get("PlaylistItem").equals("null")) {
                    JSONObject js = (JSONObject) object.get("PlaylistItem");
                    JSONArray jsonArray1 = (JSONArray) js.get("videoPlayList");
                    Iterator iterator = jsonArray1.iterator();
                    List<Video> list = new ArrayList<>();
                    //查询所有视频
                    List<String> ids = videoRepository.findAllByVideoId();
                    while (iterator.hasNext()) {
                        JSONObject obj = (JSONObject) iterator.next();
                        String vid = (String) obj.get("id");//视频id
                        //判断视频是否存在
                        if (ids.contains(vid)) continue;
                        ids.add(vid);
                        String title = (String) obj.get("title");//视频标题
                        String img = (String) obj.get("pic");//视频图片
                        Video video = new Video();
                        video.setVideoId(vid);
                        video.setImg(img);
                        video.setTitle(title);
                        video.setUrl("");
                        video.setCreateTime(new Date());
                        video.setType(2);
                        video.setSource("tx");
                        video.setUid(CommandUtil.generateShortUuid());
                        list.add(video);

                    }

                    //批量添加
                    basisService.batchInsert(list);

                } else {
                    String root = "http://v.qq.com/detail/b/" + attentId + ".html";
                    NetWorkHelper workHelper = new NetWorkHelper();
                    String r = workHelper.loadHtml(root,"utf-8");
                    Pattern pattern = Pattern.compile("_APP_DATA_posterList(.+?)</script>");
                    Matcher matcher = pattern.matcher(r);
                    List<Video> list = new ArrayList<>();
                    List<String> ids = videoRepository.findAllByVideoId();
                    if (matcher.find()) {
                        String d = matcher.group();
                        d = d.substring(d.indexOf("=") + 1, d.indexOf("</script>"));
                        JSONArray j = JSONArray.fromObject(d);
                        Iterator i = j.iterator();
                        while (i.hasNext()) {
                            JSONObject o = (JSONObject) i.next();
                            String vid = (String) o.get("id");
                            if (ids.contains(vid)) continue;
                            ids.add(vid);
                            String pic = (String) o.get("pic");
                            String title = (String) o.get("title");
                            Video video = new Video();
                            video.setVideoId(vid);
                            video.setImg(pic);
                            video.setTitle(title);
                            video.setUrl("");
                            video.setCreateTime(new Date());
                            video.setType(2);
                            video.setSource("tx");
                            video.setUid(CommandUtil.generateShortUuid());
                            list.add(video);
                        }
                    }
                    //批量添加
                    basisService.batchInsert(list);
                }
            }
        }
        System.out.println("==============爬取完成=============");
    }

    /**
     *爬取音乐视频
     */
//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "0 0 3 * * *")
    public void addMusic(){
        //查询所有视频id
        List<String> ids=videoRepository.findAllByVideoId();
        String arr[]={
                "http://v.qq.com/x/list/music?language=-1&mv_itype=-1&sort=5&itype=1&offset=",
                "http://v.qq.com/x/list/music?mv_itype=-1&itype=1&language=-1&sort=40&offset=",
                "http://v.qq.com/x/list/music?mv_itype=1&itype=1&sort=40&language=-1&offset=",
                "http://v.qq.com/x/list/music?sort=5&mv_itype=1&itype=1&language=-1&offset=",
                "http://v.qq.com/x/list/music?sort=5&language=-1&itype=1&mv_itype=2&offset=",
                "http://v.qq.com/x/list/music?sort=40&itype=1&mv_itype=2&language=-1&offset=0",
                "http://v.qq.com/x/list/music?sort=40&language=-1&mv_itype=3&itype=1&offset=",
                "http://v.qq.com/x/list/music?itype=1&mv_itype=3&sort=5&language=-1&offset=",
                "http://v.qq.com/x/list/music?language=1&sort=5&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?mv_itype=-1&itype=1&sort=40&language=1&offset=",
                "http://v.qq.com/x/list/music?language=2&sort=40&itype=1&mv_itype=-1&offset=",
                "http://v.qq.com/x/list/music?sort=5&mv_itype=-1&itype=1&language=2&offset=",
                "http://v.qq.com/x/list/music?sort=5&language=4&itype=1&mv_itype=-1&offset=",
                "http://v.qq.com/x/list/music?sort=40&language=4&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?sort=40&language=5&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?language=-1&sort=40&itype=1&mv_itype=-1&iarea=1&offset=",
                "http://v.qq.com/x/list/music?sort=5&iarea=1&language=-1&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?mv_itype=-1&sort=5&iarea=2&itype=1&language=-1&offset=",
                "http://v.qq.com/x/list/music?iarea=2&sort=40&language=-1&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?sort=40&language=-1&mv_itype=-1&iarea=3&itype=1&offset=",
                "http://v.qq.com/x/list/music?language=-1&iarea=3&sort=5&mv_itype=-1&itype=1&offset=",
                "http://v.qq.com/x/list/music?mv_itype=-1&iarea=1&itype=1&style=1&sort=40&language=-1&offset=",
                "http://v.qq.com/x/list/music?itype=2&iarea=-1&sort=40&style=-1&language=-1&iyear=-1&offset=",
                "http://v.qq.com/x/list/music?iyear=-1&style=-1&itype=2&language=-1&iarea=-1&sort=5&offset=",
                "http://v.qq.com/x/list/music?itype=2&sort=5&iarea=-1&language=1&iyear=-1&style=-1&offset=",
                "http://v.qq.com/x/list/music?iyear=-1&iarea=-1&itype=2&language=1&sort=40&style=-1&offset=",
                "http://v.qq.com/x/list/music?iyear=-1&sort=40&itype=2&language=2&iarea=-1&style=-1&offset=",
                "http://v.qq.com/x/list/music?style=-1&itype=2&iyear=-1&language=3&iarea=-1&sort=40&offset=",
                "http://v.qq.com/x/list/music?style=-1&itype=3&sort=40&language=-1&iyear=-1&iarea=2&offset=",
                "http://v.qq.com/x/list/music?style=-1&iyear=-1&sort=40&iarea=1&itype=3&language=-1&offset="
        };
        addVideo(arr,ids,4);
        System.out.println("===============爬取完毕=========================");
    }

    /**
     * 爬取旅游视频
     */
//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "0 0 4 * * *")
    public void tourism(){
        //查询所有视频id
        List<String> ids=videoRepository.findAllByVideoId();
        String arr[]={
                "http://v.qq.com/x/list/travel?ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=2&itheme_tour=-1&offset=",
                "http://v.qq.com/x/list/travel?iaspect=3&ihorizons=-1&itheme_tour=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&itheme_tour=-1&iaspect=4&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=5&itheme_tour=-1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&iaspect=6&ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?iaspect=7&ihorizons=-1&itheme_tour=-1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&ihorizons=-1&iaspect=8&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&itheme_tour=1&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=-1&itheme_tour=3&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=4&iaspect=-1&ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=5&ihorizons=-1&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=-1&itheme_tour=6&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=7&ihorizons=-1&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?iaspect=-1&itheme_tour=8&ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?iaspect=-1&itheme_tour=-1&ihorizons=1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&ihorizons=2&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&ihorizons=4&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&iaspect=-1&ihorizons=5&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&iaspect=-1&ihorizons=6&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&icolumn=2&iaspect=-1&ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=-1&itheme_tour=-1&icolumn=3&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&icolumn=4&iaspect=-1&ihorizons=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&iaspect=-1&itheme_tour=-1&icolumn=5&offset=",
                "http://v.qq.com/x/list/travel?itheme_tour=-1&iaspect=-1&ihorizons=-1&icolumn=6&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&itheme_tour=-1&icolumn=7&iaspect=-1&offset=",
                "http://v.qq.com/x/list/travel?ihorizons=-1&itheme_tour=-1&icolumn=8&iaspect=-1&offset="
        };
        addVideo(arr,ids,5);
        System.out.println("===============爬取完毕=========================");
    }

    /**
     * 爬取新闻视频
     */
//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "0 0 5 * * *")
    public void news(){
        //查询所有视频id
        List<String> ids=videoRepository.findAllByVideoId();
        String arr[]={
                "http://v.qq.com/x/list/news?column=-1&itype=1&sort=5&offset=",
                "http://v.qq.com/x/list/news?sort=5&column=-1&itype=2&offset=",
                "http://v.qq.com/x/list/news?sort=5&column=-1&itype=3&offset=",
                "http://v.qq.com/x/list/news?itype=4&sort=5&column=-1&offset=",
                "http://v.qq.com/x/list/news?itype=5&sort=5&column=-1&offset=",
                "http://v.qq.com/x/list/news?itype=-1&offset=",
                "http://v.qq.com/x/list/news?itype=1&offset=",
                "http://v.qq.com/x/list/news?itype=2&offset=",
                "http://v.qq.com/x/list/news?itype=3&offset=",
                "http://v.qq.com/x/list/news?itype=4&offset=",
                "http://v.qq.com/x/list/news?itype=5&offset="
        };
        addVideo(arr,ids,6);
        System.out.println("===============爬取完毕=========================");
    }

    private void addVideo(String arr[],List<String> ids,int type){
        int num=0;
        for (int j=0;j<arr.length;j++) {
            for (int i = 0; i < 34; i++) {
                NetWorkHelper netWorkHelper = new NetWorkHelper();
                String url = arr[j] + num;
                System.out.println("请求地址:"+url);
                String result = netWorkHelper.loadHtml(url,"utf-8");
                Pattern pattern = Pattern.compile("<li class=\"list_item\"(.+?)</li>");
                Matcher matcher = pattern.matcher(result);
                List<Video> list = new ArrayList<>();
                while (matcher.find()) {
                    //获取视频id
                    Pattern pattern1 = Pattern.compile("__wind=(.+?)>");
                    Matcher matcher1 = pattern1.matcher(matcher.group());
                    String vid = null;
                    if (matcher1.find()) {
                        vid = matcher1.group().substring(matcher1.group().indexOf("vid=") + 4, matcher1.group().indexOf(">") - 1);
                    }
                    //判断视频是否已存在，存在则忽略
                    if (ids.contains(vid)) continue;
                    ids.add(vid);
                    //获取图片
                    Pattern pattern2 = Pattern.compile("r-lazyload=(.+?)alt");
                    Matcher matcher2 = pattern2.matcher(matcher.group());
                    String img = null;
                    if (matcher2.find()) {
                        img = "http://" + matcher2.group().substring(matcher2.group().indexOf("=") + 4, matcher2.group().indexOf("alt") - 2);
                        System.out.println("图片:" + img);
                    }
                    //获取标题
                    Pattern pattern3 = Pattern.compile("alt=(.+?)r-imgerr=\"h\"");
                    Matcher matcher3 = pattern3.matcher(matcher.group());
                    String title = null;
                    if (matcher3.find()) {
                        title = matcher3.group().substring(matcher3.group().indexOf("=") + 2, matcher3.group().indexOf("r-imgerr=\"h\"") - 2);
                        System.out.println("标题:" + title);
                    }
                    Video video = new Video();
                    video.setVideoId(vid);
                    video.setImg(img);
                    video.setTitle(title);
                    video.setUrl("");
                    video.setCreateTime(new Date());
                    video.setType(type);
                    video.setSource("tx");
                    video.setUid(CommandUtil.generateShortUuid());
                    list.add(video);
                }
                //批量添加
                basisService.batchInsert(list);
                list.clear();
                if (num>990){
                    num=0;
                    break;
                }
                num = num + 30;
            }
        }
    }


}
