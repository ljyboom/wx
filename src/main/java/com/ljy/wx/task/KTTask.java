package com.ljy.wx.task;

import com.ljy.wx.Helper.NetWorkHelper;
import com.ljy.wx.Helper.OssHelper;
import com.ljy.wx.entity.Picture;
import com.ljy.wx.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 插画
 */
@Component
public class KTTask {
    private String path="http://www.jj20.com";
    private String oss_path="https://ljydzq.oss-cn-beijing.aliyuncs.com/";
    @Autowired
    private PictureRepository pictureRepository;

//    @Scheduled(cron = "0/5 * * * * *")
    public void addPic(){
        List<String> list= pictureRepository.findAllName();
        OssHelper ossHelper = new OssHelper();
        NetWorkHelper netWorkHelper = new NetWorkHelper();
        String url="http://www.jj20.com/bz/ktmh/list_16_";
        for (int j=1;j<=30;j++) {
            String u=url+j+".html";
            String result = netWorkHelper.loadHtml(u,"gb2312");
            Pattern pattern = Pattern.compile("<ul class=\"pic2 vvi fix\">(.+?)</ul>");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                String pm = matcher.group();
                Pattern pattern_li = Pattern.compile("<li>(.+?)</li>");
                Matcher matcher_li = pattern_li.matcher(pm);
                while (matcher_li.find()) {
                    String li = matcher_li.group();
//                System.out.println(li);
                    String link = path + li.substring(li.indexOf("href=", 0) + 6, li.indexOf("\" target="));
//                System.out.println(link);
                    String title = li.substring(li.indexOf("alt=\"") + 5, li.indexOf("\"></a>"));
//                System.out.println(title);
                    int page=0;
                    try{
                         page = Integer.valueOf(li.substring(li.indexOf("(") + 1, li.indexOf(")") - 1));
                    }catch (Exception e){
                        continue;
                    }
//                System.out.println(page);
                    if (!link.isEmpty()) {
                        //加载插画
                        String html = netWorkHelper.loadHtml(link,"gb2312");
//                    System.out.println(html);
                        Pattern pattern1 = Pattern.compile("<img id=\"bigImg\"(.+?)</a>");
                        Matcher matcher1 = pattern1.matcher(html);
                        if (matcher1.find()) {
                            String bi = matcher1.group();
                            String img = bi.substring(bi.indexOf("src=\"") + 5, bi.indexOf("\" alt"));
//                        System.out.println("图片:"+img);
                            String name = img.substring(img.lastIndexOf("/") + 1, img.indexOf("-"));
                            for (int i = 1; i <= page; i++) {
                                String img1 = img.substring(0, img.indexOf("-")) + "-" + i + ".jpg";
                                String name1 = name + "-" + i + ".jpg";
                                if (list.contains(name1)) continue;
                                list.add(name1);
                                //上传到oss
                                String referer=link.substring(0,link.indexOf(".html"))+"_"+i+".html";
                                System.out.println(referer);
                                try{
                                    ossHelper.upload(img1, "ill/" + name1, referer);
                                }catch (Exception e){
                                    continue;
                                }
                                String oss_pic = oss_path + "ill/" + name1;
                                Picture picture = new Picture();
                                picture.setTitle(title);
                                picture.setName(name1);
                                picture.setPicUrl(oss_pic);
                                pictureRepository.save(picture);
                            }
                        }
                    }
                }
            }
        }
    }


}
